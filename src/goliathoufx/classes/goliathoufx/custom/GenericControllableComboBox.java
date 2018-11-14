package goliathoufx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import goliathoufx.customtabs.NotifyTab;
import goliathoufx.panes.AppTabPane;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GenericControllableComboBox<E> extends HBox
{
    private final ComboBox<E> combo;
    private final Button applyButton;
    private final Button resetButton;
    private final ReadOnlyNvReadable<E> readable;
    
    public GenericControllableComboBox(ReadOnlyNvReadable<E> rdbl)
    {
        super();
        super.setPadding(new Insets(22,10,10,10));
        super.setSpacing(10);
        
        readable = rdbl;
        readable.valueProperty().addListener(new ValueListener());
        
        combo = new ComboBox<>();
        combo.setItems(FXCollections.observableArrayList(rdbl.getController().get().getAllValues().getAllInRange()));
        combo.getSelectionModel().select(rdbl.getCurrentValue());
        
        applyButton = new Button("Apply");
        applyButton.setOnMouseClicked(new ApplyHandler());
        applyButton.setPrefWidth(100);
        
        if(!readable.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            applyButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setOnMouseClicked(new ResetHandler());
        resetButton.setPrefWidth(100);

        if(!readable.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            resetButton.setDisable(true);
        
        super.getChildren().addAll(combo, applyButton, resetButton);
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            E val = readable.getCurrentValue();
            try
            {
                readable.getController().get().setValue(combo.getValue());
                
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
                notify.setHeaderText("Failed to change value for " + readable.displayNameProperty().get());
                notify.setDescText("Reason: " + ex.getLocalizedMessage() + ".");
                AppTabPane.getTabPane().showNotifyTab(notify);
            }
        }

    }
    
    private class ValueListener implements ChangeListener<E>
    {
        @Override
        public void changed(ObservableValue<? extends E> observable, E oldValue, E newValue)
        {
            combo.setValue(newValue);
        }
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            E val = readable.getCurrentValue();
            try
            {
                readable.getController().get().reset();
                combo.setValue(readable.getCurrentValue());
                
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
        }

    }
}
