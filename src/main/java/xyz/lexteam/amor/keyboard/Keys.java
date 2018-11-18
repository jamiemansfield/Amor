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

package xyz.lexteam.amor.keyboard;

import com.badlogic.gdx.Input;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Keys {

    private static final Map<String, Integer> MAPPINGS = Collections.unmodifiableMap(new HashMap<String, Integer>() {
        {
            // Alphabet
            this.put("a", Input.Keys.A);
            this.put("b", Input.Keys.B);
            this.put("c", Input.Keys.C);
            this.put("d", Input.Keys.D);
            this.put("e", Input.Keys.E);
            this.put("f", Input.Keys.F);
            this.put("g", Input.Keys.G);
            this.put("h", Input.Keys.H);
            this.put("i", Input.Keys.I);
            this.put("j", Input.Keys.J);
            this.put("k", Input.Keys.K);
            this.put("l", Input.Keys.L);
            this.put("m", Input.Keys.M);
            this.put("n", Input.Keys.N);
            this.put("o", Input.Keys.O);
            this.put("p", Input.Keys.P);
            this.put("q", Input.Keys.Q);
            this.put("r", Input.Keys.R);
            this.put("s", Input.Keys.S);
            this.put("t", Input.Keys.T);
            this.put("u", Input.Keys.U);
            this.put("v", Input.Keys.V);
            this.put("w", Input.Keys.W);
            this.put("x", Input.Keys.X);
            this.put("y", Input.Keys.Y);
            this.put("z", Input.Keys.Z);

            // Arrow keys
            this.put("up", Input.Keys.UP);
            this.put("down", Input.Keys.DOWN);
            this.put("left", Input.Keys.LEFT);
            this.put("right", Input.Keys.RIGHT);
        }
    });

    public static int of(final String key) {
        return MAPPINGS.get(key.toLowerCase());
    }

    private Keys() {
    }

}
