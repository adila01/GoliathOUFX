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

import goliathoufx.GoliathOUFX;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class ConsolePane extends Pane
{
    private static final ListView<String> STATIC_LIST = new ListView<>(FXCollections.observableArrayList(GoliathOUFX.LOG));   
    
    public ConsolePane()
    {
        super();
        //super.prefWidthProperty().bind(AppFrame.APPFRAME.widthProperty());
        //super.prefHeightProperty().bind(AppFrame.APPFRAME.heightProperty());
        
        
        STATIC_LIST.setEditable(false);
        STATIC_LIST.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        STATIC_LIST.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        //list.setItems(FXCollections.observableArrayList(GoliathOUFX.LOG));
        
        super.getChildren().add(STATIC_LIST);

    }
    public ListView<String> getList()
    {
        return STATIC_LIST;
    }
    public static void addText(String text)
    {
        STATIC_LIST.getItems().add(text);
    }
    public static void addText(List<String> text)
    {
        STATIC_LIST.getItems().addAll(text);
    }
}