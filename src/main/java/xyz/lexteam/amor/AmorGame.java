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

package xyz.lexteam.amor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;

public class AmorGame extends ApplicationAdapter {

    public static SpriteBatch BATCH;
    public static BitmapFont FONT;

    private final LuaState gameState;
    private final LuaTable gameGlobals;
    private final LuaTable conf;

    public AmorGame(final LuaState gameState, final LuaTable gameGlobals, final LuaTable conf) throws LuaError {
        this.gameState = gameState;
        this.gameGlobals = gameGlobals;
        this.conf = conf;
    }

    @Override
    public void create() {
        BATCH = new SpriteBatch();
        FONT = new BitmapFont();
        try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "load").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();

        try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "update").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }

        try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "draw").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }

        BATCH.end();
    }

    @Override
    public void dispose() {
        BATCH.dispose();
    }

}
