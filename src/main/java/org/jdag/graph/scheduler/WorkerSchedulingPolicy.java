package org.jdag.graph.scheduler;

import org.jdag.communicator.HostID;
import org.jdag.graph.VertexID;
import org.jdag.master.ExecutionRegistry;

/**
 * The type that defines the contract for scheduling vertices over the
 * worker nodes. This is the type that's responsible for selecting a node
 * to which a graph vertex has to be shipped for execution.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public interface WorkerSchedulingPolicy
{
    /**
     * Returns the identifier for a worker node to which graph vertices
     * can be scheduled or null if a worker is not free.
     */
    public HostID getWorkerNode(VertexID          graphVertexID,
                                ExecutionRegistry executionStateRegistry);
}