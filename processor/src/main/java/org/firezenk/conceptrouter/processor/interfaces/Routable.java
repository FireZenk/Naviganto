package org.firezenk.conceptrouter.processor.interfaces;

import org.firezenk.conceptrouter.processor.exceptions.NotEnoughParametersException;
import org.firezenk.conceptrouter.processor.exceptions.ParameterNotFoundException;

/**
 * Project: ConceptRouter
 *
 * Created by Jorge Garrido Oval on 04/11/2016.
 * Copyright © Jorge Garrido Oval 2016
 */
public interface Routable {

    void route(Object context, Object[] parameters, Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException;
}
