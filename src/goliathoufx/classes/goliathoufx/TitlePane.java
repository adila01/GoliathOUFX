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

import goliath.nvsettings.enums.FanMode;
import goliath.nvsettings.exceptions.ControllerResetFailedException;
import goliath.nvsettings.main.NvSettings;
import goliathoufx.customtabs.PromptTab;
import goliathoufx.panes.AppTabPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitlePane extends BorderPane
{
    private final Stage stage;
    private final Label titleLabel;
    private final Button exit, minimize;
    
    private double x, y;
    
    public TitlePane(String name, Stage appStage)
    {
        super();
        super.getStyleClass().add("title-pane");
        super.setPadding(new Insets(0,0,0,0));
        super.setOnMousePressed(new AppMovementHandler());
        super.setOnMouseDragged(new AppDragHandler());
        
        stage = appStage;
        
        titleLabel = new Label(name);
        titleLabel.setPadding(new Insets(3,3,3,3));
        
        HBox buttonBox = new HBox();
        
        exit = new Button("Exit");
        exit.getStyleClass().add("close");
        //exit.setGraphic(new ImageView(new Image(new File("src/images/x.png").toURI().toString())));
        exit.setOnMouseClicked(new ExitHandler());
        
        minimize = new Button("Minimize");
        minimize.getStyleClass().add("minimize");
        //minimize.setGraphic(new ImageView(new Image(new File("src/images/minus.png").toURI().toString())));
        minimize.setOnMouseClicked(new MinimizeHandler());
        
        buttonBox.getChildren().addAll(minimize, exit);
        
        super.setLeft(titleLabel);
        super.setRight(buttonBox);
    }

    private class MinimizeHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            stage.setIconified(true);
        }
    }
    private class AppMovementHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        }
    }
    private class AppDragHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);
        }
    }
    private class ExitHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {

            if(NvSettings.getPrimaryGPU().getFanMode().getCurrentValue().equals(FanMode.MANUAL))
            {
                PromptTab prompt = new PromptTab();
                
                prompt.setHeaderText("Fan Mode is still set to Manual.");
                prompt.setDescText("Would you like to change it back to auto before exiting?");
                
                prompt.setYesButtonHandler(new YesButtonHandler());
                prompt.setNoButtonHandler(new NoButtonHandler());
                AppTabPane.getTabPane().showPromptTab(prompt);
            }
            else
            {
                GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED = true;
                GoliathOUFX.closeApplication();
            }
        }
        
        private class YesButtonHandler implements EventHandler<MouseEvent>
        {
            @Override
            public void handle(MouseEvent event)
            {
                GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED = true;
                try
                {
                    NvSettings.getPrimaryGPU().getFanMode().getController().get().reset();
                }
                catch (ControllerResetFailedException ex)
                {
                    GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED = false;
                    ex.printStackTrace();
                }
                GoliathOUFX.closeApplication();
            }
        }
        
        private class NoButtonHandler implements EventHandler<MouseEvent>
        {
            @Override
            public void handle(MouseEvent event)
            {
                GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED = true;
                GoliathOUFX.closeApplication();
            }
        }
    }
}
