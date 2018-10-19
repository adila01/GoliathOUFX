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
package goliathoufx.menu.items;

import goliathoufx.panes.AppTabPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabsMenu extends Menu
{
    private final MenuHandler handler;
    private final TabPane tabPaneToAddTo;
    private Tab[] tabs;
    
    
    public TabsMenu(TabPane tabPane)
    {
        super("Tabs");
        
        tabPaneToAddTo = tabPane;
        tabs = tabPane.getTabs().toArray(new Tab[tabPane.getTabs().size()]);
        
        handler = new MenuHandler(tabs);
        
        for(int i = 0; i < tabs.length; i++)
        {
            MenuItem tmp = new MenuItem(tabs[i].getText());
            tmp.setOnAction(handler);
            super.getItems().add(tmp);
        }
        //tabPaneToAddTo.getTabs().remove(3, 4);
    }
    
    // Generic MenuHancler for TabsMenu since they all do the same thing.
    private class MenuHandler implements EventHandler<ActionEvent>
    {   
        public MenuHandler(Tab[] tabArray)
        {
            tabs = tabArray;
        }
        
        @Override
        public void handle(ActionEvent event)
        {
            if(AppTabPane.BLOCK_TAB_CREATION)
                event.consume();
            else
            {
                for(int i = 0; i < tabs.length; i++)
                {
                    if(tabs[i].getText().equals(((MenuItem)event.getSource()).getText()))
                    {
                        if(!tabPaneToAddTo.getTabs().contains(tabs[i]))
                            tabPaneToAddTo.getTabs().add(tabs[i]);
                        
                        tabPaneToAddTo.getSelectionModel().select(tabs[i]);
                    }
                }
            }
        }
    }
}
