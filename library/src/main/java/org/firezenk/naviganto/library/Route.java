package org.firezenk.naviganto.library;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Copyright © Jorge Garrido Oval 2016
 */
public class Route<B> {

    @Nonnull Class clazz;
    @Nullable B bundle;
    @Nullable Object[] params;
    @Nullable Object viewParent;

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params) {
        this.clazz = clazz;
        if (params instanceof Object[]) {
            this.params = (Object[]) params;
        } else {
            this.bundle = (B) params;
        }
    }

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Object viewParent) {
        this.clazz = clazz;
        if (params instanceof Object[]) {
            this.params = (Object[]) params;
        } else {
            this.bundle = (B) params;
        }
        this.viewParent = viewParent;
    }

    @Override public boolean equals(Object obj) {
        return obj instanceof Route && this.clazz.equals(((Route) obj).clazz);
    }
}
