/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.audio;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.ZeroArgFunction;

/**
 * Represents an audio source.
 */
public abstract class AudioSource {

    private final SourceType type;

    protected AudioSource(final SourceType type) {
        this.type = type;
    }

    public abstract void play();

    public abstract boolean isPlaying();

    public LuaTable createTable() {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getType", new GetType());
        table.rawset("play", new Play());
        table.rawset("isPlaying", new IsPlaying());

        return table;
    }

    private class GetType extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(AudioSource.this.type.getLoveName());
        }

    }

    private class Play extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            AudioSource.this.play();
            return Constants.NIL;
        }

    }

    private class IsPlaying extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(AudioSource.this.isPlaying());
        }

    }

}
