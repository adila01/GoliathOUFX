package goliathoufx.custom;

import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.NvControllable;
import goliathoufx.panes.AppTabPane;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;

public class LabeledSlider extends HBox
{
    private final Slider slider;
    private final TextField textBox;
    private final ReadOnlyNvReadable<Integer> attr;
    private final boolean setValue;
    
    public LabeledSlider(ReadOnlyNvReadable<Integer> attribute, boolean setVal)
    {
        super();
        super.autosize();
        super.setSpacing(8);
        
        attr = attribute;
        setValue = setVal;

        slider = new Slider();
        slider.getStyleClass().add("slider");
        slider.setPrefWidth(AppTabPane.CONTENT_WIDTH-75);
        
        if(!attribute.getController().isEmpty())
        {
            slider.setMin(attr.getController().get().getMinValue());
            slider.setMax(attr.getController().get().getMaxValue());
            slider.setValue(attr.getCurrentValue());
        }
        else
        {
            slider.setMin(attr.getCurrentValue());
            slider.setMax(attr.getCurrentValue());
            slider.setValue(attr.getCurrentValue());
        }
        
        slider.setOnMouseReleased(new DefaultContSliderHandler());
        
        textBox = new TextField();
        textBox.setEditable(true);
        textBox.setMinWidth(59);
        textBox.setMaxWidth(59);
        
        textBox.setText(String.valueOf(attr.getCurrentValue()));
        
        textBox.setOnKeyTyped(new DefaultTextBoxHandler());
        
        super.getChildren().add(slider);
        super.getChildren().add(textBox);
    }
    
    public Slider getSlider()
    {
        return slider;
    }
    
    public TextField getTextBox()
    {
        return textBox;
    }
    
    public NvControllable<Integer> getController()
    {
        return attr.getController().get();
    }
    
    private class DefaultContSliderHandler implements EventHandler<Event>
    {
        @Override
        public void handle(Event event)
        {
            textBox.setText(String.valueOf((int)slider.getValue()));
            
            if(!setValue)
                return;
            
            try
            {
                attr.getController().get().setValue((int)slider.getValue());
            }
            catch (ValueSetFailedException ex)
            {
                
            }
        }
    }
    
        private class DefaultTextBoxHandler implements EventHandler<Event>
    {
        @Override
        public void handle(Event event)
        {
            if(textBox.getText().equals(""))
                return;
            
            for(int i = 0; i < textBox.getText().toCharArray().length; i++)
            {
                if(!Character.isDigit(textBox.getText().toCharArray()[i]))
                {
                    textBox.setText(String.valueOf((int)slider.getValue()));
                    return;
                }
            }

            if(!attr.getController().get().getAllValues().isWithinRange(Integer.parseInt(textBox.getText())))
            {
                textBox.setText(String.valueOf((int)slider.getValue()));
                return;
            }
            
            slider.setValue(Integer.valueOf(textBox.getText()));
            
            if(!setValue)
                return;
            
            try
            {
                attr.getController().get().setValue((int)slider.getValue());
            }
            catch (ValueSetFailedException ex)
            {
                
            }
        } 
    }
}
