package goliathoufx.custom;

import goliathoufx.panes.AppTabPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GenericSectionDivider extends VBox
{
    private final String text;
    
    public GenericSectionDivider(String txt)
    {
        super();
        super.setSpacing(10);
        
        text = txt;
        
        Space topSpace = new Space(false);
        topSpace.setMinWidth(AppTabPane.CONTENT_WIDTH);
        topSpace.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        topSpace.setMinHeight(1);
        topSpace.setMaxHeight(1);
        
        Space bottomBar = new Space(false);
        bottomBar.setMinWidth(AppTabPane.CONTENT_WIDTH);
        bottomBar.setMaxWidth(AppTabPane.CONTENT_WIDTH);
        bottomBar.setMinHeight(1);
        bottomBar.setMaxHeight(1);
        
        super.getChildren().add(topSpace);
        super.getChildren().add(new Label(text));
        super.getChildren().add(bottomBar);
    }
    
    
}
