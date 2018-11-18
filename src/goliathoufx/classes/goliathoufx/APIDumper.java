package goliathoufx;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import java.util.ArrayList;
import java.util.List;

public class APIDumper
{
    public static void dump()
    {
        System.out.println("Dumping attribute info for primary GPU" + NvSettings.getPrimaryGPU().nameProperty().get());
        
        printLoop(new ArrayList<>(NvSettings.getPrimaryGPU().getNvReadables()));
        
        System.out.println("\n\nDone.");
        System.out.println("\n\nPrinting attributes for FAN-0\n\n");
        
        printLoop(new ArrayList<>(NvSettings.getPrimaryGPU().getFan().getNvReadables()));
        
        System.out.println("\n\nDone.");
        System.out.println("\n\nPrinting readables for nvidia-smi\n\n");
        
        printLoop(NvSMI.READABLES);
    }
    
    public static void printLoop(List<ReadOnlyNvReadable> attrs)
    {
        for(int i = 0; i < attrs.size(); i++)
        {
            System.out.println(i + " : ");
            System.out.println("\tcmdName: " + attrs.get(i).cmdNameProperty().get());
            System.out.println("\tcmdValue: " + attrs.get(i).cmdValueProperty().get());
            System.out.println("\tdisplayName: " + attrs.get(i).displayNameProperty().get());
            System.out.println("\tdisplayValue: " + attrs.get(i).displayValueProperty().get());
            System.out.println("\tvalue: " + attrs.get(i).getCurrentValue());
            System.out.println("\tmeasurement: " + attrs.get(i).getMeasurement());
            System.out.println("\tstatus: " + attrs.get(i).getOperationalStatus());
            System.out.println("\tupdate-frequency: " + attrs.get(i).getUpdateFrequency());
            System.out.println("\timplements controllable interface: " + attrs.get(i).getController().isPresent());
        }
    }
}
