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

import goliath.io.Terminal;
import goliath.io.exceptions.InvalidCommandException;
import goliath.io.exceptions.InvalidWorkingDirectoryException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AboutPane extends VBox
{
    private final Label sourceLabel;
    
    public AboutPane()
    {
        super();
        super.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        super.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        super.getStyleClass().add("vbox");
        super.setPadding(new Insets(10,10,10,10));
        super.setSpacing(25);
        
        sourceLabel = new Label("You can view the source code at:\nhttps://github.com/BlueGoliath/GoliathENVIOUSFX");
        sourceLabel.setOnMouseClicked(new SourceHandler());
        
        super.getChildren().add(new Label("Goliath Overclocking Utility FX V1 Alpha"));
        
        super.getChildren().add(new Label("An easy to use, Open Source overclocking utility for Nvidia graphics cards in Linux using Java & JavaFX."));
        
        super.getChildren().add(sourceLabel);
        
        super.getChildren().add(new Label("Java Runtime: " + Runtime.version().toString()));
    }
    private class SourceHandler implements EventHandler<MouseEvent>
    {
        private final Terminal shell;
        
        public SourceHandler()
        {
            shell = new Terminal();
            shell.setCommand("xdg-open https://github.com/BlueGoliath/Goliath-Overclocking-Utility-FX");
        }
        
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                shell.startCommand();
                shell.waitForExit();
                shell.terminate();
            }
            catch (InvalidCommandException | InvalidWorkingDirectoryException ex)
            {
                ex.printStackTrace();
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(AboutPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
