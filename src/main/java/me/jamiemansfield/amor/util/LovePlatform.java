/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.util;

import me.jamiemansfield.amor.lib.LoveLib;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.jse.JsePlatform;

/**
 * Convenience class to obtain a standard lua global table.
 */
public final class LovePlatform {

    /**
     * Creates a standard globals table for Amor.
     *
     * @param state The lua state
     * @return The globals table
     */
    public static LuaTable standardGlobals(final LuaState state) {
        final LuaTable globals = JsePlatform.standardGlobals(state);
        try {
            globals.rawget("os").checkTable().rawset("time", new ZeroArgFunction() {
                @Override
                public LuaValue call(final LuaState state) throws LuaError {
                    return ValueFactory.valueOf(System.currentTimeMillis() / 1000);
                }
            });
            globals.rawset("_requireModule", new OneArgFunction() {
                @Override
                public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
                    final LuaValue val = state.loadedPackages.rawget("love." + arg.checkString());
                    globals.rawget("love").checkTable().rawset(arg.checkString(), val);
                    return val;
                }
            });
        }
        catch (final LuaError ex) {
            ex.printStackTrace();
        }
        globals.load(state, new LoveLib());
        return globals;
    }

    private LovePlatform() {
    }

}
