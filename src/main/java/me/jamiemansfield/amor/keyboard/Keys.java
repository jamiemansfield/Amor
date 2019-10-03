/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.keyboard;

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
