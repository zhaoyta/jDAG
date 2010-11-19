package org.augur.master;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.augur.commmunicator.Communicator;
import org.augur.commmunicator.HostID;
import org.augur.commmunicator.Message;
import org.augur.commmunicator.Reactor;
import org.augur.common.Application;
import org.augur.common.ApplicationExecutor;
import org.augur.communicator.messages.ExecuteVertexProtos;
import org.augur.communicator.messages.JDAGMessageType;
import org.augur.communicator.messages.SimpleMessage;
import org.augur.communicator.messages.UpAndLiveProtos;
import org.augur.communicator.messages.ExecuteVertexProtos.ExecuteVertexMessage;
import org.augur.communicator.messages.ExecuteVertexStatusProtos.ExecuteVertexStatusMessage;
import org.augur.dag.ExecutionGraph;
import org.augur.dag.ExecutionGraphID;
import org.augur.dag.ExecutionResult;
import org.augur.dag.GraphVertexID;
import org.augur.dag.IOKey;
import org.augur.dag.Vertex;
import org.augur.dag.VertexID;
import org.augur.dag.scheduler.Schedule;
import org.augur.dag.scheduler.TopologicalSortSchedule;
import org.augur.node.NodeExecutor;

/**
 * The master server. Master takes a graph as input and gets its executed
 * through many of its workers nodes.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class GraphExecutor implements Application
{
    private final WorkerSchedulingPolicy myWorkerSchedulingPolicy;

    private final Map<ExecutionGraphID, Schedule> myGraph2ScheduleMap;

    private final Communicator myCommunicator;

    private final ScheduledExecutorService myScheduler;

    /**
     * Updates scheduling policy with the latest information from the scheduling
     * nodes.
     */
    private class UpAndALiveReactor implements Reactor
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void process(Message m)
        {
            SimpleMessage message = (SimpleMessage) m;
            myWorkerSchedulingPolicy.process(
                ((UpAndLiveProtos.UpAndAliveMessage) message.getPayload()));
        }
    }

    /**
     * Updates the status of execution of a vertex corresponding to a graph.
     */
    private class ExecuteVertexStatusReactor implements Reactor
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void process(Message m)
        {
            SimpleMessage message = (SimpleMessage) m;
            ExecuteVertexStatusMessage status =
                (ExecuteVertexStatusMessage) message.getPayload();
            ExecutionResult result =
                ExecutionResult.getExecutionResult(
                    status.getExecutionStatus());
            if (result == ExecutionResult.SUCCESS) {
                ExecutionGraphID graphID =
                    new ExecutionGraphID(status.getGraphId());
                VertexID vertexID = new VertexID(status.getVertexId());
                GraphVertexID graphVertexID =
                    new GraphVertexID(graphID, vertexID);
                myWorkerSchedulingPolicy.removeVertexToHostMapping(graphVertexID);
                Schedule schedule = myGraph2ScheduleMap.get(graphID);
                schedule.notifyDone(vertexID);
                if (schedule.isCompleted()) {
                   myGraph2ScheduleMap.remove(graphID);
                }
                else {
                    myScheduler.schedule(
                        new VertexSchedulingTask(graphID),
                        10,
                        TimeUnit.MICROSECONDS);
                }
            }
        }
    }

    /**
     * A simple function that schedules the next vertex from the graph on
     * a worker node.
     */
    private class VertexSchedulingTask implements Runnable
    {
        private final ExecutionGraphID myGraphID;

        /**
         * CTOR
         */
        public VertexSchedulingTask(ExecutionGraphID graphID)
        {
            myGraphID = graphID;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run()
        {
            Schedule schedule = myGraph2ScheduleMap.get(myGraphID);
            Vertex vertex = schedule.getVertexForExecution();
            HostID hostID =
                myWorkerSchedulingPolicy.getWorkerNode(
                    new GraphVertexID(myGraphID, vertex.getID()));
            if (hostID != null) {
                ExecuteVertexMessage executeVertexMessage =
                    ExecuteVertexProtos.ExecuteVertexMessage
                                       .newBuilder()
                                       .setUdfIdentifier(vertex.getUDFIdentifier())
                                       .addAllInputs(
                                           (Iterable<? extends org.augur.communicator.messages.ExecuteVertexProtos.ExecuteVertexMessage.IOKey>)
                                           makeIOKeys(vertex.getInputs()).iterator())
                                       .addAllOutputs(
                                           (Iterable<? extends org.augur.communicator.messages.ExecuteVertexProtos.ExecuteVertexMessage.IOKey>)
                                           makeIOKeys(vertex.getOutputs()).iterator())
                                       .build();
                SimpleMessage simpleMessage =
                    new SimpleMessage(JDAGMessageType.EXECUTE_VERTEX_MESSAGE,
                                      executeVertexMessage);
                myCommunicator.sendMessage(hostID, simpleMessage);
            }
            else {
                myScheduler.schedule(
                    new VertexSchedulingTask(myGraphID),
                    10,
                    TimeUnit.SECONDS);
            }
        }
    }

    /**
     * CTOR
     */
    @Inject
    public GraphExecutor(WorkerSchedulingPolicy schedulingPolicy,
                         Communicator communicator)
    {
        myWorkerSchedulingPolicy = schedulingPolicy;
        myGraph2ScheduleMap = new HashMap<ExecutionGraphID, Schedule>();
        myCommunicator = communicator;
        ThreadFactoryBuilder tfBuilder = new ThreadFactoryBuilder();
        ThreadFactory namedFactory =
            tfBuilder.setNameFormat(NodeExecutor.class.getSimpleName())
                     .build();
        myScheduler = Executors.newScheduledThreadPool(1, namedFactory);
    }

    /** Starts the communicator */
    public void start()
    {
        myCommunicator.attachReactor(JDAGMessageType.UP_AND_ALIVE_MESSAGE,
                                     new UpAndALiveReactor());
        myCommunicator.attachReactor(JDAGMessageType.EXECUTE_VERTEX_STATUS_MESSAGE,
                                     new ExecuteVertexStatusReactor());
        myCommunicator.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop()
    {
        // TODO Auto-generated method stub

    }

    private List<ExecuteVertexProtos.ExecuteVertexMessage.IOKey> makeIOKeys(
        List<IOKey> ioKeys)
    {
        ArrayList<ExecuteVertexProtos.ExecuteVertexMessage.IOKey> protoIOKeys =
            new ArrayList<ExecuteVertexProtos.ExecuteVertexMessage.IOKey>();
        for (IOKey ioKey : ioKeys) {
            protoIOKeys.add(ExecuteVertexProtos.ExecuteVertexMessage
                                               .IOKey
                                               .newBuilder()
                                               .setTypeId(ioKey.getSourceType()
                                                               .getTypeID())
                                               .setIoIdentifier(
                                                   ioKey.getIdentifier())
                                               .build());
        }
        return protoIOKeys;

    }

    /**
     * Executes the graph over the worker vertices.
     */
    public void execute(ExecutionGraph graph)
    {
        Schedule schedule = new TopologicalSortSchedule(graph);
        myGraph2ScheduleMap.put(graph.getID(), schedule);
        myScheduler.schedule(
            new VertexSchedulingTask(graph.getID()),
            10,
            TimeUnit.SECONDS);
    }

    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new MasterExecutorModule());
        GraphExecutor executor =
            injector.getInstance(GraphExecutor.class);
        ApplicationExecutor applicationExecutor =
            new ApplicationExecutor(executor);
        applicationExecutor.run();
    }
}