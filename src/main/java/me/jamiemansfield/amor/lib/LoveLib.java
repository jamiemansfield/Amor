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
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.Varargs;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.VarArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import me.jamiemansfield.amor.util.OperatingSystem;

/**
 * A subclass of {@link LibFunction} used to implement the love2d library.
 */
public class LoveLib implements LuaLibrary {

    private static final int    MAJOR    = 11;
    private static final int    MINOR    = 1;
    private static final int    REVISION = 0;
    private static final String CODENAME = "Mysterious Mysteries";

    @Override
    public LuaValue add(final LuaState state, final LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Variables
        // - Version
        table.rawset("_version_major", ValueFactory.valueOf(MAJOR));
        table.rawset("_version_minor", ValueFactory.valueOf(MINOR));
        table.rawset("_version_revision", ValueFactory.valueOf(REVISION));
        // - OS
        table.rawset("_os", ValueFactory.valueOf(OperatingSystem.getOs().getLoveName()));

        // Functions
        // - Version
        table.rawset("getVersion", new GetVersion());

        // Modules
        table.load(state, new AudioModule());
        table.load(state, new GraphicsModule());
        table.load(state, new SystemModule());
        table.load(state, new WindowModule());
        table.load(state, new KeyboardModule());

        state.loadedPackages.rawset("love", table);
        environment.rawset("love", table);
        return table;
    }

    private static final class GetVersion extends VarArgFunction {

        @Override
        public Varargs invoke(final LuaState state, final Varargs args) throws LuaError {
            return ValueFactory.varargsOf(
                    ValueFactory.valueOf(MAJOR), ValueFactory.valueOf(MINOR), ValueFactory.valueOf(REVISION),
                    ValueFactory.valueOf(CODENAME)
            );
        }
    }

}
