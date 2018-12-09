package goliathoufx.relationships;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Relationship
{
    private final Cause cause;
    private final Affect affect;
    private final ChangeListener<Integer> listener;
    
    public Relationship(Cause cse, Affect afct)
    {
        cause = cse;
        affect = afct;
        listener = new ConditionMetListener();
        
        cause.getReadables().valueProperty().addListener(new ConditionMetListener());
    }
    
    public void begin()
    {
        cause.getReadables().valueProperty().addListener(listener);
    }
    
    public void end()
    {
        cause.getReadables().valueProperty().removeListener(listener);
    }
    
    private class ConditionMetListener implements ChangeListener<Integer>
    {

        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
        {
            if(cause.getCondition().conditionMet())
                affect.performAffect();
        }
        
    }
}
