/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.util;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Represents common operating systems.
 */
public enum OperatingSystem implements Predicate<String> {

    MACOS("OS X", "mac"),
    LINUX("Linux", "nix", "nux") {
        private final String home = System.getenv().getOrDefault("HOME", System.getProperty("user.home", "~"));
        
        private final String dataHome = System.getenv().getOrDefault("XDG_DATA_HOME", home + "/.local/share");
        private final String configHome = System.getenv().getOrDefault("XDG_CONFIG_HOME", home + "/.config");
        private final String cacheHome = System.getenv().getOrDefault("XDG_CACHE_HOME", home + "/.cache");
        
        @Override
        public String getConfigFolder() {
            return configHome;
        }
        
        @Override
        public String getDataFolder() {
            return dataHome;
        }
        
        @Override
        public String getCacheFolder() {
            return cacheHome;
        }
    },
    WINDOWS("Windows", "win"),
    UNKNOWN("Unknown");

    private final String loveName;
    private final String[] partials;

    OperatingSystem(final String loveName, final String... partials) {
        this.loveName = loveName;
        this.partials = partials;
    }

    @Override
    public boolean test(final String s) {
        return Arrays.stream(this.partials)
                .anyMatch(s::contains);
    }

    /**
     * Gets the identifier used by love to represent the operating system.
     *
     * @return The love name
     */
    public String getLoveName() {
        return this.loveName;
    }

    /**
     * Returns the path to the config home, as defined by the XDG Base Directory specification.
     * 
     * <p>The config home defines the base directory relative to which user specific configuration
     * files should be stored. On Linux, this can be modified by the environment variable
     * {@code $XDG_CONFIG_HOME}. On OS X, this is ~/Library/Application Support. On Windows,
     * this is %APPDATA%.
     * 
     * @return The path to the config home, as defined by the XDG Base Directory specification.
     */
    public String getConfigFolder() {
        switch (this) {
            case MACOS:
                return System.getProperty("user.home") + "/Library/Application Support";
            case WINDOWS:
                return System.getenv("APPDATA");
            case LINUX:
                throw new AssertionError();
            case UNKNOWN:
            default:
                return System.getProperty("user.home");
        }
    }
    
    /**
     * Returns the path to the data home, as defined by the XDG Base Directory specification.
     * 
     * <p>The data home defines the base directory relative to which user specific data
     * files should be stored. On Linux, this can be modified by the environment variable
     * {@code $XDG_DATA_HOME}. On other operating systems, this method is equivalent to
     * {@code getConfigFolder()}.
     * 
     * @return The path to the data home, as defined by the XDG Base Directory specification.
     */
    public String getDataFolder() {
        return this.getConfigFolder();
    }
    
    /**
     * Returns the path to the cache home, as defined by the XDG Base Directory specification.
     * 
     * <p>The cache home defines the base directory relative to which user specific
     * <i>non-essential</i> data files should be stored. On Linux, this can be modified
     * by the environment variable {@code $XDG_CACHE_HOME}. On other operating systems,
     * this method is equivalent to {@code getConfigFolder()}.
     * 
     * @return The path to the cache home, as defined by the XDG Base Directory specification.
     */
    public String getCacheFolder() {
        return this.getConfigFolder();
    }

    /**
     * Gets the operating system currently running on the user's system.
     *
     * @return The current {@link OperatingSystem}.
     */
    public static OperatingSystem getOs() {
        final String osName = System.getProperty("os.name").toLowerCase();

        return Arrays.stream(values())
                .filter(os -> os.test(osName))
                .findFirst()
                .orElse(OperatingSystem.UNKNOWN);
    }

}