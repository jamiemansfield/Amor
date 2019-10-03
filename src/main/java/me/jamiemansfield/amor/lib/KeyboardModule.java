/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.lib;

import com.badlogic.gdx.Gdx;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import me.jamiemansfield.amor.keyboard.Keys;

/**
 * A subclass of {@link LibFunction} used to implement the keyboard module.
 */
public class KeyboardModule implements LuaLibrary {

    @Override
    public LuaValue add(final LuaState state, final LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("isDown", new IsDown());

        state.loadedPackages.rawset("love.keyboard", table);
        return table;
    }

    private static final class IsDown extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
            return ValueFactory.valueOf(Gdx.input.isKeyPressed(Keys.of(arg.checkString())));
        }

    }

}
