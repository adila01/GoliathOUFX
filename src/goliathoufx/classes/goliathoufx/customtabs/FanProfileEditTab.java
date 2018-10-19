package goliathoufx.customtabs;

import goliath.nvsettings.gpu.fan.FanProfile;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FanProfileEditTab extends Tab
{
    private final FanProfile profile;
    private final TableView<FanProfile> table;
    private final TableColumn<FanProfile, Integer> tempColumn, speedColumn;
    private final Button button;
    
    
    public FanProfileEditTab(FanProfile prof)
    {
        super("Fan Profile Edit");
        
        table = new TableView<>();
        tempColumn = new TableColumn<>();
        speedColumn = new TableColumn<>();
        
        
        button = new Button("Save");
        profile = prof;
    }
}


