package goliathoufx.panes;

import goliathoufx.custom.GenericReadableTablePane;
import goliath.nvsettings.enums.GPUFamily;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.custom.Space;
import goliathoufx.custom.GenericControllableSliderBox;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;

public class NvSMIInfoPane extends VBox
{
    private final GenericReadableTablePane powerPane;
    private final GenericControllableSliderBox powerTempPane;
    
    public NvSMIInfoPane()
    {
        super();
        
        List<ReadOnlyNvReadable> rds = new ArrayList<>();
        rds.add(NvSMI.getDefaultPowerLimit());
        rds.add(NvSMI.getPowerLimit());
        
        if(NvSettings.getPrimaryGPU().getFamily().equals(GPUFamily.PASCAL) || NvSettings.getPrimaryGPU().getFamily().equals(GPUFamily.TURING))
            rds.add(NvSMI.getPerformanceLimit());
        
        rds.add(NvSMI.getPowerDraw());
        
        powerPane = new GenericReadableTablePane(rds);
        powerTempPane = new GenericControllableSliderBox(NvSMI.getPowerLimit());
        
        Space space = new Space(true);
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(2);
        space.setMaxHeight(2);

        TabPane tabPane = new TabPane();
        tabPane.setMinHeight(104);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(new Tab("Power Limit(W)"));
        tabPane.getTabs().get(0).setContent(powerTempPane);

        for(int i = 0; i < tabPane.getTabs().size(); i++)
            tabPane.getTabs().get(0).setClosable(false);

        super.getChildren().add(powerPane);
        super.getChildren().add(space);
        super.getChildren().add(tabPane);        
    }
}
