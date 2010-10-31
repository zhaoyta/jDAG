package org.jdryad.dag;

import java.util.HashMap;
import java.util.Map;

/**
 * Type to be used for representing the keys used for identifying the
 * i/p or o/p sources.
 * 
 * @author Balraja Subbiah
 * @version $Id:$
 */
public class IOKey
{
    /** An enum to denote the type of the i/p or o/p source */
    public static enum SourceType {
        FILE(1),
        DB(2),
        CASSANDRA(3);
        
        private static Map<Integer, SourceType> ourID2SrcTypeMap =
        	new HashMap<Integer, SourceType>();
        
        static {
        	for (SourceType src : values()) {
        		ourID2SrcTypeMap.put(src.getTypeID(), src);
        	}
        }
        
        /** Returns the type by identifier */
        public static SourceType getTypeByID(int identifier)
        {
        	return ourID2SrcTypeMap.get(Integer.valueOf(identifier));
        }
        
        private int myTypeId;
        
        SourceType(int typeId) 
        {
        	myTypeId = typeId;
		}   
        
        public int getTypeID()
        {
        	return myTypeId;
        }
    }
    
    private final SourceType mySourceType;
    
    private final String myIdentifier;

    /**
     * CTOR
     */
    public IOKey(SourceType sourceType, String identifier)
    {
        super();
        mySourceType = sourceType;
        myIdentifier = identifier;
    }

    /**
     * Returns the value of sourceType
     */
    public SourceType getSourceType()
    {
        return mySourceType;
    }

    /**
     * Returns the value of identifier
     */
    public String getIdentifier()
    {
        return myIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((myIdentifier == null) ? 0 : myIdentifier.hashCode());
        result = prime * result
                + ((mySourceType == null) ? 0 : mySourceType.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IOKey other = (IOKey) obj;
        if (myIdentifier == null) {
            if (other.myIdentifier != null)
                return false;
        }
        else if (!myIdentifier.equals(other.myIdentifier))
            return false;
        if (mySourceType == null) {
            if (other.mySourceType != null)
                return false;
        }
        else if (!mySourceType.equals(other.mySourceType))
            return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "Key [myIdentifier=" + myIdentifier + ", mySourceType="
                + mySourceType + "]";
    }
    
}
