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

import goliath.nvsettings.enums.FanMode;
import goliath.nvsettings.exceptions.ValueSetFailedException;
import goliath.nvsettings.gpu.fan.FanProfile;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.InstanceProvider;
import goliathoufx.panes.fan.FanOptionPane;
import goliathoufx.panes.fan.FanProfileEditPane;
import goliathoufx.panes.fan.ProfileContextMenu;
import goliathoufx.threads.FanUpdateThread;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FanProfilePane extends BorderPane
{
    private final FanProfileEditPane editPane;
    private final FanOptionPane optionsPane;
    private final ListView<FanProfile> profileList;
    private final VBox leftPane;
    private final Button applyButton;
    private final NvGPU gpu;
    
    public FanProfilePane(NvGPU g)
    {
        super();
        super.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        super.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        
        gpu = g;
        
        editPane = new FanProfileEditPane(InstanceProvider.getFanProfiles().get(0));
        
        leftPane = new VBox();
        
        profileList = new ListView<>();
        profileList.setEditable(false);
        profileList.setPrefWidth(125);
        profileList.setItems(FXCollections.observableArrayList(InstanceProvider.getFanProfiles()));
        profileList.getSelectionModel().selectFirst();
        profileList.setOnMouseClicked(new ListHandler());
        profileList.setContextMenu(new ProfileContextMenu(profileList));
        
        profileList.setPlaceholder(new Label("No Profiles"));
        
        applyButton = new Button("Apply");
        applyButton.setPrefWidth(125);
        applyButton.setMinHeight(46);
        applyButton.setOnMouseClicked(new ApplyButtonHandler());
        
        leftPane.getChildren().addAll(profileList, applyButton);
        
        optionsPane = new FanOptionPane(gpu);
        
        super.setTop(optionsPane);
        super.setLeft(leftPane);
        super.setCenter(editPane);
    }

    private class ApplyButtonHandler implements EventHandler<MouseEvent>
    {
        private final FanUpdateThread thread;
        
        public ApplyButtonHandler()
        {
            thread = new FanUpdateThread();
        }
        
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                gpu.getFan().getFanTargetSpeed().getController().get().setValue(gpu.getFan().getFanTargetSpeed().getController().get().getMaxValue());
                gpu.getFanMode().getController().get().setValue(FanMode.MANUAL);
            }
            catch (ValueSetFailedException ex)
            {
                ex.printStackTrace();
            }
            
            InstanceProvider.getFanManager().setActiveProfile(profileList.getSelectionModel().getSelectedItem());
            
            gpu.getFanMode().update();
            
            ConsolePane.addText("Current Fan Profile set to: " + InstanceProvider.getFanManager().getCurrentProfile().getName());
            
            if(!thread.isAlive())
            {
                thread.start();
                ConsolePane.addText("Started background fan profile thread.");
            }
        }
    }   
    private class ListHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            InstanceProvider.setCurrentFanProfile(profileList.getSelectionModel().getSelectedItem());
            editPane.setFanProfile(profileList.getSelectionModel().getSelectedItem());
        }
    }
}