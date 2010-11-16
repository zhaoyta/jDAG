package org.jdryad.communicator.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.MessageHandler;


import org.jdryad.commmunicator.Communicator;
import org.jdryad.commmunicator.HostID;
import org.jdryad.commmunicator.Message;
import org.jdryad.commmunicator.MessageMarshaller;
import org.jdryad.commmunicator.MessageType;
import org.jdryad.commmunicator.Reactor;
import org.jdryad.common.NamedThreadFactory;
import org.jdryad.common.Pair;
import org.jdryad.common.log.LogFactory;

/**
 * A simple implementation of communicator using jboss HornetQ messaging
 * server. The idea is each node has a message queue. The queue is made
 * persistent. This way the messages sent to an application is persisted in the
 * message quque's journal and when started again it can process the messages
 * from the quque. This is our first level of defense against failover.
 *
 * I am contemplating about replacing the hornetq with our own custom
 * protocol implementation using jboss netty. But that can wait for sometime.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class CommunicatorImpl implements Communicator
{
    private static final String PRODUCER_TF_NAME = "HQMessageProducer";

    private static final String CONSUMER_TF_NAME = "HQMessageConsumer";

    /** The logger */
    private final Logger LOG =  LogFactory.getLogger(CommunicatorImpl.class);

    /** The connection factory to be used for the creating a jms connection */
    private final ClientSessionFactory myClientSessionFactory;

    /**
     * The session to be used by message producer for sending the messages
     */
    private final ClientSession mySession;

    /** The producer is configured with null destination */
    private final ClientProducer myMessageProducer;

    /** The consumer to be used for receiving the messages */
    private final ClientConsumer myMessageConsumer;

    /**
     * The thread pool to be used for sending messages through producer.
     * Since a session is not thread safe, we always use a SingleThreaded
     * Executor.
     */
    private final ExecutorService myProducerExecutor;

    /**
     * The thread pool to be used for processing the received messages
     * from the quque.
     */
    private final ExecutorService myConsumerExecutor;

    /** Used for converting the messages to binary format */
    private final MessageMarshaller myMarshaller;

    /** The configuration information */
    private final CommunicatorConfig myConfig;

    /** Maps a Reactor to its MessageType */
    private final Multimap<MessageType, Pair<Reactor, Executor>>
        myMessageToReactorMap;

    /**
     * Wraps a <code>Reactor</code> so that it can be scheduled on a
     * Executor.
     */
    private static class ExecutableReactor implements Runnable
    {
        private final Reactor myReactor;

        private final Message myMessage;

        /**
         * CTOR
         */
        public ExecutableReactor(Message message, Reactor reactor)
        {
            myMessage = message;
            myReactor = reactor;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run()
        {
            myReactor.process(myMessage);
        }
    }

    /**
     * Takes care of performing send message task on a single threaded
     * executor.
     */
    private class SendMessageTask implements Runnable
    {
        private final HostID myHostID;

        private final byte[] myMessageBody;

        /**
         * CTOR
         */
        public SendMessageTask(HostID hostID, Message message)
        {
            myHostID = hostID;
            myMessageBody = myMarshaller.marshal(message);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run()
        {
            ClientMessage message = null;
            synchronized(mySession) {
                message =
                    mySession.createMessage(ClientMessage.BYTES_TYPE, true);
            }
            message.setAddress(new SimpleString(myHostID.getIdentifier()));
            message.getBodyBuffer().writeBytes(myMessageBody,
                                               0,
                                               myMessageBody.length);
            try {
                myMessageProducer.send(message.getAddress(), message);
            }
            catch (HornetQException e) {
                LOG.log(Level.SEVERE, "Unable to send the message", e);
            }
        }
    }

    /**
     * Implements MessageListener for processing the messages received
     * through jms quque.
     */
    private class CommMessageListener implements MessageHandler
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void onMessage(ClientMessage message)
        {
            if (message.getType() != ClientMessage.BYTES_TYPE) {
                LOG.info("Received a message from " + message.getAddress()
                         + " of type " + message.getType());
                return;
            }
            byte[] body = new byte[message.getBodyBuffer().readableBytes()];
            message.getBodyBuffer().readBytes(body);
            Message m = myMarshaller.unmarshal(body);
            synchronized(myMessageToReactorMap) {
                for (Pair<Reactor, Executor> reactorExecPair :
                    myMessageToReactorMap.get(m.getMessageType()))
                {
                    ExecutableReactor reactor =
                        new ExecutableReactor(m, reactorExecPair.getFirst());
                    if (reactorExecPair.getSecond() != null) {
                        reactorExecPair.getSecond().execute(reactor);
                    }
                    else {
                        myConsumerExecutor.execute(reactor);
                    }
                }
            }
        }
    }

    /**
     * CTOR
     */
    @Inject
    public CommunicatorImpl(MessageMarshaller messageMarshaller,
                            CommunicatorConfig config,
                            TransportConfiguration transportConfiguration)
    {
        myConfig = config;
        myMarshaller = messageMarshaller;
        myMessageToReactorMap =
            HashMultimap.<MessageType, Pair<Reactor, Executor>>create();
        myClientSessionFactory =
            HornetQClient.createClientSessionFactory(transportConfiguration);

        try {
            mySession = myClientSessionFactory.createSession();
            myMessageProducer = mySession.createProducer();
            myProducerExecutor =
                Executors.newSingleThreadExecutor(
                    NamedThreadFactory.newNamedFactory(PRODUCER_TF_NAME));

            mySession.createQueue(myConfig.getClientName(),
                                  myConfig.getQuqueName(),
                                  true);
            myMessageConsumer =
                mySession.createConsumer(myConfig.getQuqueName());
            myMessageConsumer.setMessageHandler(new CommMessageListener());
            myConsumerExecutor =
                Executors.newSingleThreadExecutor(
                    NamedThreadFactory.newNamedFactory(CONSUMER_TF_NAME));
        }
        catch (HornetQException e) {
            LOG.log(Level.INFO, "Exception while initializing the communicator", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachReactor(MessageType type, Reactor r)
    {
        synchronized (myMessageToReactorMap) {
            myMessageToReactorMap.put(type,
                                       new Pair<Reactor, Executor>(r, null));
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachReactor(MessageType type, Reactor r, Executor e)
    {
        synchronized (myMessageToReactorMap) {
            myMessageToReactorMap.put(type,
                                       new Pair<Reactor, Executor>(r, e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HostID getMyHostID()
    {
        return new HostID(myConfig.getClientName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(HostID host, Message m)
    {
        myProducerExecutor.submit(new SendMessageTask(host, m));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start()
    {
        try {
            mySession.start();
        }
        catch (HornetQException e) {
            LOG.log(Level.SEVERE, "Unable to start a session", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop()
    {
        try {
            mySession.stop();
            myProducerExecutor.shutdownNow();
            myConsumerExecutor.shutdownNow();
        }
        catch (HornetQException e) {
            LOG.log(Level.SEVERE, "Unable to start a session", e);
            throw new RuntimeException(e);
        }
    }
}