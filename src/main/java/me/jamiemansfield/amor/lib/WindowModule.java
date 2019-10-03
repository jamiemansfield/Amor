/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.lib;

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
    public LuaValue add(final LuaState state, final LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getHeight", new GetHeight());
        table.rawset("getTitle", new GetTitle());
        table.rawset("getWidth", new GetWidth());
        table.rawset("setTitle", new SetTitle());

        state.loadedPackages.rawset("love.window", table);
        return table;
    }

    private static final class GetHeight extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getHeight());
        }

    }

    private static final class GetTitle extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            // TODO: Implement
            return Constants.NIL;
        }

    }

    private static final class GetWidth extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getWidth());
        }

    }

    private static final class SetTitle extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
            Gdx.graphics.setTitle(arg.checkString());
            return Constants.NIL;
        }

    }

}
