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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.Varargs;
import org.squiddev.cobalt.function.LibFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ThreeArgFunction;
import org.squiddev.cobalt.function.VarArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;
import xyz.lexteam.amor.AmorGame;
import xyz.lexteam.amor.graphics.Image;

/**
 * A subclass of {@link LibFunction} used to implement the graphics module.
 */
public class GraphicsModule implements LuaLibrary {

    private Color color = Color.BLACK;

    @Override
    public LuaValue add(LuaState state, LuaTable environment) {
        final LuaTable table = new LuaTable();

        // Functions
        table.rawset("reset", new Reset());
        table.rawset("newImage", new NewImage());
        table.rawset("getWidth", new GetWidth());
        table.rawset("getHeight", new GetHeight());
        table.rawset("setBackgroundColor", new SetBackgroundColour());
        table.rawset("draw", new Draw());
        table.rawset("print", new Print());
        table.rawset("setColor", new SetColor());
        table.rawset("circle", new Circle());

        environment.rawset("graphics", table);
        return table;
    }

    private static class Reset extends ZeroArgFunction {

        @Override
        public LuaValue call(final LuaState state) throws LuaError {
            // TODO: Implement
            return Constants.NIL;
        }

    }

    private static class NewImage extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
            return new Image(arg.checkString()).createTable();
        }

    }

    private static final class GetWidth extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getWidth());
        }

    }

    private static final class GetHeight extends ZeroArgFunction {

        @Override
        public LuaValue call(LuaState state) throws LuaError {
            return ValueFactory.valueOf(Gdx.graphics.getHeight());
        }

    }

    private static class SetBackgroundColour extends ThreeArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg1, final LuaValue arg2, final LuaValue arg3) throws LuaError {
            Gdx.gl.glClearColor((float) arg1.checkDouble() / 255f, (float) arg2.checkDouble() / 255f, (float) arg3.checkDouble() / 255f, 1);
            return Constants.NIL;
        }

    }

    private static class Draw extends OneArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg) throws LuaError {
            return arg.checkTable().rawget("_draw").checkFunction()
                    .call(state, ValueFactory.valueOf(0), ValueFactory.valueOf(0));
        }

    }

    private class Print extends ThreeArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg1, final LuaValue arg2, final LuaValue arg3) throws LuaError {
            AmorGame.FONT.setColor(GraphicsModule.this.color);
            AmorGame.FONT.draw(AmorGame.BATCH, arg1.checkString(), arg2.checkInteger(), arg3.checkInteger());
            return Constants.NIL;
        }

    }

    private class SetColor extends ThreeArgFunction {

        @Override
        public LuaValue call(final LuaState state, final LuaValue arg1, final LuaValue arg2, final LuaValue arg3) throws LuaError {
            GraphicsModule.this.color = new Color(arg1.checkInteger(), arg2.checkInteger(), arg3.checkInteger(), 1);
            return Constants.NIL;
        }

    }

    private class Circle extends VarArgFunction {

        @Override
        public Varargs invoke(final LuaState state, final Varargs args) throws LuaError {
            final int x = args.arg(2).checkInteger();
            final int y = args.arg(3).checkInteger();
            final int radius = args.arg(4).checkInteger();
            final int diameter = 2 * radius;

            final Pixmap pixmap = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
            pixmap.setColor(GraphicsModule.this.color);
            pixmap.fillCircle(radius, radius, radius);

            AmorGame.BATCH.draw(new Texture(pixmap), x, y);
            return Constants.NIL;
        }

    }

}
