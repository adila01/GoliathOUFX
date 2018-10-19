package goliathoufx.panes;

import goliath.nvsettings.enums.OperationalStatus;
import goliath.nvsettings.interfaces.NvReadable;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.panes.performance.OCPaneTemplate;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NvSMIInfoPane extends VBox
{
    private final ReadableTablePane powerPane;
    private OCPaneTemplate powerTempPane;
    
    public NvSMIInfoPane()
    {
        super();
        
        List<NvReadable> rds = new ArrayList<>();
        rds.add(NvSMI.getDefaultPowerLimit());
        rds.add(NvSMI.getPowerLimit());
        rds.add(NvSMI.getPerformanceLimit());
        rds.add(NvSMI.getPowerDraw());
        
        powerPane = new ReadableTablePane(rds);
        
        if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            powerTempPane = new OCPaneTemplate(NvSMI.getPowerLimit());
        
        super.getChildren().add(powerPane);
        
        if(powerTempPane != null)
        {
            Label label = new Label("Max Power Limit Control");
            label.setPadding(new Insets(8,8,8,8));
            
            Space space = new Space();
            space.setMinWidth(AppTabPane.CONTENT_WIDTH);
            space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
            space.setMinHeight(1);
            space.setMaxHeight(1);
            
            super.getChildren().add(label);
            super.getChildren().add(space);
            super.getChildren().add(powerTempPane);
        }
    }
}
