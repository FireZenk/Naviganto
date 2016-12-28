package org.firezenk.naviganto.sample.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.firezenk.naviganto.library.NotEnoughParametersException;
import org.firezenk.naviganto.library.ParameterNotFoundException;
import org.firezenk.naviganto.library.Routable;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 */
public class ProfileRoute<C extends Context, B extends Bundle> implements Routable<Context, Bundle> {

    @Override public void route(@NonNull Context context, @NonNull Bundle parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException {

        if (viewParent == null)
            throw new ParameterNotFoundException("Need a view parent");

        ((ViewGroup) viewParent).removeAllViews();
        ((ViewGroup) viewParent).addView(new ProfileView(context));
    }
}
