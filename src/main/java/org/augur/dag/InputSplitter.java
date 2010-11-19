package org.augur.dag;

import java.util.List;

/**
 * Defines the contract for a function that splits the input across multiple
 * small output.
 *
 * @author Balraja Subbiah
 * @version $Id:$
 */
public interface InputSplitter
{
    /** Returns the IOKey corresponding to output */
    public IOKey getOutput(Record record,
                           int position,
                           IOKey input,
                           List<IOKey> outputs);
}