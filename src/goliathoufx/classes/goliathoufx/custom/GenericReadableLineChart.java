package goliathoufx.custom;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class GenericReadableLineChart extends LineChart
{
    private final NumberAxis x = new NumberAxis();
    private final NumberAxis y = new NumberAxis();
    private final ReadOnlyNvReadable<Integer> readable;
    
    public GenericReadableLineChart(ReadOnlyNvReadable<Integer> rdbl)
    {
        super(new NumberAxis(), new NumberAxis());
        super.setAnimated(false);
        
        readable = rdbl;
        
    }
}
