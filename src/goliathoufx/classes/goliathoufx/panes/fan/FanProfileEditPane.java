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
package goliathoufx.panes.fan;

import goliath.nvsettings.gpu.fan.FanNode;
import goliath.nvsettings.gpu.fan.FanProfile;
import goliathoufx.InstanceProvider;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FanProfileEditPane extends VBox
{
    private final TableView<FanNode> tempTable;
    private final TableColumn<FanNode, Integer> tempCol;
    private final TableColumn<FanNode, Integer> speedCol;
    private final ContextMenu menu;
    private final HBox tempEditOpts;
    private final Label tempLabel, speedLabel;
    private final Spinner<Integer> tempSpinner, speedSpinner;
    private final Button apply;
    private final HBox tempBox, speedBox;
    
    public FanProfileEditPane(FanProfile file)
    {
        super();

        tempCol = new TableColumn<>("Temperature(C)");
        tempCol.setCellValueFactory(new PropertyValueFactory<>("temp"));
        tempCol.setSortable(true);
        tempCol.setSortType(TableColumn.SortType.ASCENDING);
        
        speedCol = new TableColumn<>("Speed(%)");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        
        tempTable = new TableView<>();
        tempTable.setMinWidth(625);
        tempTable.setMaxWidth(625);
        tempTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tempTable.setItems(FXCollections.observableArrayList(InstanceProvider.getCurrentFanProfile().getNodes()));
        tempTable.getSortOrder().add(tempCol);
        tempTable.getColumns().addAll(tempCol, speedCol);
        
        menu = new ContextMenu();
        menu.getItems().add(new DeleteItem());
        tempTable.setContextMenu(menu);
        
        tempBox = new HBox();
        tempBox.getStyleClass().add("hbox");
        tempBox.setSpacing(5);
        
        speedBox = new HBox();
        speedBox.getStyleClass().add("hbox");
        speedBox.setSpacing(5);
        
        tempLabel = new Label("Temperature(C):");
        speedLabel = new Label("Speed(%):");
        
        tempSpinner = new Spinner<>();
        tempSpinner.setPrefWidth(75);
        tempSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 5));
        
        speedSpinner = new Spinner<>();
        speedSpinner.setPrefWidth(75);
        speedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 5));
        
        tempBox.getChildren().addAll(tempLabel, tempSpinner);
        speedBox.getChildren().addAll(speedLabel, speedSpinner);
        
        apply = new Button("Add / Edit");
        apply.setPrefWidth(90); 
        apply.setOnMouseClicked(new ApplyButtonHandler());
        
        tempEditOpts = new HBox();
        tempEditOpts.getStyleClass().add("hbox");
        tempEditOpts.setPadding(new Insets(10,10,10,10));
        tempEditOpts.setSpacing(16);
        tempEditOpts.getChildren().addAll(tempBox, speedBox, apply);
        
        super.getChildren().addAll(tempTable, tempEditOpts); 
    }
    private class DeleteItem extends MenuItem
    {
        public DeleteItem()
        {
            super();
            super.setText("Delete");
            super.setOnAction(new DeleteAction());
        }
        
        private class DeleteAction implements EventHandler<ActionEvent>
        {
            @Override
            public void handle(ActionEvent event)
            {
                InstanceProvider.getCurrentFanProfile().getNodes().remove(tempTable.getSelectionModel().getSelectedItem());
                tempTable.getItems().remove(tempTable.getSelectionModel().getSelectedItem());
                tempTable.sort();
            }
        }
    }
    private class ApplyButtonHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            FanNode newNode;
            for(int i = 0; i < InstanceProvider.getCurrentFanProfile().getNodes().size(); i++)
            {
                if(tempSpinner.getValue().equals(InstanceProvider.getCurrentFanProfile().getNodes().get(i).tempProperty().getValue()))
                {
                    tempTable.getItems().remove(InstanceProvider.getCurrentFanProfile().getNodes().get(i));
                    InstanceProvider.getCurrentFanProfile().getNodes().remove(i);
                    InstanceProvider.getCurrentFanProfile().reSortNodes();
                }
            }
            newNode = new FanNode(tempSpinner.getValue(), speedSpinner.getValue());
            InstanceProvider.getCurrentFanProfile().addNode(newNode);

            tempTable.getItems().add(newNode);
            tempTable.sort();
        }
    }
    public void setFanProfile(FanProfile file)
    {
        tempTable.setItems(FXCollections.observableArrayList(InstanceProvider.getCurrentFanProfile().getNodes()));
        tempTable.sort();
    }
}
