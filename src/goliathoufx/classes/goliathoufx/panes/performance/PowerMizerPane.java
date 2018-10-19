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

import goliath.nvsettings.enums.PerformanceLevel;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.panes.performance.powermizer.PerformanceModePane;
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
    
    private final PerformanceModePane performancePane;
    private final NvGPU gpu;
    
    public PowerMizerPane(NvGPU g)
    {
        super();
        
        gpu = g;
        
        table = new TableView<>(FXCollections.observableArrayList(PerformanceLevel.values()));
        table.setMinHeight(125);
        table.setMaxHeight(125);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);
        
        perfLevel = new TableColumn<>("Performance Level");
        perfLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        perfLevel.setSortable(false);
        
        minClock = new TableColumn<>("Min Core(MHz)");
        minClock.setCellValueFactory(new PropertyValueFactory<>("coreMin"));
        minClock.setSortable(false);
        
        maxClock = new TableColumn<>("Max Core(MHz)");
        maxClock.setCellValueFactory(new PropertyValueFactory<>("coreMax"));
        maxClock.setSortable(false);
        
        minMemory = new TableColumn<>("Min Memory(MHz)");
        
        if(true)
            minMemory.setCellValueFactory(new PropertyValueFactory<>("transferMin"));
        else
            minMemory.setCellValueFactory(new PropertyValueFactory<>("minEffectiveMemoryClock"));
        
        minMemory.setSortable(false);
        
        maxMemory = new TableColumn<>("Max Memory(MHz)");
        
        if(true)
            maxMemory.setCellValueFactory(new PropertyValueFactory<>("transferMax"));
        else
            maxMemory.setCellValueFactory(new PropertyValueFactory<>("maxEffectiveMemoryClock"));
        
        maxMemory.setSortable(false);
        table.getColumns().addAll(perfLevel, minClock, maxClock, minMemory, maxMemory);
       
        performancePane = new PerformanceModePane(gpu);
        super.getChildren().addAll(table, performancePane);
    }         
}
