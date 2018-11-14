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
import goliath.envious.enums.OperationalStatus;
import goliath.envious.interfaces.NvControllable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.customtabs.NotifyTab;
import goliathoufx.menu.AppMenu;
import goliathoufx.panes.AppTabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import java.util.ArrayList;
import java.util.List;

public class AppFrame extends VBox
{
    private final AppMenu appMenu;
    private final AppTabPane tabPanel;
    
    public AppFrame(Stage appStage)
    {
        super();
        super.getChildren().add(new TitlePane("Goliath Overclocking Utility V2 - " + NvSettings.getPrimaryGPU().nameProperty().get() + " - Nvidia " + NvSettings.getDriverVersion().getCurrentValue(), appStage));
        //TabPanel - has to be set up first before Menu for the "Tabs" menu item.
        tabPanel = new AppTabPane();
        
        // Menu
        appMenu = new AppMenu(tabPanel);
        
        super.getChildren().addAll(appMenu, tabPanel);

        List<NvControllable> conts = List.copyOf(NvSettings.getPrimaryGPU().getControllableAttributes());

        String contList = "";

        for(int i = 0; i < conts.size(); i++)
        {
            if(conts.get(i).getOperationalStatus().equals(OperationalStatus.READABLE) || conts.get(i).getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED))
                contList += "\n" + conts.get(i).getControlName();
        }
        
        if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED) || NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE))
            contList += "\n" + NvSMI.getPowerLimit().getControlName();
            
        if(System.getProperty("user.name").equals("root") && !contList.equals(""))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read one or more controllables.\n\nThis could be due to coolbits not being set or not being supported.");
            notify.setDescText("The following is a list of these attributes:\n" + contList);
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        else if(!contList.equals(""))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read one or more controllables.\n\nThis could be due to coolbits not being set, not being supported, or not running as root.");
            notify.setDescText("The following is a list of these attributes:\n" + contList);
            AppTabPane.getTabPane().showNotifyTab(notify);
        }

        /*
        if((fanMode.getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED) ||voltage.getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED) || core.getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED)) && power.getOperationalStatus().equals(OperationalStatus.READABLE))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read one or more attributes from nvidia-settings.");
            notify.setDescText("This is likely because a valid cool-bits value is not set or it is not supported by this GPU.\n\nRunning as normal user. Some features require root.\n\nSome features disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        else if(NvSettings.getPrimaryGPU().getCoreOffset().getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read core offset from nvidia-settings.");
            notify.setDescText("This is likely because a valid cool-bits value is not set.\n\nCore and Memory Overclocking is disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        else if(NvSettings.getPrimaryGPU().getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read Fan Mode from nvidia-settings.");
            notify.setDescText("This is likely because a valid cool-bits value is not set.\n\nFan Control is disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        else if(NvSettings.getPrimaryGPU().getVoltageOffset().getOperationalStatus().equals(OperationalStatus.NOT_SUPPORTED))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("GoliathENVIOUS API was unable to read voltage offset from nvidia-settings.");
            notify.setDescText("This is likely because a valid cool-bits value is not set or is not supported by this GPU.\n\nVoltage offset is disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        else if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE))
        {
            NotifyTab notify = new NotifyTab();
            notify.setTabText("Warning");
            notify.setHeaderText("Running GoliathOUFX as a normal user.");
            notify.setDescText("Some features will be disabled.");
            AppTabPane.getTabPane().showNotifyTab(notify);
        }
        */
    }
}