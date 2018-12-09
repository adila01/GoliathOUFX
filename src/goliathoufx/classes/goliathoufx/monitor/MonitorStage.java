package goliathoufx.monitor;

import goliath.nvsettings.main.NvSettings;
import goliathoufx.custom.GenericReadableLineChart;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MonitorStage extends Stage
{
    private final MonitorGrid grid;
    
    public MonitorStage()
    {
        super();
        super.setTitle("GoliathOUFX Monitor");
        super.setResizable(true);
        super.setWidth(Screen.getPrimary().getBounds().getWidth());
        super.setHeight(Screen.getPrimary().getBounds().getHeight());
        super.initStyle(StageStyle.DECORATED);
        super.setAlwaysOnTop(false);
        super.setFullScreen(false);
        super.setOpacity(.98);
        
        grid = new MonitorGrid();
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add("goliath/css/Goliath-Envy.css");
        
        super.setScene(scene);
    }
    
    private class MonitorGrid extends VBox
    {
        public MonitorGrid()
        {
            super();
            super.getStyleClass().add("grid-pane");
            super.setPadding(new Insets(10,10,10,10));
            
            super.getChildren().addAll(new GenericReadableLineChart(NvSettings.getPrimaryGPU().getCoreUsage()));
        }
    }
}
