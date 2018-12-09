package goliathoufx.panes;

import goliathoufx.custom.relations.GBoostRelationPane;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class NvRelationsPane extends TabPane
{
    public NvRelationsPane()
    {
        super();
        super.setSide(Side.BOTTOM);
        super.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        Tab custom = new Tab("Custom");
        custom.setContent(new Pane());
        
        Tab fanControl = new Tab("Fan Curve");
        fanControl.setContent(new Pane());
        
        Tab boost = new Tab("GBoost");
        boost.setContent(new GBoostRelationPane());
        
        super.getTabs().add(custom);
        super.getTabs().add(fanControl);
        super.getTabs().add(boost);
    }
}
