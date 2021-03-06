package goliathoufx.relationships;

import goliath.envious.interfaces.Range;
import goliathoufx.interfaces.Condition;
import goliath.envious.interfaces.ReadOnlyNvReadable;

public class IsNotWithinCondition implements Condition
{
    private final ReadOnlyNvReadable<Integer> readable;
    private final Range<Integer> range;
    
    public IsNotWithinCondition(ReadOnlyNvReadable<Integer> rdbl, Range<Integer> rng)
    {
        readable = rdbl;
        range = rng;
    }
    
    @Override
    public boolean conditionMet()
    {
        if(!range.isWithinRange(readable.getCurrentValue()))
            return true;
        
        return false;
    }

    @Override
    public String getConditionName()
    {
        return "Is Not Within";
    }
    
}
