package goliathoufx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import goliathoufx.customtabs.NotifyTab;
import goliathoufx.customtabs.PromptTab;
import goliathoufx.panes.AppTabPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GenericControllableSliderBox extends VBox
{
    private final HBox buttonBox;
    private final LabeledSlider slider;
    private final Button apply;
    private final ReadOnlyNvReadable<Integer> readable;
    private Button reset;
    
    public GenericControllableSliderBox(ReadOnlyNvReadable<Integer> rdbl)
    {
        super();
        super.setPadding(new Insets(10,10,10,10));
        
        readable = rdbl;
        readable.valueProperty().addListener(new ValueListener());
        
        buttonBox = new HBox();

        slider = new LabeledSlider(readable, false);

        apply = new Button("Apply");
        reset = new Button("Reset");
        
        buttonBox.setSpacing(10);
        
        apply.setPrefWidth(100);
        apply.setOnMouseClicked(new ApplyHandler());
        
        reset = new Button("Reset");
        reset.setPrefWidth(100);
        reset.setOnMouseClicked(new ResetHandler());

        if(!rdbl.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            apply.setDisable(true);
            reset.setDisable(true);
        }

        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.getChildren().add(slider);
        super.getChildren().add(buttonBox);
    }

    public int getCurrentValue()
    {
        return (int)slider.getSlider().getValue();
    }
    
    private class ValueListener implements ChangeListener<Integer>
    {
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
        {
            slider.getSlider().setValue(newValue);
            slider.getTextBox().setText(newValue.toString());
        }
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        private PromptTab tab;
        
        @Override
        public void handle(MouseEvent event)
        {
            tab = new PromptTab();
            tab.setHeaderText("Are you sure you would like to make this change?");
            tab.setDescText(readable.displayNameProperty().get() + " will be changed from " + readable.getCurrentValue() + " to " + (int)slider.getSlider().getValue() + ".");
            
            tab.setYesButtonHandler(new YesHandler());
            tab.setNoButtonHandler(new NoHandler());
            
            AppTabPane.getTabPane().showPromptTab(tab);
        }
        
        private class YesHandler implements EventHandler<MouseEvent>
        {   
            @Override
            public void handle(MouseEvent event)
            {
                Integer val = readable.getCurrentValue();
                AppTabPane.getTabPane().removeSpecialTab();
                try
                {
                    readable.getController().get().setValue((int)slider.getSlider().getValue());
                    
                    NotifyTab notify = new NotifyTab();
                    notify.setTabText("Notification");
                    notify.setHeaderText("Value changed for " + readable.displayNameProperty().get() + ".");
                    notify.setDescText("Value changed from " + val + " to " + readable.getCurrentValue() + ".");
                    AppTabPane.getTabPane().showNotifyTab(notify);
                    
                }
                catch (ValueSetFailedException ex)
                {
                    NotifyTab notify = new NotifyTab();
                    notify.setTabText("Notification");
                    notify.setHeaderText("Failed to set value for " + readable.displayNameProperty().get() + ".");
                    notify.setDescText("Reason: " + ex.getLocalizedMessage() + ".");
                    AppTabPane.getTabPane().showNotifyTab(notify);
                }
            }
        }
        
        private class NoHandler implements EventHandler<MouseEvent>
        {
            @Override
            public void handle(MouseEvent event)
            {
                AppTabPane.getTabPane().removeSpecialTab();
            }
        }
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            int val = readable.getCurrentValue();
            try
            {
                readable.getController().get().reset();
                
                NotifyTab notify = new NotifyTab();
                notify.setTabText("Notification");
                notify.setHeaderText(readable.displayNameProperty().get() + " has been reset.");
                notify.setDescText("Value changed from " + val + " to " + readable.getCurrentValue() + ".");
                AppTabPane.getTabPane().showNotifyTab(notify);
            }
            catch (ControllerResetFailedException ex)
            {
                NotifyTab notify = new NotifyTab();
                notify.setTabText("Notification");
                notify.setHeaderText("Failed to reset " + readable.displayNameProperty().get() + ".");
                notify.setDescText("Reason: " + ex.getLocalizedMessage() + ".");
                AppTabPane.getTabPane().showNotifyTab(notify);
            }
            slider.getSlider().setValue(readable.getCurrentValue());
            slider.getTextBox().setText(String.valueOf(readable.getCurrentValue()));
        }
    }
}
