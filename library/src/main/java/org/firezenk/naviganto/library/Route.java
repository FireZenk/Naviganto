package org.firezenk.naviganto.library;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 */
public class Route<B> {

    @Nonnull Class clazz;
    @Nullable B bundle;
    @Nullable Object[] params;
    @Nullable Object viewParent;

    public Route(@Nonnull Class clazz, @Nonnull B bundle) {
        this.clazz = clazz;
        this.bundle = bundle;
    }

    public Route(@Nonnull Class clazz, @Nonnull Object[] params) {
        this.clazz = clazz;
        this.params = params;
    }

    public Route(@Nonnull Class clazz, @Nonnull B bundle, @Nullable Object viewParent) {
        this.clazz = clazz;
        this.bundle = bundle;
        this.viewParent = viewParent;
    }

    public Route(@Nonnull Class clazz, @Nonnull Object[] params, @Nullable Object viewParent) {
        this.clazz = clazz;
        this.params = params;
        this.viewParent = viewParent;
    }

    @Override public boolean equals(Object obj) {
        return obj instanceof Route && this.clazz.equals(((Route) obj).clazz);
    }
}
