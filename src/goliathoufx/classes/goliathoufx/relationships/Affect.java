package goliathoufx.relationships;

import goliath.envious.interfaces.NvControllable;

public interface Affect
{
    public void performAffect();
    public NvControllable<Integer> getNvControllable();
    public Integer getAffectValue();
}
