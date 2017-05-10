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
    @Nullable private Integer forResult;
    @Nonnull Boolean isRootView = Boolean.FALSE;

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params) {
        this.clazz = clazz;
        this.getExtras(params);
    }

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params, @Nonnull Boolean isRootView) {
        this.clazz = clazz;
        this.getExtras(params);
        this.isRootView = isRootView;
    }

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Integer forResult) {
        this.clazz = clazz;
        this.getExtras(params);
        this.setForResult(forResult);
    }

    @SuppressWarnings("unchecked") public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Integer forResult, @Nonnull Boolean isRootView) {
        this.clazz = clazz;
        this.getExtras(params);
        this.setForResult(forResult);
        this.isRootView = isRootView;
    }

    public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Object viewParent) {
        this.clazz = clazz;
        this.getExtras(params);
        this.viewParent = viewParent;
    }

    public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Object viewParent, @Nonnull Boolean isRootView) {
        this.clazz = clazz;
        this.getExtras(params);
        this.viewParent = viewParent;
        this.isRootView = isRootView;
    }

    public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Object viewParent, @Nullable Integer forResult) {
        this.clazz = clazz;
        this.getExtras(params);
        this.viewParent = viewParent;
        this.setForResult(forResult);
    }

    public Route(@Nonnull Class clazz, @Nonnull Object params, @Nullable Object viewParent, @Nullable Integer forResult, @Nonnull Boolean isRootView) {
        this.clazz = clazz;
        this.getExtras(params);
        this.viewParent = viewParent;
        this.setForResult(forResult);
        this.isRootView = isRootView;
    }

    @Override public boolean equals(Object obj) {
        return obj instanceof Route && this.clazz.equals(((Route) obj).clazz);
    }

    @SuppressWarnings("unchecked") private void getExtras(@Nonnull Object params) {
        try {
            this.params = (Object[]) params;
        } catch (ClassCastException ex) {
            this.bundle = (B) params;
        }
    }

    private void setForResult(@Nullable Integer forResult) {
        this.forResult = forResult == null ? -1 : forResult;
    }

    @Override public String toString() {
        return "Route class name: " + clazz.getSimpleName() +
                " Has bundle? " + (bundle != null) +
                " Has params? " + (params != null);
    }
}
