package goliathoufx.relationships;

import goliathoufx.interfaces.Condition;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.beans.value.ObservableValue;

public class IsGreaterCondition implements Condition
{
    private final ReadOnlyNvReadable<Integer> readable;
    private final ObservableValue<Integer> value;
    
    public IsGreaterCondition(ReadOnlyNvReadable<Integer> rdbl, ObservableValue<Integer> val)
    {
        readable = rdbl;
        value = val;
    }
    
    @Override
    public boolean conditionMet()
    { 
        if(readable.getCurrentValue() > value.getValue())
            return true;
        return false;
    }

    @Override
    public String getConditionName()
    {
        return "Is Greater";
    }
    
}
