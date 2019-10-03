/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.audio;

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
