/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.jamiemansfield.amor.util.LovePlatform;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.compiler.CompileException;
import org.squiddev.cobalt.compiler.LoadState;
import org.squiddev.cobalt.lib.platform.FileResourceManipulator;
import me.jamiemansfield.amor.util.OperatingSystem;

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
