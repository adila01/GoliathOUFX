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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PromptTab extends Tab
{
    private final Pane pane;
    private final VBox contentBox;
    private final HBox buttonBox;
    private final Label header, desc;
    private final Button yesButton, noButton;
    
    public PromptTab()
    {
        super();
        super.setText("Confirm");
        super.setClosable(false);
        
        pane = new Pane();
        contentBox = new VBox(); 
        buttonBox = new HBox();
        header = new Label();
        desc = new Label();
        
        yesButton = new Button("Yes");
        yesButton.setPrefWidth(50);
        
        noButton = new Button("No");
        noButton.setPrefWidth(50);
        
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setSpacing(10);
        
        contentBox.getChildren().addAll(header, desc, buttonBox);
        contentBox.setPadding(new Insets(15, 15, 15, 15));
        contentBox.setSpacing(15);

        pane.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        pane.setPrefWidth(AppTabPane.CONTENT_WIDTH);

        pane.getChildren().add(contentBox);
        
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
    public void setYesButtonHandler(EventHandler<MouseEvent> handler)
    {
        yesButton.setOnMouseClicked(handler);
    }
    public void setNoButtonHandler(EventHandler<MouseEvent> handler)
    {
        noButton.setOnMouseClicked(handler);
    }
}