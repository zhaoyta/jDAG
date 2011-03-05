/**
 *
 */
package org.jdag.node;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jdag.communicator.messages.ExecuteVertexCommand;
import org.jdag.data.Function;
import org.jdag.data.FunctionInput;
import org.jdag.graph.ExecutionContext;
import org.jdag.graph.ExecutionResult;
import org.jdag.graph.SimpleVertex;
import org.jdag.graph.Vertex;
import org.jdag.graph.VertexID;
import org.jdag.io.IOKey;

/**
 * Extends SimpleVertex to support the execution of a function.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class ExecutableVertex  implements Vertex, Callable<ExecutionResult>
{
    private final Vertex myVertex;

	private final ExecutionContext myExecutionContext;

	/**
	 * CTOR
	 */
	public ExecutableVertex(Vertex vertex,
			                          ExecutionContext executionContext)
	{
	    myVertex = vertex;
		myExecutionContext = executionContext;
	}

	/**
     * Executes the specified user defined function and returns the result.
     */
    public ExecutionResult execute(ExecutionContext context)
    {

        try {
             Object toBeExecuted = Class.forName(getUDFIdentifier());
             if (toBeExecuted instanceof Function<?,?>) {
                 Function<?,?> function = (Function<?,?>) toBeExecuted;
                 FunctionInput<T>
             }
        }
        catch (Throwable e) {
            return ExecutionResult.ERROR;
        }
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public ExecutionResult call() throws Exception
	{
		return execute(myExecutionContext);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public VertexID getID()
    {
        return myVertex.getID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IOKey> getInputs()
    {
        return myVertex.getInputs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IOKey> getOutputs()
    {
        return myVertex.getOutputs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUDFIdentifier()
    {
        return myVertex.getUDFIdentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
    }
}