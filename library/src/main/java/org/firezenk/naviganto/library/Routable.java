package org.firezenk.naviganto.library;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Copyright © Jorge Garrido Oval 2016
 */
public interface Routable<C, B> {

    void route(@Nonnull C context, UUID uuid, @Nonnull B parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException;
}
