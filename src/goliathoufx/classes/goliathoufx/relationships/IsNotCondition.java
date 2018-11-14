package goliathoufx.relationships;

import goliathoufx.interfaces.Condition;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.beans.value.ObservableValue;

public class IsNotCondition implements Condition
{
    private final ReadOnlyNvReadable<Integer> readable;
    private final ObservableValue<Integer> value;
    
    public IsNotCondition(ReadOnlyNvReadable<Integer> rdbl, ObservableValue<Integer> val)
    {
        readable = rdbl;
        value = val;
    }
    
    @Override
    public boolean conditionMet()
    {
        if(!readable.getCurrentValue().equals(value.getValue()))
            return true;
        
        return false;
    }

    @Override
    public String getConditionName()
    {
        return "Is Not";
    }
    
}
