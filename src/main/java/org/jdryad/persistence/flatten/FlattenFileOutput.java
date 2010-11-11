package org.jdryad.persistence.flatten;

import com.google.common.base.Preconditions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jdryad.dag.FunctionOutput;
import org.jdryad.dag.Record;

/**
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class FlattenFileOutput implements FunctionOutput
{
    private final PrintWriter myWriter;

    private final LineInterpreter myLineInterpreter;

    /**
     * CTOR
     */
    public FlattenFileOutput(LineInterpreter lineInterpreter, String fileName)
    {
        myLineInterpreter = lineInterpreter;
        File f = new File(fileName);
        Preconditions.checkArgument(f.exists() && f.canWrite());
        try {
            myWriter =
                new PrintWriter(new BufferedWriter(new FileWriter(f)));
        }
        catch (IOException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void done()
    {
        myWriter.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Record r)
    {
        myWriter.println(myLineInterpreter.flattenRecord(r));
    }

}
