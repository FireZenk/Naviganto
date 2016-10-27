package org.firezenk.conceptrouter.sample.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.firezenk.conceptrouter.library.NotEnoughParametersException;
import org.firezenk.conceptrouter.library.ParameterNotFoundException;
import org.firezenk.conceptrouter.library.Routable;

/**
 * Project: ConceptRouter2
 *
 * Created by Jorge Garrido Oval on 27/10/16.
 * Copyright © Mr.Milú 2016
 */
public class HomeRoute implements Routable {

    @Override public void route(@NonNull Context context, @NonNull Bundle parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
