package goliathoufx.panes.performance;

import goliath.nvsettings.exceptions.ControllerResetFailedException;
import goliath.nvsettings.exceptions.ValueSetFailedException;
import goliath.nvsettings.interfaces.NvAttribute;
import goliath.nvsettings.interfaces.NvControllable;
import goliath.nvsettings.interfaces.NvReadable;
import goliathoufx.custom.LabeledSlider;
import goliathoufx.customtabs.PromptTab;
import goliathoufx.panes.AppTabPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OCPaneTemplate extends VBox
{
    private HBox buttonBox;
    private final LabeledSlider slider;
    private Button apply;
    private NvReadable<Integer> readable;
    private Button reset;
    
    public OCPaneTemplate(NvReadable<Integer> rdbl)
    {
        super();
        super.setPadding(new Insets(10,10,10,10));

        readable = rdbl;
        
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
        
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.getChildren().add(slider);
        super.getChildren().add(buttonBox);
    }

    public int getCurrentValue()
    {
        return (int)slider.getSlider().getValue();
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        private PromptTab tab;
        
        @Override
        public void handle(MouseEvent event)
        {
            tab = new PromptTab();
            tab.setHeaderText("Are you sure you would like to make this change?");
            tab.setDescText("Value will be changed from " + readable.getCurrentValue() + " to " + (int)slider.getSlider().getValue() + ".");
            
            tab.setYesButtonHandler(new YesHandler());
            tab.setNoButtonHandler(new NoHandler());
            
            AppTabPane.getTabPane().showPromptTab(tab);
        }
        
        private class YesHandler implements EventHandler<MouseEvent>
        {
            @Override
            public void handle(MouseEvent event)
            {
                try
                {
                    readable.getController().get().setValue((int)slider.getSlider().getValue());
                }
                catch (ValueSetFailedException ex)
                {
                    Logger.getLogger(OCPaneTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }
                AppTabPane.getTabPane().removeSpecialTab();
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
            
            try
            {
                readable.getController().get().reset();
            }
            catch (ControllerResetFailedException ex)
            {
                Logger.getLogger(OCPaneTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
            slider.getSlider().setValue(readable.getCurrentValue());
            slider.getTextBox().setText(String.valueOf(readable.getCurrentValue()));
        }
    }
}
