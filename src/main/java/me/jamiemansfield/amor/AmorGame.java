/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
        final FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.classpath("assets/amor/font/Vera.ttf"));
        FONT = gen.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            this.size = 12;
        }
        });
        gen.dispose();
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
        FONT.dispose();
    }

}
