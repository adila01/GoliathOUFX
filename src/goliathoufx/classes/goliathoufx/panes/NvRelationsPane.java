package goliathoufx.panes;

import goliathoufx.relationships.Relationship;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class NvRelationsPane extends VBox
{
    private final TableView<Relationship> table;
    
    
    private final Button newButton;
    private final Button deleteButton;
    
    public NvRelationsPane()
    {
        table = new TableView<>();
        
        newButton = new Button("New");
        deleteButton = new Button("Delete");
    }
}
