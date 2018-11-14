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
package goliathoufx.panes.performance;

import goliath.nvsettings.enums.PowerMizerMode;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.panes.ConsolePane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PerformanceModePane extends HBox
{
    private static Label currentPerfLevelLabel;
    
    private final ComboBox<PowerMizerMode> modes;
    private final NvGPU gpu;

    public static Label getLabel()
    {
        return currentPerfLevelLabel;
    }
    
    public PerformanceModePane(NvGPU g)
    {
        super();
        super.getStyleClass().add("hbox");
        super.setPrefHeight(55);
        super.setPadding(new Insets(15, 10, 10, 6));
        super.setSpacing(5);
        
        gpu = g;

        currentPerfLevelLabel = new Label(gpu.getCurrentPerformanceLevel().cmdValueProperty().getValue());

        modes = new ComboBox<>(FXCollections.observableArrayList(gpu.getPowerMizer().getController().get().getAllValues().getAllInRange()));
        modes.getSelectionModel().select(g.getPowerMizer().getCurrentValue());
        modes.getSelectionModel().selectedItemProperty().addListener(new ModeHandler());
        
        super.getChildren().addAll(new Label("Performance Mode:"), modes);
    }
    
    private class ModeHandler implements ChangeListener<PowerMizerMode>
    {
        @Override
        public void changed(ObservableValue<? extends PowerMizerMode> observable, PowerMizerMode oldValue, PowerMizerMode newValue)
        {
            try
            {
                gpu.getPowerMizer().getController().get().setValue(newValue);
            }
            catch (ValueSetFailedException ex)
            {
                ConsolePane.addText("Failed to change PowerMizer mode!");
                System.out.println("Failed to change PowerMizer mode!");
            }
        }
    }
}
