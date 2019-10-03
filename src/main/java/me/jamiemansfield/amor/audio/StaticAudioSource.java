/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * An implementation of {@link AudioSource} for static sources.
 */
public class StaticAudioSource extends AudioSource {

    private final Sound sound;

    public StaticAudioSource(final String fileName) {
        super(SourceType.STATIC);
        this.sound = Gdx.audio.newSound(Gdx.files.local(fileName));
    }

    @Override
    public void play() {
        this.sound.play();
    }

    @Override
    public boolean isPlaying() {
        // TODO: ?
        return true;
    }

}
