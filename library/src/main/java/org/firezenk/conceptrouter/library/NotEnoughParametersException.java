package org.firezenk.conceptrouter.library;

import android.support.annotation.NonNull;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class NotEnoughParametersException extends Exception {

    public NotEnoughParametersException(@NonNull String message) {
        super(message);
    }
}
