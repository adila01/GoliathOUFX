/* 
 * The MIT License
 *
 * Copyright 2018 Ty Young.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliathoufx.panes;

import goliathoufx.custom.Space;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.InstanceProvider;
import goliathoufx.osd.OSDStage;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;

public class OSDPane extends VBox
{
    private static ListView<ReadOnlyNvReadable> staticOnList;
    
    private final OSDLaunchPane launchPane;

    private final ListView<ReadOnlyNvReadable> fullList, onList;
    private final Button fullToOn;
    private final Button onToOff;
    
    
    public static ListView<ReadOnlyNvReadable> getOnList()
    {
        return staticOnList;
    }
    
    public OSDPane()
    {
        super();
        super.getStyleClass().add("hbox");
        super.setSpacing(8);
        super.setHeight(AppTabPane.CONTENT_HEIGHT);
        super.setWidth(AppTabPane.CONTENT_WIDTH);    
        
        HBox osdSelectionBox = new HBox();
        osdSelectionBox.setPadding(new Insets(8,8,0,7));
        osdSelectionBox.setSpacing(25);
        
        List<ReadOnlyNvReadable> rds = new ArrayList<>();
        rds.addAll(NvSettings.getPrimaryGPU().getNvReadables());
        rds.add(NvSettings.getDriverVersion());
        rds.add(NvSettings.getPrimaryGPU().getFan().getFanTargetSpeed());
        rds.add(NvSettings.getPrimaryGPU().getFan().getFanRPMSpeed());
        rds.add(NvSMI.getDefaultPowerLimit());
        rds.add(NvSMI.getPowerLimit());
        rds.add(NvSMI.getPerformanceLimit());
        rds.add(NvSMI.getPowerDraw());
        
        fullList = new ListView<>(FXCollections.observableArrayList(rds));
        fullList.setPrefWidth(323);
        fullList.getSelectionModel().selectFirst();
        
        VBox buttonBox = new VBox();
        buttonBox.setPadding(new Insets(8,0,0,0));
        buttonBox.setSpacing(8);
        
        fullToOn = new Button("-->");
        fullToOn.setOnMouseClicked(new OnMouseEvent());
        
        onToOff = new Button("<--");
        onToOff.setOnMouseClicked(new OffMouseEvent());
        
        buttonBox.getChildren().add(fullToOn);
        buttonBox.getChildren().add(onToOff);
        
        onList = new ListView<>(FXCollections.observableArrayList(InstanceProvider.getOSDAttributes()));
        onList.setPrefWidth(323);
        onList.getSelectionModel().selectFirst();
        
        staticOnList = onList;
        
        osdSelectionBox.getChildren().add(fullList);
        osdSelectionBox.getChildren().add(buttonBox);
        osdSelectionBox.getChildren().add(onList);
        
        Space space = new Space(false);
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(1);
        space.setMaxHeight(1);
        
        launchPane = new OSDLaunchPane();
        
        super.getChildren().add(osdSelectionBox);
        super.getChildren().add(space);
        super.getChildren().add(launchPane);
    }
    
    private class OSDLaunchPane extends VBox
    {
        private OSDStage osd;
        private final Spinner<Integer> xLoc, yLoc;
        
        public OSDLaunchPane()
        {
            super.setSpacing(8);
            
            HBox optionsBox = new HBox();
            optionsBox.setPadding(new Insets(0,0,0,8));
            optionsBox.setSpacing(8);
            
            xLoc = new Spinner<>();
            xLoc.setPrefWidth(85);
            xLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1920));

            yLoc = new Spinner<>();
            yLoc.setPrefWidth(85);
            yLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1080));
            
            TextField colorField = new TextField("#00FF00");
            colorField.setPrefWidth(100);
            colorField.textProperty().addListener(new ColorListener());
            
            optionsBox.getChildren().addAll(new Label("X Location:"), xLoc, new Space(false), new Label("Y Location:"), yLoc);
            //optionsBox.getChildren().addAll(new Space(false), new Label("Color:"), colorField);
            
            HBox buttonBox = new HBox();
            buttonBox.setPadding(new Insets(0,0,10,10));
            buttonBox.setSpacing(8);
            
            Space space = new Space(false);
            space.setMinWidth(AppTabPane.CONTENT_WIDTH);
            space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
            space.setMinHeight(1);
            space.setMaxHeight(1);
            
            Button enableButton = new Button("Enable");
            enableButton.setPrefWidth(100);
            enableButton.setOnMouseClicked(new LaunchButtonHandler());
            
            buttonBox.getChildren().add(enableButton);
            
            super.getChildren().addAll(optionsBox, space, buttonBox);
            
        }
        
        private class ColorListener implements ChangeListener<String>
        {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if(osd != null)
                    osd.setColor(newValue);
            }
        }
        
        private class LaunchButtonHandler implements EventHandler<MouseEvent>
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(osd == null)
                    osd = new OSDStage();
                
                osd.setX(xLoc.getValue());
                osd.setY(yLoc.getValue());
                osd.show();
            }
        }
    }
    
    
    private class OnMouseEvent implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent event)
        {
            if(onList.getItems().size() == 15)
                return;
            
            if(onList.getItems().contains(fullList.getSelectionModel().getSelectedItem()))
                return;
                
            if(onList.getItems().isEmpty() || onList.getItems().size() == 1)
            {
                onList.getItems().add(fullList.getSelectionModel().getSelectedItem());
                onList.getSelectionModel().selectFirst();
            }
            else
            {
                onList.getItems().add(onList.getSelectionModel().getSelectedIndex(), fullList.getSelectionModel().getSelectedItem());
                onList.getSelectionModel().select(onList.getSelectionModel().getSelectedIndex());
            }
        }
    }
    
    private class OffMouseEvent implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(onList.getSelectionModel().isEmpty())
                return;
            
            int index = onList.getSelectionModel().getSelectedIndex();
            onList.getItems().remove(index);
            
            if(onList.getSelectionModel().getSelectedIndex() < index)
                onList.getSelectionModel().select(index-1);
            else
                onList.getSelectionModel().select(index+1);
        }
    }
}
