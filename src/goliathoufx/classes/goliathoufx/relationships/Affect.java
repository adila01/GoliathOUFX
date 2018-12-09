package goliathoufx.relationships;

import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.NvControllable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Affect
{
    private final IntegerProperty newValue;
    private final ObjectProperty<NvControllable<Integer>> controller;
    
    public Affect(NvControllable<Integer> cont, IntegerProperty intProp)
    {
        controller = new SimpleObjectProperty<>(cont);
        newValue = intProp;
    }
    
    public void performAffect()
    {
        try
        {
            controller.get().setValue(newValue.get());
        }
        catch (ValueSetFailedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public IntegerProperty getNewValue()
    {
        return newValue;
    }
    
    public ObjectProperty<NvControllable<Integer>> getController()
    {
        return controller;
    }
}
