package goliathoufx.custom.relations;

import goliath.nvsettings.main.NvSettings;
import goliathoufx.custom.GenericSectionDivider;
import goliathoufx.custom.LabeledSlider;
import javafx.scene.layout.VBox;

public class GBoostRelationPane extends VBox
{
    public GBoostRelationPane()
    {
        super();
        
        super.getChildren().add(new LabeledSlider(NvSettings.getPrimaryGPU().getCoreOffset(), false));
        super.getChildren().add(new GenericSectionDivider("Memory Offset"));
        super.getChildren().add(new LabeledSlider(NvSettings.getPrimaryGPU().getMemoryOffset(), false));
        super.getChildren().add(new GenericSectionDivider("Voltage Offset"));
        super.getChildren().add(new LabeledSlider(NvSettings.getPrimaryGPU().getVoltageOffset(), false));
    }
}
