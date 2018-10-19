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

import goliathoufx.panes.monitoring.CoreUsageMonitorPane;
import goliathoufx.panes.monitoring.MemoryUsageMonitorPane;
import goliathoufx.panes.monitoring.TempMonitorPane;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MonitoringPane extends TabPane
{   
    public MonitoringPane()
    {
        super();
        
        super.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        super.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        super.setSide(Side.BOTTOM);
        
        Tab[] tabs = new Tab[3];
        
        tabs[0] = new Tab("Core Usage");
        tabs[0].setContent(new CoreUsageMonitorPane());
        
        tabs[1] = new Tab("Memory Usage");
        tabs[1].setContent(new MemoryUsageMonitorPane());
        
        tabs[2] = new Tab("Temperature");
        tabs[2].setContent(new TempMonitorPane());
        
        for(int i = 0; i < tabs.length; i++)
            tabs[i].setClosable(false);
        
        super.getTabs().addAll(tabs);
    }
}
