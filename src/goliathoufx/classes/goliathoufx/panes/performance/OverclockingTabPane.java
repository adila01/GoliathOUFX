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

import goliath.nvsettings.enums.OperationalStatus;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.panes.AppTabPane;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class OverclockingTabPane extends TabPane
{
    
    private final ArrayList<Tab> tabs;
    private final NvGPU gpu;
    
    public OverclockingTabPane(NvGPU g)
    {
        super();
        super.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        super.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        
        gpu = g;
        tabs = new ArrayList<>();

        tabs.add(new Tab("Core Offset(Mhz)"));
        tabs.get(0).setContent(new OCPaneTemplate(gpu.getCoreOffset()));

        tabs.add(new Tab("Memory Offset(Mhz)"));
        tabs.get(tabs.size()-1).setContent(new OCPaneTemplate(gpu.getMemoryOffset()));
        
        if(NvSettings.getPrimaryGPU().getVoltageOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            tabs.add(new Tab("Voltage Offset(uV)"));
            tabs.get(tabs.size()-1).setContent(new OCPaneTemplate(gpu.getVoltageOffset()));
        }
        
        /*
        tabs.add(new Tab("Core Offset(MHz)"));
        tabs.get(0).setContent(corePane);
        
        tabs.add(new Tab("Memory Offset(MHz)"));
        tabs.get(tabs.size()-1).setContent(memoryPane);
        
        if(gpu.getVoltageOffsetController().isAvailable())
        {
            voltagePane = new VoltageOffsetPane(gpu);
            tabs.add(new Tab("Voltage Offset(mV)"));
            tabs.get(tabs.size()-1).setContent(voltagePane);
        }
          
        if(NvGPU.getPowerLimitController().isAvailable())
        {
            powerPane = new PowerLimitPane();
            tabs.add(new Tab("Power Limit(W)"));
            tabs.get(tabs.size()-1).setContent(powerPane);
        }
        */
        super.getTabs().addAll(tabs);
    }
}
