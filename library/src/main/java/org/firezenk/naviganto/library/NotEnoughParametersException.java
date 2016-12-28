package org.firezenk.naviganto.library;

import javax.annotation.Nonnull;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 */
public class NotEnoughParametersException extends Exception {

    public NotEnoughParametersException(@Nonnull String message) {
        super(message);
    }
}
