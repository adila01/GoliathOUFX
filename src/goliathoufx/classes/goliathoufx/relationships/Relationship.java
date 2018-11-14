package goliathoufx.relationships;

import goliathoufx.interfaces.Condition;

public class Relationship
{
    private final Condition condition;
    private final Affect affect;
    
    public Relationship(Condition cond, Affect afct)
    {
        condition = cond;
        affect = afct;
    }
}
