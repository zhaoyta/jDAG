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

package org.jdag.graph;

import java.util.Collections;

import org.jdag.io.IOKey;

/**
 * Special vertex that is used for representing the input data to be processed.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class InputVertex extends SimpleVertex
{
    /**
     * CTOR
     */
    public InputVertex()
    {
         super();
    }

    /**
     * CTOR
     */
      public InputVertex(VertexID id, IOKey key)
      {
          super(id,  
                "", 
                Collections.<IOKey>emptyList(), 
                Collections.singletonList(key));
      }
}
