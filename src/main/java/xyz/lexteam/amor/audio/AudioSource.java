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

package xyz.lexteam.amor.audio;

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

    public abstract boolean isStopped();

    public LuaTable createTable() {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("getType", new GetType());
        table.rawset("play", new Play());
        table.rawset("isStopped", new IsStopped());

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

    private class IsStopped extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            return ValueFactory.valueOf(AudioSource.this.isStopped());
        }

    }

}
