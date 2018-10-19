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
package goliathoufx.panes.monitoring;

import goliathoufx.panes.AppTabPane;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;

public class MonitorPaneTemplate extends Pane
{
    private final LineChart<Number, Number> chart;
    private final NumberAxis x;
    private final NumberAxis y;
    private final Series<Number, Number> series;
    private final ArrayList<Data<Number,Number>> data;
    
    public MonitorPaneTemplate()
    {
        super();
        super.getStyleClass().add("pane");
        
        x = new NumberAxis();
        x.setTickLabelsVisible(true);
        x.setTickMarkVisible(true);
        x.setForceZeroInRange(false);
        x.setAutoRanging(true);
        
        y = new NumberAxis();
        y.setAutoRanging(false);
        
        chart = new LineChart<>(x, y);
        chart.horizontalZeroLineVisibleProperty().set(false);
        chart.setCreateSymbols(false);
        chart.legendVisibleProperty().setValue(false);
        chart.setPrefHeight(250);
        chart.setPrefWidth(AppTabPane.CONTENT_WIDTH);
        
        series = new Series<>();
        data = new ArrayList<>();
        
        chart.getData().add(series);
        
        super.getChildren().add(chart);
    }
    public Series getSeries()
    {
        return series;
    }
    public ArrayList<Data<Number,Number>> getData()
    {
        return data;
    }
    public void setXTickRate(int rate)
    {
        x.setTickUnit(rate);
    }
    public void setYTickRate(int rate)
    {
        y.setTickUnit(rate);
    }
    public void setXLabel(String text)
    {
        x.setLabel(text);
    }
    public void setYLabel(String text)
    {
        y.setLabel(text);
    }
    public void setXUpper(double num)
    {
        x.setUpperBound(num);
    }
    public void setYUpper(double num)
    {
        y.setUpperBound(num);
    }
    public void addData(Data<Number, Number> entry)
    {
        data.add(entry);
        chart.getData().get(0).setData(FXCollections.observableArrayList(data));
    }
}
