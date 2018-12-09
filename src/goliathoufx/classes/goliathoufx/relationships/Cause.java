package goliathoufx.relationships;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathoufx.interfaces.Condition;

public class Cause
{
    private final ReadOnlyNvReadable<Integer> readable;
    private final Condition condition;
    
    public Cause(Condition cond, ReadOnlyNvReadable<Integer> rdbl)
    {
        readable = rdbl;
        condition = cond; 
    }
    
    public ReadOnlyNvReadable<Integer> getReadables()
    {
        return readable;
    }
    
    public Condition getCondition()
    {
        return condition;
    }
}
