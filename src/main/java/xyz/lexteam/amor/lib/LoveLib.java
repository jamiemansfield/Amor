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

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.Varargs;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.VarArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import xyz.lexteam.amor.util.OperatingSystem;

/**
 * A subclass of {@link LibFunction} used to implement the love2d library.
 */
public class LoveLib implements LuaLibrary {

    private static final int    MAJOR    = 0;
    private static final int    MINOR    = 10;
    private static final int    REVISION = 2;
    private static final String CODENAME = "Blue Banana";

    private final LuaTable conf;

    public LoveLib(final LuaTable conf) {
        this.conf = conf;
    }

    @Override
    public LuaValue add(LuaState state, LuaTable environment) {
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
        try {
            final LuaValue modules = this.conf.get(state, "modules");
            if (modules.get(state, "audio").checkBoolean()) table.load(state, new AudioModule());
            if (modules.get(state, "graphics").checkBoolean()) table.load(state, new GraphicsModule());
            if (modules.get(state, "system").checkBoolean()) table.load(state, new SystemModule());
            if (modules.get(state, "window").checkBoolean()) table.load(state, new WindowModule());
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }

        environment.rawset("love", table);
        state.loadedPackages.rawset("love", table);
        return table;
    }

    private static final class GetVersion extends VarArgFunction {

        @Override
        public Varargs invoke(LuaState state, Varargs args) throws LuaError {
            return ValueFactory.varargsOf(
                    ValueFactory.valueOf(MAJOR), ValueFactory.valueOf(MINOR), ValueFactory.valueOf(REVISION),
                    ValueFactory.valueOf(CODENAME)
            );
        }
    }

}
