/*******************************************************************************
 * jDAG is a project to build acyclic dataflow graphs for processing massive datasets.
 *
 *     Copyright (C) 2011, Author: Balraja,Subbiah
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 */

package org.jdag.communicator.messages;

import java.io.Serializable;

import org.jdag.communicator.Message;
import org.jdag.graph.ExecutionResult;
import org.jdag.graph.VertexID;

/**
 * The message to be used for sending back the status related to the
 * execution of a vertex in a node.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class ExecuteVertexCommandStatus implements Serializable,Message
{
     private final VertexID myExecutedVertex;

     private final ExecutionResult myResult;

     /**
      * CTOR
      */
    public ExecuteVertexCommandStatus(VertexID executedVertex,
                                      ExecutionResult result)
    {
        myExecutedVertex = executedVertex;
        myResult = result;
    }

    /**
     * Returns the value of executedVertex
     */
    public VertexID getExecutedVertex()
    {
        return myExecutedVertex;
    }

    /**
     * Returns the value of result
     */
    public ExecutionResult getResult()
    {
        return myResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + (myExecutedVertex == null ? 0 : myExecutedVertex.hashCode());
        result = prime * result
                + (myResult == null ? 0 : myResult.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExecuteVertexCommandStatus other = (ExecuteVertexCommandStatus) obj;
        if (myExecutedVertex == null) {
            if (other.myExecutedVertex != null) {
                return false;
            }
        }
        else if (!myExecutedVertex.equals(other.myExecutedVertex)) {
            return false;
        }
        if (myResult == null) {
            if (other.myResult != null) {
                return false;
            }
        }
        else if (!myResult.equals(other.myResult)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "ExecuteVertexCommandStatus [myExecutedVertex="
                    + myExecutedVertex + ", myResult=" + myResult + "]";
    }
}
