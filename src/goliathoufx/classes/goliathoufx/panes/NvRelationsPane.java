package goliathoufx.panes;

import goliathoufx.relationships.Relationship;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NvRelationsPane extends VBox
{
    private final TableView<Relationship> table;
    
    private final HBox buttonArea;
    
    
    private final Button newButton;
    private final Button deleteButton;
    
    public NvRelationsPane()
    {
        table = new TableView<>();
        
        
        
        buttonArea = new HBox();
        buttonArea.setPadding(new Insets(8,8,8,8));
        
        
        newButton = new Button("New");
        deleteButton = new Button("Delete Selected");
    }
}
