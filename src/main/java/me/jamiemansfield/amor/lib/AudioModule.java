/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.lib;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.TwoArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import me.jamiemansfield.amor.audio.SourceType;

/**
 * A subclass of {@link LibFunction} used to implement the audio module.
 */
public class AudioModule implements LuaLibrary {

    @Override
    public LuaValue add(final LuaState state, final LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("newSource", new NewSource());

        state.loadedPackages.rawset("love.audio", table);
        return table;
    }

    private static final class NewSource extends TwoArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg1, final LuaValue arg2) throws LuaError {
            final SourceType type = SourceType.from(arg2.checkString());
            return type.create(arg1.checkString()).createTable();
        }

    }

}
