package goliathoufx.panes;

import goliathoufx.custom.GenericReadableTablePane;
import goliathoufx.custom.Space;
import goliath.nvsettings.main.NvSettings;
import goliathoufx.custom.GenericControllableComboBox;
import goliathoufx.custom.GenericControllableSliderBox;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class FanInfoPane extends VBox
{
    private final GenericReadableTablePane fanPane;
    
    public FanInfoPane()
    {
        super();
        
        fanPane = new GenericReadableTablePane(new ArrayList<>(NvSettings.getPrimaryGPU().getFan().getAttributes()));
        
        Space space = new Space(true);
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(8);
        space.setMaxHeight(8);

        TabPane tabPane = new TabPane();
        tabPane.setMinHeight(104);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        tabPane.getTabs().add(new Tab("GPU Fan Mode"));
        tabPane.getTabs().get(0).setContent(new GenericControllableComboBox(NvSettings.getPrimaryGPU().getFanMode()));
        
        tabPane.getTabs().add(new Tab("Fan Target Speed(%)"));
        tabPane.getTabs().get(1).setContent(new GenericControllableSliderBox(NvSettings.getPrimaryGPU().getFan().getFanTargetSpeed()));

        for(int i = 0; i < tabPane.getTabs().size(); i++)
            tabPane.getTabs().get(i).setClosable(false);
            
        super.getChildren().add(fanPane);
        super.getChildren().add(space);
        super.getChildren().add(tabPane);
    }
}
