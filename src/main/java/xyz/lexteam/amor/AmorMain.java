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

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.compiler.CompileException;
import org.squiddev.cobalt.compiler.LoadState;
import org.squiddev.cobalt.lib.platform.FileResourceManipulator;
import xyz.lexteam.amor.util.LovePlatform;
import xyz.lexteam.amor.util.OperatingSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Main-Class behind Amor, the alternate love2d game engine
 * implementation.
 */
public final class AmorMain {

    public static final Path AMOR_PATH = Paths.get(OperatingSystem.getOs().getDataFolder(), "amor");

    public static void main(final String[] args) {
        // Run the boot script
        final LuaState state = new LuaState(new FileResourceManipulator());
        final LuaTable globals = LovePlatform.standardGlobals(state);
        LuaTable amor = new LuaTable();
        try {
            amor = LoadState.load(state, AmorMain.class.getResourceAsStream("/assets/amor/boot/boot.lua"), "boot", globals).call(state).checkTable();
        } catch (final LuaError | CompileException | IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        try {
            amor.get(state, "main").checkFunction().call(state);
        }
        catch (final LuaError luaError) {
            luaError.printStackTrace();
        }

        LuaTable conf = new LuaTable();
        try {
            conf = amor.rawget("config").checkTable();
        }
        catch (LuaError luaError) {
            luaError.printStackTrace();
        }

        // Setup game container
        try {
            final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle(conf.get(state, "window").get(state, "title").checkString());
            config.setWindowedMode(
                    conf.get(state, "window").get(state, "width").checkInteger(),
                    conf.get(state, "window").get(state, "height").checkInteger()
            );
            config.setResizable(conf.get(state, "window").get(state, "resizable").checkBoolean());
            new Lwjgl3Application(new AmorGame(state, globals, conf), config);
        }
        catch (LuaError luaError) {
            luaError.printStackTrace();
        }
    }

    private AmorMain() {
    }

}
