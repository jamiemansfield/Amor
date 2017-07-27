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

package xyz.lexteam.amor.lib;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import xyz.lexteam.amor.util.ClipboardUtil;
import xyz.lexteam.amor.util.OperatingSystem;

import java.awt.Desktop;
import java.net.URI;

/**
 * A subclass of {@link LibFunction} used to implement the system module.
 */
public class SystemModule implements LuaLibrary {

    @Override
    public LuaValue add(LuaState state, LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getClipboardText", new GetClipboardText());
        table.rawset("getOS", new GetOS());
        // TODO: https://love2d.org/wiki/love.system.getPowerInfo
        table.rawset("getProcessorCount", new GetProcessorCount());
        table.rawset("openURL", new OpenURL());
        table.rawset("setClipboardText", new SetClipboardText());
        // TODO: https://love2d.org/wiki/love.system.vibrate

        environment.rawset("system", table);
        return table;
    }

    public static final class GetClipboardText extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(ClipboardUtil.getContents().orElse(""));
        }

    }

    public static final class GetOS extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(OperatingSystem.getOs().getLoveName());
        }

    }

    public static final class GetProcessorCount extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(Runtime.getRuntime().availableProcessors());
        }

    }

    public static final class OpenURL extends OneArgFunction {

        @Override
        public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
            final String url = arg.checkString();
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url.replace(' ', '+')));
                    return ValueFactory.valueOf(true);
                }
            } catch (final Exception ignored) {
            }
            return ValueFactory.valueOf(false);
        }

    }

    public static final class SetClipboardText extends OneArgFunction {

        @Override
        public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
            ClipboardUtil.setContents(arg.checkString());
            return Constants.NIL;
        }

    }

}
