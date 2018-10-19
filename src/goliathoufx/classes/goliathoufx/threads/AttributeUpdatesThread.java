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
package goliathoufx.threads;

import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.GoliathOUFX;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttributeUpdatesThread extends Thread
{   
    public AttributeUpdatesThread()
    {
        super();
        super.setDaemon(true);
        super.setName("Goliath Attribute Update Thread");
        super.setPriority(Thread.MAX_PRIORITY);
    }
    
    @Override
    public void run()
     {
        while(!GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED)
        {
            try
            {
                Thread.sleep(0);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(AttributeUpdatesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            NvSettings.getPrimaryGPU().updateAttributes();
            NvSMI.getPowerDraw().update();
            NvSMI.getPerformanceLimit().update();
            //Platform.runLater(new LabelUpdateRunner(PerformanceModePane.getLabel(), NvSettings.getPrimaryGPU().getCurrentPerformanceLevelAttribute().cmdValueProperty().getValue()));
        }
    }
}
