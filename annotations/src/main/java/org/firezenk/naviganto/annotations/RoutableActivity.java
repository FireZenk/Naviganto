package org.firezenk.naviganto.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval on 04/11/2016.
 * Copyright © Jorge Garrido Oval 2016
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface RoutableActivity {

    /**
     * Possible "bundle" extras
     * @return array class types for the params
     */
    Class[] params();

    /**
     * Define the result code for the activity
     * @return the result code, -1 if not needed
     */
    int forResult();
}
