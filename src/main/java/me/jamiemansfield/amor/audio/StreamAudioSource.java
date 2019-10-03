/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * An implementation of {@link AudioSource} for stream sources.
 */
public class StreamAudioSource extends AudioSource {

    private final Music music;

    public StreamAudioSource(final String fileName) {
        super(SourceType.STREAM);
        this.music = Gdx.audio.newMusic(Gdx.files.local(fileName));
    }

    @Override
    public void play() {
        this.music.play();
    }

    @Override
    public boolean isPlaying() {
        return this.music.isPlaying();
    }

}
