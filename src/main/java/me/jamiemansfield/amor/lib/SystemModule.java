/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.lib;

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
import me.jamiemansfield.amor.util.ClipboardUtil;
import me.jamiemansfield.amor.util.OperatingSystem;

import java.awt.Desktop;
import java.net.URI;

/**
 * A subclass of {@link LibFunction} used to implement the system module.
 */
public class SystemModule implements LuaLibrary {

    @Override
    public LuaValue add(final LuaState state, final LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getClipboardText", new GetClipboardText());
        table.rawset("getOS", new GetOS());
        // TODO: https://love2d.org/wiki/love.system.getPowerInfo
        table.rawset("getProcessorCount", new GetProcessorCount());
        table.rawset("openURL", new OpenURL());
        table.rawset("setClipboardText", new SetClipboardText());
        // TODO: https://love2d.org/wiki/love.system.vibrate

        state.loadedPackages.rawset("love.system", table);
        return table;
    }

    private static final class GetClipboardText extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(ClipboardUtil.getContents().orElse(""));
        }

    }

    private static final class GetOS extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(OperatingSystem.getOs().getLoveName());
        }

    }

    private static final class GetProcessorCount extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(Runtime.getRuntime().availableProcessors());
        }

    }

    private static final class OpenURL extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
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

    private static final class SetClipboardText extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
            ClipboardUtil.setContents(arg.checkString());
            return Constants.NIL;
        }

    }

}
