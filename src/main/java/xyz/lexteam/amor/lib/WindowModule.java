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

import com.badlogic.gdx.Gdx;
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

/**
 * A subclass of {@link LibFunction} used to implement the window module.
 */
public class WindowModule implements LuaLibrary {

    @Override
    public LuaValue add(LuaState state, LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getHeight", new GetHeight());
        table.rawset("getTitle", new GetTitle());
        table.rawset("getWidth", new GetWidth());
        table.rawset("setTitle", new SetTitle());

        environment.rawset("window", table);
        return table;
    }

    private static final class GetHeight extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getHeight());
        }

    }

    private static final class GetTitle extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            // TODO: Implement
            return Constants.NIL;
        }

    }

    private static final class GetWidth extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getWidth());
        }

    }

    private static final class SetTitle extends OneArgFunction {

        @Override
        public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
            Gdx.graphics.setTitle(arg.checkString());
            return Constants.NIL;
        }

    }

}
