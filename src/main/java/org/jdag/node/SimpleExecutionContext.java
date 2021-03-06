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

package org.jdag.node;

import org.jdag.graph.ExecutionContext;
import org.jdag.io.IOFactory;
import org.jdag.io.IOSource;
import org.jdag.io.flatfile.FlatFileIOFactory;
import org.jdag.io.serializedfile.FileIOFactory;

public class SimpleExecutionContext implements ExecutionContext
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IOFactory makeIOFactory(IOSource source)
	{
		return source == IOSource.FLAT_FILE ?
		    new FlatFileIOFactory() :  new FileIOFactory();
	}
}
