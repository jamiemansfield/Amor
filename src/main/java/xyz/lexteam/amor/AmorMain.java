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

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.compiler.CompileException;
import org.squiddev.cobalt.compiler.LoadState;
import org.squiddev.cobalt.lib.jse.JsePlatform;
import org.squiddev.cobalt.lib.platform.FileResourceManipulator;
import xyz.lexteam.amor.util.LovePlatform;
import xyz.lexteam.amor.util.OperatingSystem;

import java.io.File;
import java.io.FileInputStream;
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

    public static final Path AMOR_PATH = Paths.get(OperatingSystem.getOs().getDataFolder(), "amor");
    public static final Path NATIVES_PATH = AMOR_PATH.resolve("natives");

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
            extractNatives();
        } catch (final IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        // Stage 2: Set the library path to the correct directory
        System.setProperty("org.lwjgl.librarypath", NATIVES_PATH.toFile().getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", NATIVES_PATH.toFile().getAbsolutePath());

        // Stage 3: Play games
        // - Phase 1: Read config
        final LuaTable conf = LovePlatform.standardConfiguration();
        if (Files.exists(Paths.get("conf.lua"))) {
            try {
                final LuaState confState = new LuaState(new FileResourceManipulator());
                final LuaTable confGlobals = JsePlatform.standardGlobals(confState);
                confGlobals.rawset("love", new LuaTable());
                LoadState.load(confState, new FileInputStream(new File("conf.lua")), "conf", confGlobals).call(confState);
                confGlobals.get(confState, "love").get(confState, "conf").checkFunction().call(confState, conf);
            } catch (final LuaError | IOException | CompileException ex) {
                ex.printStackTrace();
            }
        }

        // - Phase 2: Read game
        final LuaState gameState = new LuaState(new FileResourceManipulator());
        final LuaTable gameGlobals = LovePlatform.standardGlobals(gameState, conf);
        try {
            LoadState.load(gameState, new FileInputStream(new File("main.lua")), "main", gameGlobals).call(gameState);
        } catch (final LuaError | CompileException | IOException ex) {
            ex.printStackTrace();
        }

        // - Phase 3: Setup game container
        try {
            final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle(conf.get(gameState, "window").get(gameState, "title").checkString());
            config.setWindowedMode(
                    conf.get(gameState, "window").get(gameState, "width").checkInteger(),
                    conf.get(gameState, "window").get(gameState, "height").checkInteger()
            );
            config.setResizable(conf.get(gameState, "window").get(gameState, "resizable").checkBoolean());
            new Lwjgl3Application(new AmorGame(gameState, gameGlobals, conf), config);
        }
        catch (LuaError luaError) {
            luaError.printStackTrace();
        }
    }

    private static void extractNatives() throws IOException {
        if (Files.notExists(AMOR_PATH)) Files.createDirectory(AMOR_PATH);
        if (Files.notExists(NATIVES_PATH)) Files.createDirectory(NATIVES_PATH);

        switch (OperatingSystem.getOs()) {
            case MACOS:
                // jinput
                extractNative("libjinput-osx.jnilib");
                // lwjgl
                extractNative("liblwjgl.dylib");
                extractNative("openal.dylib");
                break;
            case LINUX:
                // jinput
                extractNative("libjinput-linux.so");
                extractNative("libjinput-linux64.so");
                // lwjgl
                extractNative("liblwjgl.so");
                extractNative("liblwjgl64.so");
                extractNative("libopenal.so");
                extractNative("libopenal64.so");
                break;
            case WINDOWS:
                // jinput
                extractNative("jinput-dx8.dll");
                extractNative("jinput-dx8_64.dll");
                extractNative("jinput-raw.dll");
                extractNative("jinput-raw_64.dll");
                extractNative("jinput-wintab.dll");
                // lwjgl
                extractNative("lwjgl.dll");
                extractNative("lwjgl64.dll");
                extractNative("OpenAL32.dll");
                extractNative("OpenAL64.dll");
                break;
        }
    }

    private static void extractNative(final String nativeFile) throws IOException {
        final Path path = NATIVES_PATH.resolve(nativeFile);
        if (Files.exists(path)) return;

        try (final ReadableByteChannel source = Channels.newChannel(getResource(nativeFile).openConnection().getInputStream());
                final FileChannel out = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            out.transferFrom(source, 0, Long.MAX_VALUE);
        }
    }

    private AmorMain() {
    }

}
