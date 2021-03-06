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
package goliathoufx.panes.performance;

import goliath.nvsettings.main.NvSettings;
import goliath.nvsettings.performance.PerformanceLevel;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.custom.Space;
import goliathoufx.panes.AppTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class PowerMizerPane extends VBox
{
    private final TableView<PerformanceLevel> table;
    private final TableColumn<PerformanceLevel, Integer> perfLevel;
    private final TableColumn<PerformanceLevel, Integer> minClock;
    private final TableColumn<PerformanceLevel, Integer> maxClock;
    private final TableColumn<PerformanceLevel, Integer> minMemory;
    private final TableColumn<PerformanceLevel, Integer> maxMemory;
    private final NvGPU gpu;
    
    public PowerMizerPane(NvGPU g)
    {
        super();
        gpu = g;

        table = new TableView<>(FXCollections.observableArrayList(g.getPerfModes().getCurrentValue()));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMouseTransparent(true);
        table.setEditable(false);
        
        perfLevel = new TableColumn<>("Performance Level");
        perfLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        perfLevel.setEditable(false);
        perfLevel.setSortable(false);
        
        minClock = new TableColumn<>("Min Core(MHz)");
        minClock.setCellValueFactory(new PropertyValueFactory<>("coreMin"));
        minClock.setEditable(false);
        minClock.setSortable(false);
        
        maxClock = new TableColumn<>("Max Core(MHz)");
        maxClock.setEditable(false);
        maxClock.setCellValueFactory(new PropertyValueFactory<>("coreMax"));
        maxClock.setSortable(false);
        
        minMemory = new TableColumn<>("Min Memory(MHz)");
        
        if(NvSettings.getDisableExtraAbstraction())
            minMemory.setCellValueFactory(new PropertyValueFactory<>("transferMin"));
        else
            minMemory.setCellValueFactory(new PropertyValueFactory<>("effectiveMin"));
        
        minMemory.setEditable(false);
        minMemory.setSortable(false);
        
        maxMemory = new TableColumn<>("Max Memory(MHz)");
        maxMemory.setEditable(false);
        
        if(NvSettings.getDisableExtraAbstraction())
            maxMemory.setCellValueFactory(new PropertyValueFactory<>("transferMax"));
        else
            maxMemory.setCellValueFactory(new PropertyValueFactory<>("effectiveMax"));
        
        maxMemory.setSortable(false);
        table.getColumns().addAll(perfLevel, minClock, maxClock, minMemory, maxMemory);
       
        Space space = new Space(true);
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(2);
        space.setMaxHeight(2);
                
        gpu.getCurrentPerformanceLevel().valueProperty().addListener(new ValueListener());
        table.getSelectionModel().select(gpu.getCurrentPerformanceLevel().getCurrentValue());
        
        super.getChildren().addAll(table, space);
    }
    
    private class ValueListener implements ChangeListener<PerformanceLevel>
    {
        @Override
        public void changed(ObservableValue<? extends PerformanceLevel> observable, PerformanceLevel oldValue, PerformanceLevel newValue)
        {
            table.getSelectionModel().select(gpu.getCurrentPerformanceLevel().getCurrentValue());
        }
    }
}
