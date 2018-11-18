/*
 * This file is part of Amor, licensed under the MIT License (MIT).
 *
 * Copyright (c) Lexteam <https://www.lexteam.xyz/>
 * Copyright (c) contributors
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

package xyz.lexteam.amor.util;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.jse.JsePlatform;
import xyz.lexteam.amor.lib.LoveLib;

/**
 * Convenience class to obtain a standard lua global table.
 */
public final class LovePlatform {

    /**
     * Creates a standard globals table for Amor.
     *
     * @param state The lua state
     * @param conf The Amor configuration
     * @return The globals table
     */
    public static LuaTable standardGlobals(final LuaState state, final LuaTable conf) {
        final LuaTable globals = JsePlatform.standardGlobals(state);
        try {
            globals.rawget("os").checkTable().rawset("time", new ZeroArgFunction() {
                @Override
                public LuaValue call(final LuaState state) throws LuaError {
                    return ValueFactory.valueOf(System.currentTimeMillis() / 1000);
                }
            });
        }
        catch (final LuaError ex) {
            ex.printStackTrace();
        }
        globals.load(state, new LoveLib(conf));
        return globals;
    }

    /**
     * Creates a table containing the standard configuration
     * for Amor.
     *
     * @return The standard configuration
     */
    public static LuaTable standardConfiguration() {
        final LuaTable conf = new LuaTable();
        conf.rawset("identity", Constants.NIL);
        conf.rawset("version", ValueFactory.valueOf("0.10.2"));
        conf.rawset("console", ValueFactory.valueOf(false));
        conf.rawset("accelerometerjoystick", ValueFactory.valueOf(false));
        conf.rawset("externalstorage", ValueFactory.valueOf(false));
        conf.rawset("gammacorrect", ValueFactory.valueOf(false));

        final LuaTable windowConf = new LuaTable();
        windowConf.rawset("title", ValueFactory.valueOf("Untitled"));
        windowConf.rawset("icon", Constants.NIL);
        windowConf.rawset("width", ValueFactory.valueOf(800));
        windowConf.rawset("height", ValueFactory.valueOf(600));
        windowConf.rawset("borderless", ValueFactory.valueOf(false));
        windowConf.rawset("resizable", ValueFactory.valueOf(false));
        windowConf.rawset("minwidth", ValueFactory.valueOf(1));
        windowConf.rawset("minheight", ValueFactory.valueOf(1));
        windowConf.rawset("fullscreen", ValueFactory.valueOf(false));
        windowConf.rawset("fullscreentype", ValueFactory.valueOf("desktop"));
        windowConf.rawset("vsync", ValueFactory.valueOf(true));
        windowConf.rawset("msaa", ValueFactory.valueOf(0));
        windowConf.rawset("display", ValueFactory.valueOf(1));
        windowConf.rawset("highdpi", ValueFactory.valueOf(false));
        windowConf.rawset("x", Constants.NIL);
        windowConf.rawset("y", Constants.NIL);
        conf.rawset("window", windowConf);

        final LuaTable modulesConf = new LuaTable();
        modulesConf.rawset("audio", ValueFactory.valueOf(true));
        modulesConf.rawset("event", ValueFactory.valueOf(true));
        modulesConf.rawset("graphics", ValueFactory.valueOf(true));
        modulesConf.rawset("image", ValueFactory.valueOf(true));
        modulesConf.rawset("joystick", ValueFactory.valueOf(true));
        modulesConf.rawset("keyboard", ValueFactory.valueOf(true));
        modulesConf.rawset("math", ValueFactory.valueOf(true));
        modulesConf.rawset("mouse", ValueFactory.valueOf(true));
        modulesConf.rawset("physics", ValueFactory.valueOf(true));
        modulesConf.rawset("sound", ValueFactory.valueOf(true));
        modulesConf.rawset("system", ValueFactory.valueOf(true));
        modulesConf.rawset("timer", ValueFactory.valueOf(true));
        modulesConf.rawset("touch", ValueFactory.valueOf(true));
        modulesConf.rawset("video", ValueFactory.valueOf(true));
        modulesConf.rawset("window", ValueFactory.valueOf(true));
        modulesConf.rawset("thread", ValueFactory.valueOf(true));
        conf.rawset("modules", modulesConf);

        return conf;
    }

    private LovePlatform() {
    }

}
