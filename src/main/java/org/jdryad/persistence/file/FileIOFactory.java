package org.jdryad.persistence.file;

import org.jdryad.dag.FunctionInput;
import org.jdryad.dag.FunctionOutput;
import org.jdryad.dag.IOFactory;
import org.jdryad.dag.IOKey;

/**
 * A simple class that takes key to the inputs or outputs and generates
 * appropriate file based input or outputs.
 * 
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class FileIOFactory implements IOFactory
{
    /**
     * {@inheritDoc}
     */
    @Override
    public FunctionInput makeInput(IOKey key)
    {
        return new FileInput(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctionOutput makeOutput(IOKey key)
    {
        return new FileOutput(key);
    }
}