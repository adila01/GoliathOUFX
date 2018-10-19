package goliathoufx.threads;

import javafx.scene.control.Label;

public class LabelUpdateRunner implements Runnable
{
    private final Label label;
    private final String string;

    public LabelUpdateRunner(Label lab, String str)
    {
        label = lab;
        string = str;
    }

    @Override
    public void run()
    {
        label.setText(string);
    }

}
