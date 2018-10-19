package goliathoufx.panes;

import goliath.nvsettings.main.NvSettings;
import goliath.nvsettings.targets.NvGPU;
import goliathoufx.custom.LabeledSlider;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MiscPane extends TabPane
{
    public MiscPane()
    {
        super();
        super.setSide(Side.BOTTOM);
        
        super.getTabs().add(new Tab(NvSettings.getPrimaryGPU().nameProperty().getValue()));
        super.getTabs().get(super.getTabs().size()-1).setContent(new MiscVBoxPane(NvSettings.getPrimaryGPU()));
        super.getTabs().get(super.getTabs().size()-1).setClosable(false);
    }
    
    private class MiscVBoxPane extends VBox
    {
        private final LabeledSlider slider;
        private final NvGPU gpu;
        
        public MiscVBoxPane(NvGPU g)
        {
            super();
            gpu = g;
            
            Pane pane = new Pane();
            pane.getStyleClass().add("pane");
            
            slider = new LabeledSlider(gpu.getLogoBrightness(), true);
            slider.setPadding(new Insets(10,0,10,0));
            pane.getChildren().add(slider);
            
            TitledPane gpuLogo = new TitledPane();
            gpuLogo.setText("GPU Logo Brightness");
            gpuLogo.setCollapsible(false);
            gpuLogo.setContent(pane);
            
            super.getChildren().add(gpuLogo);
        }
    }
}
