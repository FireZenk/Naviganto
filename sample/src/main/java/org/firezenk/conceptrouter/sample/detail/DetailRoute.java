package org.firezenk.conceptrouter.sample.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.firezenk.conceptrouter.library.NotEnoughParametersException;
import org.firezenk.conceptrouter.library.ParameterNotFoundException;
import org.firezenk.conceptrouter.library.Routable;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class DetailRoute implements Routable {

    @Override public void route(@NonNull Context context, @NonNull Bundle parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException {

        if (parameters.getString("model1") == null && parameters.getString("model1") == null) {
            throw new NotEnoughParametersException("Need model1 and model2");
        } else if (parameters.getString("model1") == null) {
            throw new ParameterNotFoundException("Need model1");
        } else if (parameters.getString("model2") == null) {
            throw new ParameterNotFoundException("Need model2");
        } else {
            context.startActivity(new Intent(context, DetailActivity.class));
        }
    }
}
