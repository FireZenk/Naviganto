package org.firezenk.conceptrouter.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class Route {

    @NonNull Class clazz;
    @NonNull Bundle bundle;
    @Nullable Object viewParent;

    public Route(@NonNull Class clazz, @NonNull Bundle bundle) {
        this.clazz = clazz;
        this.bundle = bundle;
    }

    public Route(@NonNull Class clazz, @NonNull Bundle bundle, @Nullable Object viewParent) {
        this.clazz = clazz;
        this.bundle = bundle;
        this.viewParent = viewParent;
    }

    @Override public boolean equals(Object obj) {
        return obj instanceof Route && this.clazz.equals(((Route) obj).clazz);
    }
}
