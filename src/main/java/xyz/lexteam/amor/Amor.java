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

import org.newdawn.slick.AppGameContainer;

/**
 * The 'brains of the operation'.
 */
public final class Amor {

    private static AppGameContainer gameContainer;
    private static AmorGame         game;

    public static AppGameContainer getGameContainer() {
        return Amor.gameContainer;
    }

    public static void setGameContainer(final AppGameContainer gameContainer) {
        if (Amor.gameContainer != null) {
            throw new RuntimeException("The gameContainer has already been set!");
        }
        Amor.gameContainer = gameContainer;
    }

    public static AmorGame getGame() {
        return Amor.game;
    }

    public static void setGame(final AmorGame game) {
        if (Amor.game != null) {
            throw new RuntimeException("The game has already been set!");
        }
        Amor.game = game;
    }

    private Amor() {
    }

}
