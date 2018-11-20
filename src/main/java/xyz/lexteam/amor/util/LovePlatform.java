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

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
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
