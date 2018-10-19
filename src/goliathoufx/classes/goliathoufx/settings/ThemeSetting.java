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
package goliathoufx.settings;

import goliathoufx.interfaces.AppSetting;
import java.util.ArrayList;

public class ThemeSetting implements AppSetting
{
    private final ArrayList<String> pastKeys;
    private String key, value;
    
    public ThemeSetting()
    {
        pastKeys = new ArrayList<>();
    }
    
    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public String getValue()
    {
        return value;
    }

    @Override
    public ArrayList<String> getOldKeys()
    {
        return pastKeys;
    }

    @Override
    public void setKey(String val)
    {
        key = val;
    }

    @Override
    public void setValue(String val)
    {
        value = val;
    }
}