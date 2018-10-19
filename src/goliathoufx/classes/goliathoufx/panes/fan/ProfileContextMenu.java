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

import goliath.nvsettings.gpu.fan.FanProfile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class ProfileContextMenu extends ContextMenu
{
    private final ListView<FanProfile> list;
    
    public ProfileContextMenu(ListView<FanProfile> cList)
    {
        super();
        
        list = cList;
        
        super.getItems().add(new NewItem());
        super.getItems().add(new RenameItem());
        super.getItems().add(new DeleteItem());
  
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
                if(list.getSelectionModel().getSelectedIndex() == 0)
                    event.consume();
                
                list.getItems().remove(list.getSelectionModel().getSelectedIndex());
            }
        }
    }
    private class RenameItem extends MenuItem
    {
        public RenameItem()
        {
            super();
            super.setText("Rename");
            super.setOnAction(new DeleteAction());
        }
        
        private class DeleteAction implements EventHandler<ActionEvent>
        {
            @Override
            public void handle(ActionEvent event)
            {
                list.getItems().remove(list.getSelectionModel().getSelectedIndex());
            }
        }
    }
    
    public class NewItem extends MenuItem
    {
        public NewItem()
        {
            super();
            super.setText("New");
            super.setOnAction(new NewAction());
        }
        
        private class NewAction implements EventHandler<ActionEvent>
        {

            @Override
            public void handle(ActionEvent event)
            {
                
            }
            
        }
    }
}
