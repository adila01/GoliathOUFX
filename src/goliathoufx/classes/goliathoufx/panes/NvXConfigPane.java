package goliathoufx.panes;

import goliath.nvsettings.exceptions.ControllerResetFailedException;
import goliath.nvsettings.exceptions.ValueSetFailedException;
import goliath.nvxconfig.NvXConfig;
import goliath.nvxconfig.enums.Coolbits;
import goliathoufx.custom.Space;
import goliathoufx.customtabs.NotifyTab;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NvXConfigPane extends VBox
{
    private final ComboBox<Coolbits> bitBox;
    
    public NvXConfigPane()
    {
        super();
        super.setPadding(new Insets(8,8,8,8));
        super.setSpacing(8);
        
        bitBox = new ComboBox(FXCollections.observableArrayList(Coolbits.values()));
        bitBox.getSelectionModel().selectFirst();
        
        Space space = new Space();
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(1);
        space.setMaxHeight(1);
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8);
        
        Button applyButton = new Button("Apply");
        applyButton.setOnMouseClicked(new ApplyHandler());
        
        Button resetButton = new Button("Reset");
        resetButton.setOnMouseClicked(new ResetHandler());
        
        buttonBox.getChildren().add(applyButton);
        buttonBox.getChildren().add(resetButton);
        
        super.getChildren().add(new Label("Coolbits control. A restart is required for a new value to take affect.\n\nCoolbits value of 31 is recommended."));
        super.getChildren().add(space);
        super.getChildren().add(bitBox);
        super.getChildren().add(buttonBox);
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                NvXConfig.getCoolbitsController().setValue(bitBox.getValue());
            }
            catch (ValueSetFailedException ex)
            {
                ex.printStackTrace();
            }
            
            NotifyTab notify = new NotifyTab();
            notify.setTabText("NvXConfig Coolbits");
            notify.setHeaderText("A new coolbits value of" + bitBox.getValue().getBit() + " has been applied.");
            notify.setDescText("You will need to restart your computer for this to take affect.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                NvXConfig.getCoolbitsController().reset();
            }
            catch (ControllerResetFailedException ex)
            {
                Logger.getLogger(NvXConfigPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
