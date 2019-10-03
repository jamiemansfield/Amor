/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package me.jamiemansfield.amor.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;

/**
 * A utility class for added convenience when working with the {@link Clipboard}
 * class.
 */
public final class ClipboardUtil {

    /**
     * Gets the system clipboard.
     *
     * @return The clipboard
     */
    private static Clipboard getClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    /**
     * Gets the contents of the system clipboard.
     *
     * @return The clipboard contents
     */
    public static Optional<String> getContents() {
        final Transferable transferable = getClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return Optional.of((String) transferable.getTransferData(DataFlavor.stringFlavor));
            } catch (final UnsupportedFlavorException | IOException ignored) {
            }
        }
        return Optional.empty();
    }

    /**
     * Sets the contents of the system clipboard.
     *
     * @param contents The clipboard contents
     */
    public static void setContents(final String contents) {
        try {
            getClipboard().setContents(new StringSelection(contents), null);
        } catch (final Exception ignored) {
        }
    }

    private ClipboardUtil() {
    }

}
