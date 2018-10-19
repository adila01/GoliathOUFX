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

import goliath.nvsettings.interfaces.NvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.InstanceProvider;
import goliathoufx.osd.OSDStage;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OSDPane extends VBox
{
    private static ListView<NvReadable> staticOnList;
    
    private final OSDLaunchPane launchPane;

    private final ListView<NvReadable> fullList, onList;
    private final Button fullToOn;
    private final Button onToOff;
    
    
    public static ListView<NvReadable> getOnList()
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
        
        List<NvReadable> rds = new ArrayList<>();
        rds.addAll(NvSettings.getPrimaryGPU().getAttributes());
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
        
        Space space = new Space();
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
        
        public OSDLaunchPane()
        {
            super.setPadding(new Insets(0,0,8,8));
            super.setSpacing(8);
            
            Button enableButton = new Button("Enable");
            enableButton.setOnMouseClicked(new LaunchButtonHandler());
            
            super.getChildren().add(enableButton);
            super.getChildren().add(new Label("NOTICE: Use of the OSD WILL cause performance issues."));
            
        }
        
        private class LaunchButtonHandler implements EventHandler<MouseEvent>
        {
            public void changed(ObservableValue<? extends StringProperty> observable, StringProperty oldValue, StringProperty newValue)
            {
                if(newValue.getValue().equals("On"))
                    osd = new OSDStage();
                osd.show();
            }

            @Override
            public void handle(MouseEvent event)
            {
                if(osd == null)
                    osd = new OSDStage();
                
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
                
            onList.getItems().add(onList.getSelectionModel().getSelectedIndex(), fullList.getSelectionModel().getSelectedItem());
        }
    }
    
    private class OffMouseEvent implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            int index = onList.getSelectionModel().getSelectedIndex();
            onList.getItems().remove(index);
            
            if(onList.getSelectionModel().getSelectedIndex() < index)
                onList.getSelectionModel().select(index-1);
            else
                onList.getSelectionModel().select(index+1);
        }
    }
}
