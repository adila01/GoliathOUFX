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

import goliath.nvsettings.enums.FanMode;
import goliath.nvsettings.exceptions.ValueSetFailedException;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.custom.LabeledSlider;
import goliathoufx.panes.AppTabPane;
import goliathoufx.panes.ConsolePane;
import goliathoufx.panes.Space;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FanOptionPane extends HBox
{
    private final ComboBox<FanMode> modes;
    private final LabeledSlider speedSlider;
    private final HBox modeBox, speedBox;
    private final NvGPU gpu;

    public FanOptionPane(NvGPU g)
    {
        super();
        super.getStyleClass().add("hbox");
        super.setPadding(new Insets(10, 10, 10, 10));
        super.setSpacing(10);

        gpu = g;
        
        speedSlider = new LabeledSlider(gpu.getFan().getFanTargetSpeed(), true);
        speedSlider.setPrefWidth(AppTabPane.CONTENT_WIDTH-325);

        Space sep = new Space();
        sep.setMinWidth(3);
        sep.setMaxWidth(3);
        sep.setMinHeight(25);
        sep.setMaxHeight(25);

        modes = new ComboBox<>(FXCollections.observableArrayList(gpu.getFanMode().getController().get().getAllValues().getAllInRange().get()));
        modes.getSelectionModel().selectedItemProperty().addListener(new ModeHandler());
        
        modes.getSelectionModel().select(g.getFanMode().getCurrentValue());

        modeBox = new HBox();
        modeBox.setSpacing(8);
        modeBox.getChildren().addAll(new Label("Fan Mode:"), modes);

        speedBox = new HBox();
        speedBox.setSpacing(8);
        speedBox.getChildren().addAll(sep, new Label("Manual Speed:"), speedSlider);

        super.getChildren().addAll(modeBox, speedBox);

    }

    private class ModeHandler implements ChangeListener<FanMode>
    {
        @Override
        public void changed(ObservableValue<? extends FanMode> observable, FanMode oldValue, FanMode newValue)
        {
            try
            {
                gpu.getFanMode().getController().get().setValue(newValue);
                gpu.getFanMode().update();
            }
            catch (ValueSetFailedException ex)
            {
                ConsolePane.addText("Failed to change fan mode!");
            }
            
            if(newValue.getCmdNumber() == 0)
                speedSlider.setDisable(true);
            else
                speedSlider.setDisable(false);
        }
    }
}
