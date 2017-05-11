package org.firezenk.naviganto.sample.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.firezenk.naviganto.library.NotEnoughParametersException;
import org.firezenk.naviganto.library.ParameterNotFoundException;
import org.firezenk.naviganto.library.Routable;

import java.util.UUID;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval on 27/10/16.
 */
public class HomeRoute<C extends Context, B extends Bundle> implements Routable<Context, Bundle> {

    @Override public void route(@NonNull Context context, UUID uuid, @NonNull Bundle parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
