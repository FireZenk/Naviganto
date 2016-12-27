package org.firezenk.conceptrouter.library;

import javax.annotation.Nonnull;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class ParameterNotFoundException extends Exception {

    public ParameterNotFoundException(@Nonnull String message) {
        super(message);
    }
}
