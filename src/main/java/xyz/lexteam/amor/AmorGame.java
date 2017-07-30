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

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;

public class AmorGame extends BasicGame {

    private final LuaState gameState;
    private final LuaTable gameGlobals;
    private final LuaTable conf;

    private String amorTitle;

    public AmorGame(final LuaState gameState, final LuaTable gameGlobals, final LuaTable conf) throws LuaError {
        super(null);
        this.amorTitle = conf.get(gameState, "window").get(gameState, "title").checkString();
        this.gameState = gameState;
        this.gameGlobals = gameGlobals;
        this.conf = conf;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "load").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        /*try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "update").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }*/
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        /*try {
            this.gameGlobals.get(this.gameState, "love").get(this.gameState, "draw").checkFunction().call(this.gameState);
        } catch (final LuaError ex) {
            ex.printStackTrace();
        }*/
    }

    @Override
    public String getTitle() {
        return this.amorTitle;
    }

    public void setTitle(final String title) {
        this.amorTitle = title;
    }

}
