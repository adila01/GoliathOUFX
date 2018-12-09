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

import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathoufx.panes.ConsolePane;
import goliathoufx.threads.AttributeUpdatesThread;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GoliathOUFX extends Application
{
    public static final Logger APP_LOGGER = Logger.getLogger("GoliathOUFX");
    public static final List<String> LOG = new ArrayList<>();
    public static boolean SAFE_SHUTDOWN_ACHIEVED = false;
    
    public static Scene APP_SCENE;
    public static Stage APP_STAGE;
    private static boolean DEV = false;
    
    private static AttributeUpdatesThread high, med, low, fan, power;

    public static void main(String[] args)
    {   
        APP_LOGGER.setUseParentHandlers(false);
        APP_LOGGER.addHandler(new LogHandler());
        APP_LOGGER.info("Application start");
        
        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                case "--developer":
                    DEV = true;
                    break;
            }
        }

        NvSettings.init();

        InstanceProvider.init();
        
        high = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getHighUpdateFrequencyAttributes()), Thread.MAX_PRIORITY, 0);
        high.start();
        
        fan = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getFan().getNvReadables()), Thread.MIN_PRIORITY, 600);
        fan.start();
        
        med = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getMediumUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 750);
        med.start();
        
        low = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getLowUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 1000);
        low.start();
        
        power = new AttributeUpdatesThread(new ArrayList<>(NvSMI.READABLES), Thread.MIN_PRIORITY, 1250);
        power.start();
        
        APP_LOGGER.info("Launch application GUI");
        
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        APP_STAGE = stage;
        APP_STAGE.setOpacity(.98);
        
        APP_SCENE = new Scene(new AppFrame(stage));
        APP_SCENE.getStylesheets().add("goliath/css/Goliath-Envy.css");
        
        stage.setScene(APP_SCENE);
        stage.setTitle("Goliath Overclocking Utility V3 - " + NvSettings.getPrimaryGPU().nameProperty().get()
                + " - Nvidia " + NvSettings.getDriverVersion().getCurrentValue());
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setMinHeight(436);
        stage.setMinWidth(900);
        stage.show(); 
    }
    
    @Override
    public void stop()
    {
        GoliathOUFX.closeApplication();
    }

    public static void closeApplication()
    {
        if(!SAFE_SHUTDOWN_ACHIEVED)
        {
            APP_LOGGER.info("Safe shutdown not achieved! Setting ALL GPU controllers to their default values!");
            
            try
            {
                NvSettings.getPrimaryGPU().resetControllables();
            }
            catch (ControllerResetFailedException ex)
            {

            }
        }
        
        APP_LOGGER.info("Application shutdown.");
        Platform.exit();
        System.exit(0);
    }
    
    private static class LogHandler extends Handler 
    {
        @Override
        public void publish(LogRecord record)
        {
            /*
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append(record.getLoggerName());
            builder.append("]:");

            builder.append("[");
            builder.append(record.getSourceClassName());
            builder.append("]:");
            
            builder.append("[");
            builder.append(record.getLevel());
            builder.append("]:");
            
            builder.append("[");
            builder.append(record.getMessage());
            builder.append("]");
            */
            ConsolePane.addText(record.getMessage());
            System.out.println(record.getMessage());
            LOG.add(record.getMessage());

        }

        @Override
        public void flush()
        {
            throw new UnsupportedOperationException("Not used!");
        }

        @Override
        public void close() throws SecurityException
        {
            throw new UnsupportedOperationException("Not used!");
        }
    }
}