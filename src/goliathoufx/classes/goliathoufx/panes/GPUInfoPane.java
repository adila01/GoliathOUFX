package goliathoufx.panes;

import goliath.nvsettings.main.NvSettings;
import goliathoufx.custom.GenericControllableComboBox;
import goliathoufx.custom.GenericReadableTablePane;
import goliathoufx.custom.Space;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class GPUInfoPane extends VBox
{
    private final GenericReadableTablePane gpuPane;
    
    public GPUInfoPane()
    {
        super();
        
        gpuPane = new GenericReadableTablePane(new ArrayList<>(NvSettings.getPrimaryGPU().getAttributes()));
        
        Space space = new Space(true);
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(8);
        space.setMaxHeight(8);
        
        TabPane tabPane = new TabPane();
        tabPane.setMinHeight(104);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(new Tab("GPU Fan Mode"));
        tabPane.getTabs().get(0).setContent(new GenericControllableComboBox<>(NvSettings.getPrimaryGPU().getFanMode()));
        
        for(int i = 0; i < tabPane.getTabs().size(); i++)
            tabPane.getTabs().get(i).setClosable(false);
            
        super.getChildren().add(gpuPane);
        super.getChildren().add(space);
        super.getChildren().add(tabPane);
    }
}
