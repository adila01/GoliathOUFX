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

import goliath.nvsettings.utility.CsvWriter;
import java.io.File;
import java.io.IOException;

public class AppDirectory extends File
{
    private final File appConfig;
    private final File fanProfilesDir;
    private final File attributesDir;
    private final File defaultFanProfile;
    private final File gpuInfo;

    public AppDirectory(String dir)
    {
        super(dir);

        appConfig = new File(super.getAbsolutePath() + "/app.csv");
        gpuInfo = new File(super.getAbsolutePath() + "/gpuinfo.csv");
        fanProfilesDir = new File(super.getAbsolutePath() + "/Fan Profiles");
        defaultFanProfile = new File(fanProfilesDir.getAbsolutePath() + "/default.csv");
        attributesDir = new File(super.getAbsolutePath() + "/Attributes");

        try
        {
            if (!super.exists())
            {
                this.createAppDirectory();
                this.createDefaultConfig();
                this.createFanProfileDirectory();
                this.createDefaultFanProfile();
            }

            if (!appConfig.exists())
            {
                this.createDefaultConfig();
            }

            if (!gpuInfo.exists())
            {
                this.createGPUInfo();
            }

            if (!fanProfilesDir.exists())
            {
                this.createFanProfileDirectory();
            }

            if (!defaultFanProfile.exists())
            {
                this.createDefaultFanProfile();
            }

            if (!attributesDir.exists())
            {
                this.createAttributesDirectory();
            }
        } 
        catch (IOException e)
        {
            System.out.println("Failed to create app directory.");
        }
    }

    private void createAppDirectory() throws IOException
    {
        super.mkdir();
    }

    private void createFanProfileDirectory()
    {
        fanProfilesDir.mkdir();
    }

    private void createAttributesDirectory()
    {
        attributesDir.mkdir();
    }

    private void createDefaultConfig() throws IOException
    {
        CsvWriter writer;

        appConfig.createNewFile();

        writer = new CsvWriter(appConfig.getAbsoluteFile());

        writer.addKeyValue("theme", "Magma");
        writer.addKeyValue("show_extra_attribute_data", "false");
        writer.addKeyValue("disable_overvoltage", "true");
        writer.addKeyValue("reset_overclocks_on_close", "true");
        writer.addKeyValue("voltage_droop_compensation", "false");
    }

    private void createDefaultFanProfile() throws IOException
    {
        CsvWriter writer = new CsvWriter(defaultFanProfile);

        defaultFanProfile.createNewFile();

        writer.addKeyValue("display_name", "Default");
        writer.addKeyValue("update_speed", "1000");
        writer.addKeyValue("smooth", "true");

        writer.addKeyValue("node_35", "15");
        writer.addKeyValue("node_40", "20");
        writer.addKeyValue("node_45", "25");
        writer.addKeyValue("node_50", "30");
        writer.addKeyValue("node_55", "35");
        writer.addKeyValue("node_60", "40");
        writer.addKeyValue("node_65", "45");
        writer.addKeyValue("node_70", "50");
        writer.addKeyValue("node_75", "55");
        writer.addKeyValue("node_80", "60");
    }

    private void createGPUInfo() throws IOException
    {
        //CsvWriter writer;
        //PerfLevelParser parser = new PerfLevelParser();
        //ArrayList<PerformanceLevel> perfs = new ArrayList<>();

        gpuInfo.createNewFile();

        //perfs = parser.beginParse();
        //writer = new CsvWriter(gpuInfo);
    }

    public File getAppConfig()
    {
        return appConfig;
    }

    public File getAttributesDirectory()
    {
        return attributesDir;
    }

    public File getFanProfileDirectory()
    {
        return fanProfilesDir;
    }
}
