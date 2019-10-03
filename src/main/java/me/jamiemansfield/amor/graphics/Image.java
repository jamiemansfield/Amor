/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import me.jamiemansfield.amor.AmorGame;
import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.function.TwoArgFunction;

/**
 * An image.
 *
 * @see <a href="https://love2d.org/wiki/Image">Image</a>
 */
public class Image {

    private final Texture texture;

    public Image(final String fileName) {
        this.texture = new Texture(Gdx.files.local(fileName));
    }

    public LuaTable createTable() {
        final LuaTable table = new LuaTable();

        // Internal Functions
        table.rawset("_draw", new Draw());

        return table;
    }

    private class Draw extends TwoArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg1, final LuaValue arg2) throws LuaError {
            AmorGame.BATCH.draw(
                    Image.this.texture,
                    arg1.checkInteger(),
                    Gdx.graphics.getHeight() - Image.this.texture.getHeight() - arg2.checkInteger()
            );
            return Constants.NIL;
        }

    }

}
