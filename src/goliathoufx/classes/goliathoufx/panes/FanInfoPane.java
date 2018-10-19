package goliathoufx.panes;

import goliath.nvsettings.main.NvSettings;
import goliathoufx.panes.fan.FanOptionPane;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FanInfoPane extends VBox
{
    private final ReadableTablePane fanPane;
    private final FanOptionPane fanOptions;
    
    public FanInfoPane()
    {
        super();
        
        fanPane = new ReadableTablePane(new ArrayList<>(NvSettings.getPrimaryGPU().getFan().getAttributes()));
        fanOptions = new FanOptionPane(NvSettings.getPrimaryGPU());
        
        Space space = new Space();
        space.setMinWidth(AppTabPane.CONTENT_WIDTH);
        space.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        space.setMinHeight(1);
        space.setMaxHeight(1);
        
        Label label = new Label("Fan Control");
        label.setPadding(new Insets(8,8,8,8));
        
        super.getChildren().add(fanPane);
        super.getChildren().add(label);
        super.getChildren().add(space);
        super.getChildren().add(fanOptions);
    }
}
