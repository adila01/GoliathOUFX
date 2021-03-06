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

import goliath.envious.enums.OperationalStatus;
import goliath.nvsettings.main.NvSettings;
import goliath.nvxconfig.NvXConfig;
import goliathoufx.customtabs.NotifyTab;
import goliathoufx.customtabs.PromptTab;
import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AppTabPane extends TabPane
{   
    public static final int CONTENT_HEIGHT = 348;
    public static final int CONTENT_WIDTH = 900;
    
    public static boolean POWER_ONLY = false;
    public static boolean BLOCK_TAB_CREATION = false;
    public static boolean SPECIAL_TAB_IN_USE = false;
    
    private static AppTabPane APP_TAB_PANE;
    
    private final TabHandler handler;
    private final ArrayList<Tab> tabs;
    private final ArrayList<Tab> openTabs;
    private Tab activeTab;
    
    
    public static AppTabPane getTabPane()
    {
        return APP_TAB_PANE;
    }
    
    public AppTabPane()
    {
        super();
        super.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        APP_TAB_PANE = this;

        ConsolePane pane = new ConsolePane();
        
        handler = new TabHandler();
        openTabs = new ArrayList<>();
        
        TabPane overviewTabPane = new TabPane();
        overviewTabPane.setMinHeight(AppTabPane.CONTENT_HEIGHT);
        overviewTabPane.setMaxWidth(AppTabPane.CONTENT_HEIGHT);
        overviewTabPane.setMinWidth(AppTabPane.CONTENT_WIDTH);
        overviewTabPane.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        overviewTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        overviewTabPane.setSide(Side.BOTTOM);

        for(int i = 0; i < NvSettings.getGPUS().size(); i++)
        {
            overviewTabPane.getTabs().add(new Tab(NvSettings.getGPUS().get(i).idProperty().get()));
            overviewTabPane.getTabs().get(i).setContent(new GPUOverviewPane(NvSettings.getGPUS().get(i)));
        }
        
        tabs = new ArrayList<>();

        tabs.add(new Tab("Overview"));
        tabs.get(0).setContent(overviewTabPane);
        
        tabs.add(new Tab("GPU-0"));
        tabs.get(tabs.size()-1).setContent(new GPUPane());

        tabs.add(new Tab("FAN-0"));
        tabs.get(tabs.size()-1).setContent(new FanPane());

        tabs.add(new Tab("NvSMI"));
        tabs.get(tabs.size()-1).setContent(new NvSMIInfoPane());

        tabs.add(new Tab("PowerMizer & Overclocking"));
        tabs.get(tabs.size()-1).setContent(new PerformancePane(NvSettings.getPrimaryGPU()));
        
        //tabs.add(new Tab("NvRelations"));
        //tabs.get(tabs.size()-1).setContent(new NvRelationsPane());

        tabs.add(new Tab("OSD"));
        tabs.get(tabs.size()-1).setContent(new OSDPane());
        
        if(NvXConfig.getCoolbitsController().getOperationalStatus().equals(OperationalStatus.CONTROLLABLE))
        {
            tabs.add(new Tab("NvXConfig"));
            tabs.get(tabs.size()-1).setContent(new NvXConfigPane());
        }
        
        /*
        tabs.add(new Tab("App Console"));
        tabs.get(tabs.size()-1).setContent(pane);
        */
        
        tabs.add(new Tab("About"));
        tabs.get(tabs.size()-1).setContent(new AboutPane());
        
        for(int i = 0; i < tabs.size(); i++)
           tabs.get(i).setOnCloseRequest(handler);

        super.getTabs().addAll(tabs);
    }
    public void showPromptTab(PromptTab tab)
    {
        this.hideOpenTabs();
        
        this.getTabs().add(tab);
        this.getSelectionModel().select(tab);
    }
    public void showNotifyTab(NotifyTab tab)
    {
        this.hideOpenTabs();
        
        this.getTabs().add(tab);
        this.getSelectionModel().select(tab);
    }
    public void removeSpecialTab()
    {
        SPECIAL_TAB_IN_USE = false;
        this.showOpenTabs();
    }
    private void hideOpenTabs()
    {
        BLOCK_TAB_CREATION = true;
        SPECIAL_TAB_IN_USE = true;
        
        openTabs.removeAll(openTabs);
        
        for(int i = 0; i < this.getTabs().size(); i++)
            openTabs.add(this.getTabs().get(i));
        
        activeTab = this.getSelectionModel().getSelectedItem();
        this.getTabs().removeAll(this.getTabs());
    }
    private void showOpenTabs()
    {
        BLOCK_TAB_CREATION = false;
        
        this.getTabs().remove(0);
        this.getTabs().addAll(openTabs);
        
        this.getSelectionModel().select(activeTab);
    }
    public Tab getInformationTab()
    {
        return tabs.get(0);
    }
    public Tab getPowerMizerTab()
    {
        return tabs.get(1);
    }
    public Tab getFanControlTab()
    {
        return tabs.get(2);
    }
    
    // Generic EventHandler class for taking care of special tab closing behavior
    private class TabHandler implements EventHandler<Event>
    {
        @Override
        public void handle(Event event)
        {
            if(AppTabPane.APP_TAB_PANE.getTabs().size() == 1)
            {
                NotifyTab displayTab = new NotifyTab();
                
                displayTab.setHeaderText("No content to display.");
                displayTab.setDescText("Choose a tab from the Tabs menu.");
                
                AppTabPane.APP_TAB_PANE.showNotifyTab(displayTab);
            }
            AppTabPane.APP_TAB_PANE.getTabs().remove(((Tab)event.getSource()));
        } 
    }
}