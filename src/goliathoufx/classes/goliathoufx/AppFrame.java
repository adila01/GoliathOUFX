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
package goliathoufx;
import goliath.nvsettings.enums.OperationalStatus;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.customtabs.NotifyTab;
import goliathoufx.menu.AppMenu;
import goliathoufx.panes.AppTabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppFrame extends VBox
{
    private final AppMenu appMenu;
    private final AppTabPane tabPanel;
    
    public AppFrame(Stage appStage)
    {
        super();
        super.getChildren().add(new TitlePane("Goliath Overclocking Utility V1.02 - " + NvSettings.getPrimaryGPU().nameProperty().get() + " - Nvidia " + NvSettings.getDriverVersion().getCurrentValue(), appStage));
        //TabPanel - has to be set up first before Menu for the "Tabs" menu item.
        tabPanel = new AppTabPane();
        
        // Menu
        appMenu = new AppMenu(tabPanel);
        
        super.getChildren().addAll(appMenu, tabPanel);

        if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.ROOT_REQUIRED))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("Running GoliathOUFX as a normal user.");
            notify.setDescText("Some features will be disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
    }
}