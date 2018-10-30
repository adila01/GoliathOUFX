/* 
 * The MIT License
 *
 * Copyright 2018 Ty Young.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliathoufx;

import goliath.nvsettings.enums.GPUFamily;
import goliath.nvsettings.enums.OperationalStatus;
import goliath.nvsettings.gpu.fan.FanManager;
import goliath.nvsettings.gpu.fan.FanProfile;
import goliath.nvsettings.interfaces.NvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsettings.targets.NvGPU;
import goliath.nvsmi.main.NvSMI;
import static goliathoufx.GoliathOUFX.APP_LOGGER;
import java.util.ArrayList;

public class InstanceProvider
{
    private static final ArrayList<NvReadable> ON_SCREEN_DISPLAY_ATTRIBUTES = new ArrayList<>();
    private static FanProfile currentProfile;
    private static FanManager fanManager;
    private static NvGPU GPU_0;
    
    public static void init()
    {
        APP_LOGGER.entering(InstanceProvider.class.getSimpleName(), "init()");
        
        GPU_0 = NvSettings.getPrimaryGPU();
        

        APP_LOGGER.info("InstanceProvider: Found GPU " + GPU_0.nameProperty().getValue() + " with ID " + GPU_0.idProperty().getValue());

        initAttributes();

    }
    
    private static void initAttributes()
    {
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getGPUName());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(NvSettings.getDriverVersion());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getCoreUsage());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getCoreClock());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getMemoryUsage());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getMemoryClock());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getUsedGPUMemory());
        
        if(GPU_0.getCurrentVoltage().getOperationalStatus().equals(OperationalStatus.READABLE))
            ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getCurrentVoltage());
        
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(NvSMI.getPowerDraw());
        
        if(NvSettings.getPrimaryGPU().getFamily().equals(GPUFamily.PASCAL) || NvSettings.getPrimaryGPU().getFamily().equals(GPUFamily.TURING))
            ON_SCREEN_DISPLAY_ATTRIBUTES.add(NvSMI.getPerformanceLimit());
        
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getCoreTemp());
        ON_SCREEN_DISPLAY_ATTRIBUTES.add(GPU_0.getFan().getFanCurrentSpeed());
        
        APP_LOGGER.info("InstanceProvider: Envious API providing " + GPU_0.getAttributes().size() + " attributes for primary GPU " + GPU_0.nameProperty().getValue() + " from nvidia-settings.");
        
        APP_LOGGER.info("InstanceProvider: Envious API providing " + GPU_0.getFan().getAttributes().size() + " attributes for fan of primary GPU " + GPU_0.nameProperty().getValue() + " from nvidia-settings.");   
        APP_LOGGER.info("InstanceProvider: Envious API providing " + NvSMI.READABLES.size() + " readables from nvidia-smi.");   
                
    }
    
    public static AppDirectory getAppDir()
    {
        return null;
    }
    public static ArrayList<NvReadable> getOSDAttributes()
    {
        return ON_SCREEN_DISPLAY_ATTRIBUTES;
    }
    public static FanProfile getCurrentFanProfile()
    {
        return currentProfile;
    }
    public static FanManager getFanManager()
    {
        return fanManager;
    }
    public static void setCurrentFanProfile(FanProfile profile)
    {
        currentProfile = profile;
    }
}