package org.firezenk.conceptrouter.library;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public interface Routable<C, B> {

    void route(@Nonnull C context, @Nonnull B parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException;
}
