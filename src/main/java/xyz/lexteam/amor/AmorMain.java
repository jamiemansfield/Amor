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

import static com.google.common.io.Resources.getResource;

import xyz.lexteam.amor.util.OperatingSystem;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The Main-Class behind Amor, the alternate love2d game engine
 * implementation.
 */
public final class AmorMain {

    public static final Path NATIVES_PATH = Paths.get(OperatingSystem.getOs().getDataFolder(), "amor/natives");

    public static void main(final String[] args) {
        // Since LWJGL (and therefor Slick2D transitively) has native libraries of which
        // it is dependant on, Amor will first need to ensure that the environment is
        // set up to allow Amor to run.
        // To do this, there is a process of which Amor will have to go through upon
        // each launch.
        // 1. Extract every native, if it has'nt been
        //    a. Natives will be extracted to a directory under the user's home directory.
        // 2. Set the library path to the correct directory
        // 3. Play games
        // ---

        // Stage 1: Extract every native, if it has'nt been
        try {
            extractNatives(NATIVES_PATH);
        } catch (final IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        // Stage 2: Set the library path to the correct directory
        System.setProperty("org.lwjgl.librarypath", NATIVES_PATH.toFile().getAbsolutePath());

        // Stage 3: Play games
        // TODO: this stage
    }

    private static void extractNatives(final Path nativesDir) throws IOException {
        if (Files.notExists(nativesDir)) Files.createDirectory(nativesDir);

        switch (OperatingSystem.getOs()) {
            case MACOS:
                // jinput
                extractNative("libjinput-osx.jnilib", nativesDir);
                // lwjgl
                extractNative("liblwjgl.dylib", nativesDir);
                extractNative("openal.dylib", nativesDir);
                break;
            case LINUX:
                // jinput
                extractNative("libjinput-linux.so", nativesDir);
                extractNative("libjinput-linux64.so", nativesDir);
                // lwjgl
                extractNative("liblwjgl.so", nativesDir);
                extractNative("liblwjgl64.so", nativesDir);
                extractNative("libopenal.so", nativesDir);
                extractNative("libopenal64.so", nativesDir);
                break;
            case WINDOWS:
                // jinput
                extractNative("jinput-dx8.dll", nativesDir);
                extractNative("jinput-dx8_64.dll", nativesDir);
                extractNative("jinput-raw.dll", nativesDir);
                extractNative("jinput-raw_64.dll", nativesDir);
                extractNative("jinput-wintab.dll", nativesDir);
                // lwjgl
                extractNative("lwjgl.dll", nativesDir);
                extractNative("lwjgl64.dll", nativesDir);
                extractNative("OpenAL32.dll", nativesDir);
                extractNative("OpenAL64.dll", nativesDir);
                break;
        }
    }

    private static void extractNative(final String nativeFile, final Path nativesDir) throws IOException {
        final Path path = nativesDir.resolve(nativeFile);
        if (Files.exists(path)) return;

        try (final ReadableByteChannel source = Channels.newChannel(getResource(nativeFile).openConnection().getInputStream());
                final FileChannel out = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            out.transferFrom(source, 0, Long.MAX_VALUE);
        }
    }

    private AmorMain() {
    }

}
