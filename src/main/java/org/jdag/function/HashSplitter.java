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

package org.jdag.function;

import java.util.ArrayList;
import java.util.List;

import org.jdag.dsl.Input;
import org.jdag.dsl.Output;
import org.jdag.dsl.Splitter;
import org.jdag.dsl.impl.ComputeFailedException;
import org.jdag.graph.ExecutionContext;
import org.jdag.io.IOKey;

/**
 * The splitter function to be used for hashing the records
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class HashSplitter<T> implements Splitter<T>
{
    private int myNumPartitions;
    
    /**
     * CTOR
     */
    public HashSplitter()
    {
        myNumPartitions = 0;
    }

    /**
     * CTOR
     */
    public HashSplitter(int numPartitions)
    {
        myNumPartitions = numPartitions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numPartitions()
    {
        return myNumPartitions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void split(Input<T> input, List<Output<T>> outputs)
    {
        int rc = 0;
        for (T record : new IteratorWrapper<T>(input.getIterator())) {
            int outIndex = rc % myNumPartitions;
            Output<T> output = outputs.get(outIndex);
            output.write(record);
            rc++;
        }

        for (Output<T> output : outputs) {
            output.done();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(ExecutionContext context,
                        List<IOKey> inputKeys,
                        List<IOKey> outputKeys)
        throws ComputeFailedException
    {
        myNumPartitions = outputKeys.size();
        IOKey inputKey = inputKeys.get(0);
        Input<T> input =
            context.makeIOFactory(inputKey.getSourceType())
                   .makeInput(inputKey);

        List<Output<T>> outputs = new ArrayList<Output<T>>();
        for (IOKey outputKey : outputKeys) {
            outputs.add(
                context.makeIOFactory(outputKey.getSourceType())
                       .<T>makeOutput(outputKey)
            );
        }

        split(input, outputs);
    }
}
