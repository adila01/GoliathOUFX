package goliathoufx.relationships;

import goliathoufx.interfaces.Condition;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.beans.value.ObservableValue;

public class IsCondition<E> implements Condition
{
    private final ReadOnlyNvReadable<E> readable;
    private final ObservableValue<E> value;
    
    public IsCondition(ReadOnlyNvReadable<E> rdbl, ObservableValue<E> val)
    {
        readable = rdbl;
        value = val;
    }
    
    @Override
    public boolean conditionMet()
    {
        if(readable.getCurrentValue().equals(value.getValue()))
            return true;
        return false;
    }

    @Override
    public String getConditionName()
    {
        return "Is";
    }
    
}
