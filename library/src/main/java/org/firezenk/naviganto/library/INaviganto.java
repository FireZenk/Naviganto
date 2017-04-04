package org.firezenk.naviganto.library;

import javax.annotation.Nonnull;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 07/03/17.
 * Copyright © Jorge Garrido Oval 2016
 */
interface INaviganto<C> {

    /**
     * Enables debug mode for all the navigation session
     * @param debugMode true or false for all the session
     * @return the instance
     */
    Naviganto debug(boolean debugMode);

    /**
     * Navigate to the next route
     * @param context The Android's context (required for Android)
     * @param route The target route
     */
    <C> void routeTo(@Nonnull C context, @Nonnull Route route);


    /**
     * Navigate to the last route available on history
     * @param context The Android's context (required for Android)
     */
    <C> void routeToLast(@Nonnull C context);

    /**
     * Go back to the directly previous route
     * @param context The Android's context (required for Android)
     * @return true if go back is possible, false if is the end of navigation history
     */
    <C> boolean back(@Nonnull C context);

    /**
     * Navigate back n times
     * @param context The Android's context (required for Android)
     * @param times The n times that we need to navigate backwards
     * @return true if go back n times is possible, false if is the end of navigation history
     */
    <C> boolean backTimes(@Nonnull C context, @Nonnull Integer times);

    /**
     * Navigate through the navigation history until find the route
     * @param context The Android's context (required for Android)
     * @param route The route (params not needed) that we want to navigate back to
     * @return true if go back to this route is possible, false if it is not
     */
    <C> boolean backTo(@Nonnull C context, @Nonnull Route route);

    /**
     * Clear navigation history
     */
    void clearHistory();

    /**
     * Has history or not helper
     * @return true if the history is not empty
     */
    boolean hasHistory();
}
