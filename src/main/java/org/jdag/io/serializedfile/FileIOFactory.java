package org.jdag.io.serialiazedfile;

import org.jdag.dsl.Input;
import org.jdag.dsl.Output;
import org.jdag.io.IOFactory;
import org.jdag.io.IOKey;

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
    public <T> Input<T> makeInput(IOKey key)
    {
        return new FileInput<T>(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Output<T> makeOutput(IOKey key)
    {
        return new FileOutput<T>(key);
    }
}