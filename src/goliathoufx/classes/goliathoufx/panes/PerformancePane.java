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

import goliath.nvsettings.targets.NvGPU;
import goliathoufx.panes.performance.OverclockingTabPane;
import goliathoufx.panes.performance.PowerMizerPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PerformancePane extends VBox
{
    private final HBox overclockingPane;
    private final OverclockingTabPane overclockingTabPane;
    private final NvGPU gpu;

    public PerformancePane(NvGPU g)
    {
        super();
        super.setPrefHeight(AppTabPane.CONTENT_HEIGHT);
        super.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        super.getStyleClass().add("vbox");

        gpu = g;
        overclockingTabPane = new OverclockingTabPane(gpu);

        overclockingPane = new HBox();
        overclockingPane.getChildren().addAll(overclockingTabPane);

        super.getChildren().addAll(new PowerMizerPane(gpu), overclockingPane);
    }
}
