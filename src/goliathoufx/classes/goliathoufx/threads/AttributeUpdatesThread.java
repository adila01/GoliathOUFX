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

import goliathoufx.GoliathOUFX;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import goliath.envious.interfaces.ReadOnlyNvReadable;

public class AttributeUpdatesThread extends Thread
{   
    private final List<ReadOnlyNvReadable> readables;
    private final int delay;
    
    
    public AttributeUpdatesThread(List<ReadOnlyNvReadable> rdbls, int prt, int dly)
    {
        super();
        super.setDaemon(true);
        super.setName("Goliath Attribute Update Thread");
        super.setPriority(prt);
        
        readables = rdbls;
        delay = dly;
    }
    
    @Override
    public void run()
     {
        while(!GoliathOUFX.SAFE_SHUTDOWN_ACHIEVED)
        {
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(AttributeUpdatesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(int i = 0; i < readables.size(); i++)
            {
                readables.get(i).update();
            }
        }
    }
}
