package org.firezenk.naviganto.processor.interfaces;

import org.firezenk.naviganto.processor.exceptions.NotEnoughParametersException;
import org.firezenk.naviganto.processor.exceptions.ParameterNotFoundException;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval on 04/11/2016.
 * Copyright © Jorge Garrido Oval 2016
 */
public interface Routable {

    void route(Object context, Object[] parameters, Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException;
}
