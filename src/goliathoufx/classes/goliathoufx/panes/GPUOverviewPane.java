package goliathoufx.panes;

import goliath.envious.enums.OperationalStatus;
import goliath.nvsettings.targets.NvGPU;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.custom.Tile;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GPUOverviewPane extends GridPane
{
    public GPUOverviewPane(NvGPU g)
    {
        super();
        super.setWidth(AppTabPane.CONTENT_WIDTH);
        super.setHeight(AppTabPane.CONTENT_HEIGHT-25);
        
        super.setPadding(new Insets(10,10,10,10));
        super.setHgap(8);
        super.setVgap(8);
        
        Tile gpuNameTile = new Tile();
        gpuNameTile.addLabel(g.idProperty().get() + " Name");
        gpuNameTile.addLabel(g.nameProperty());
        
        Tile gpuTemp = new Tile();
        gpuTemp.setNvReadable(g.getCoreTemp());
        
        Tile gpuIdTile = new Tile();
        gpuIdTile.addLabel(g.idProperty().get() + " NvSettings ID");
        gpuIdTile.addLabel(g.idProperty());
        
        Tile gpuUUIDTile = new Tile();
        gpuUUIDTile.setNvReadable(g.getUuid());
        
        Tile gpuCores = new Tile();
        gpuCores.setNvReadable(g.getCUDACores()); 
        
        Tile totalMemory = new Tile();
        totalMemory.setNvReadable(g.getDedicatedMemory()); 

        Tile memoryInterface = new Tile();
        memoryInterface.setNvReadable(g.getMemoryInterface()); 
        
        Tile memoryBandwidthUsage = new Tile();
        memoryBandwidthUsage.setNvReadable(g.getMemoryUsage()); 
        
        Tile coreUsage = new Tile();
        coreUsage.setNvReadable(g.getCoreUsage()); 
        
        Tile usedMemory = new Tile();
        usedMemory.setNvReadable(g.getUsedGPUMemory()); 
        
        Tile coreClock = new Tile();
        coreClock.setNvReadable(g.getCoreClock()); 
        
        Tile memoryClock = new Tile();
        memoryClock.setNvReadable(g.getMemoryClock()); 
        
        Tile pcieGen = new Tile();
        pcieGen.setNvReadable(g.getPCIeGen());

        Tile pcieCurrentWidth = new Tile();
        pcieCurrentWidth.setNvReadable(g.getPCIeCurrentWidth());
        
        Tile pcieCurrentSpeed = new Tile();
        pcieCurrentSpeed.setNvReadable(g.getPCIeCurrentSpeed());
        
        Tile pcieUsage = new Tile();
        pcieUsage.setNvReadable(g.getPCIeUsage());
        
        Tile coreOCSupported = new Tile();
        coreOCSupported.addLabel("Overclocking Support");
        
        if(g.getCoreOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            coreOCSupported.addLabel("Enabled");
        else
            coreOCSupported.addLabel("Disabled");
        
        Tile voltageSupport = new Tile();
        voltageSupport.addLabel("Voltage Support");
        
        if(g.getVoltageOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            voltageSupport.addLabel("Enabled");
        else
            voltageSupport.addLabel("Disabled");
        
        Tile fanControlSupport = new Tile();
        fanControlSupport.addLabel("Fan Control Support");
        
        if(g.getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            fanControlSupport.addLabel("Enabled");
        else
            fanControlSupport.addLabel("Disabled");
        
        
        Tile powerLimitSupport = new Tile();
        powerLimitSupport.addLabel("Power Management Support");
        
        if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            powerLimitSupport.addLabel("Enabled");
        else if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE))
            powerLimitSupport.addLabel("Disabled - Root Required");
        else
            powerLimitSupport.addLabel("Disabled - Not Supported");
        
        
        
        super.add(gpuNameTile, 0, 0);
        super.add(gpuIdTile, 1, 0);
        super.add(gpuCores, 2, 0);
        super.add(gpuUUIDTile, 3, 0);
        
        super.add(usedMemory, 0, 1);
        super.add(totalMemory, 1, 1);
        super.add(memoryInterface, 2, 1);
        super.add(memoryBandwidthUsage, 3, 1);
        
        super.add(coreUsage, 0, 2);
        super.add(coreClock, 1, 2);
        super.add(memoryClock, 2, 2);
        super.add(gpuTemp, 3, 2);
        
        super.add(pcieGen, 0, 3);
        super.add(pcieCurrentWidth, 1, 3);
        super.add(pcieCurrentSpeed, 2, 3);
        super.add(pcieUsage, 3, 3);
        
        super.add(coreOCSupported, 0, 4);
        super.add(voltageSupport, 1, 4);
        super.add(fanControlSupport, 2, 4);
        
        super.add(powerLimitSupport, 3, 4);
    }
}
