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
package goliathoufx.osd;

import goliath.nvsettings.interfaces.NvReadable;
import goliathoufx.panes.OSDPane;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OSDStage extends Stage
{

    public static boolean OSD_ENABLED = false;

    private final Scene scene;

    public OSDStage()
    {
        super();
        super.setTitle("Goliath Overclocking Utility OSD");
        super.setResizable(false);
        super.setX(0);
        super.setY(0);
        super.initStyle(StageStyle.TRANSPARENT);
        super.setAlwaysOnTop(true);
        super.setFullScreen(false);

        scene = new Scene(new OsdTable());
        scene.setFill(Color.TRANSPARENT);

        OSD_ENABLED = true;

        super.setScene(scene);
        super.show();
    }

    @Override
    public void close()
    {
        OSD_ENABLED = false;
        super.close();
    }

    private class OsdTable extends TableView<NvReadable>
    {

        private final TableColumn<NvReadable, String> colOne, colTwo;

        public OsdTable()
        {
            super();
            super.setPrefWidth(427);
            super.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            super.setEditable(false);
            super.setMouseTransparent(true);
            super.setTableMenuButtonVisible(false);
            super.getStylesheets().add("goliathoufx/osd/osd.css");
            super.setItems(OSDPane.getOnList().getItems());
            
            colOne = new TableColumn<>();
            colOne.setMinWidth(225);
            colOne.setMaxWidth(225);
            colOne.setStyle("-fx-text-fill: #00FF00;");
            colOne.setCellValueFactory(new PropertyValueFactory<>("displayName"));
            colOne.setEditable(false);

            colTwo = new TableColumn<>();
            colTwo.setMinWidth(200);
            colTwo.setMaxWidth(200);
            colTwo.setStyle("-fx-text-fill: #00FF00;");
            colTwo.setCellValueFactory(new PropertyValueFactory<>("displayValue"));
            colTwo.setEditable(false);

            super.getColumns().add(colOne);
            super.getColumns().add(colTwo);
        }
    }
}
