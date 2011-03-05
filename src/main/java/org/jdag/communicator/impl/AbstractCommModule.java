package org.jdag.communicator.impl;

import com.google.inject.AbstractModule;

import org.hornetq.api.core.TransportConfiguration;
import org.jdag.commmunicator.MessageMarshaller;
import org.jdag.communicator.messages.ProtoBufMessageMarshaller;

/**
 * Implements guice <code>AbstractModule</code> to define bindings corresponding
 * to the dependency injection.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public abstract class AbstractCommModule extends AbstractModule
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure()
    {
        bind(MessageMarshaller.class).to(ProtoBufMessageMarshaller.class);
        bind(CommunicatorConfig.class).toProvider(CommunicatorConfigProvider.class);
        bind(TransportConfiguration.class).toProvider(TransportConfigurationProvider.class);
    }
}