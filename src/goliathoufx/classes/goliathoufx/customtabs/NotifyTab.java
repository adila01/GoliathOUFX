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
package goliathoufx.customtabs;

import goliathoufx.panes.AppTabPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NotifyTab extends Tab
{   
    private final Pane pane;
    private final VBox box;
    private final Label header, desc;
    private final Button okButton;
    
    public NotifyTab()
    {
        super();
        super.setText("Notification");
        super.setClosable(false);
        
        pane = new Pane();
        box = new VBox();        
        header = new Label();
        desc = new Label();
        
        okButton = new Button("Ok");
        okButton.setPrefWidth(50);
        okButton.setOnMouseClicked(new OkButtonHandler());
        
        box.getChildren().addAll(header, desc, okButton);
        box.setPadding(new Insets(15, 15, 15, 15));
        box.setSpacing(15);
        
        pane.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        pane.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        pane.getChildren().add(box);
        
        super.setContent(pane);
    }
    public void setTabText(String text)
    {
        super.setText(text);
    }
    public void setHeaderText(String text)
    {
        header.setText(text);
    }
    public void setDescText(String text)
    {
        desc.setText(text);
    }
    private class OkButtonHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            AppTabPane.getTabPane().removeSpecialTab();
        }
    }
}