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

import java.util.Objects;

/**
 * Represents the source type of some audio.
 *
 * @see <a href="https://love2d.org/wiki/SourceType">SourceType</a>
 */
public enum SourceType {

    STATIC("static") {
        @Override
        public AudioSource create(final String fileName) {
            return new StaticAudioSource(fileName);
        }
    },
    STREAM("stream") {
        @Override
        public AudioSource create(final String fileName) {
            return new StreamAudioSource(fileName);
        }
    },
    QUEUE("queue") {
        @Override
        public AudioSource create(final String fileName) {
            return null;
        }
    },
    ;

    private final String loveName;

    SourceType(final String loveName) {
        this.loveName = loveName;
    }

    public String getLoveName() {
        return this.loveName;
    }

    public abstract AudioSource create(final String fileName);

    public static SourceType from(final String loveName) {
        for (final SourceType type : values()) {
            if (Objects.equals(type.loveName, loveName)) return type;
        }
        throw new IllegalArgumentException(loveName + " is an invalid source type!");
    }

}
