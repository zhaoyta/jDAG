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

package org.jdag.example.wc;

import java.util.HashMap;
import java.util.Map;

import org.jdag.dsl.Input;
import org.jdag.dsl.Output;
import org.jdag.function.FunctionBase;
import org.jdag.function.IteratorWrapper;

import com.google.common.base.Functions;

/**
 * The function to be used for computing the number of words in a file.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class CountWords extends FunctionBase<String, Map<String,Integer>>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(Input<String> input,
                        Output<Map<String, Integer>> output)
    {
         Map<String, Integer> wordcountMap = new HashMap<String, Integer>();
         com.google.common.base.Function<String,Integer> lookupFunction =
             Functions.forMap(wordcountMap, 0);
         for (String line : new IteratorWrapper<String>(input.getIterator())) {
             String[] words = line.split(" ");
             for (String word : words) {
                  int count = lookupFunction.apply(word);
                  wordcountMap.put(word, ++count);
             }
         }
         output.write(wordcountMap);
         output.done();
    }
}
