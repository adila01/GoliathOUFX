package goliathoufx.panes;

import goliath.nvsettings.main.NvSettings;
import goliathoufx.custom.GenericReadableTablePane;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TargetTreeView extends TreeView<String>
{

    public TargetTreeView()
    {
        super();
        super.setShowRoot(false);
        super.setMaxWidth(275);

        TreeItem<String> root = new TreeItem<>("");
        root.setExpanded(true);

        for (int i = 0; i < NvSettings.getGPUS().size(); i++)
        {
            root.getChildren().add(new TreeItem<>(NvSettings.getGPUS().get(i).nameProperty().get()));

            root.getChildren().get(i).getChildren().add(new TreeItem<>("GPU"));
            root.getChildren().get(i).getChildren().add(new TreeItem<>("Fan"));
            root.getChildren().get(i).getChildren().add(new TreeItem<>("PowerMizer & Overclocking"));
            root.getChildren().get(i).getChildren().add(new TreeItem<>("NvSMI"));
        }

        super.setRoot(root);

    }
}
