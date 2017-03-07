package org.firezenk.naviganto.library;

import javax.annotation.Nonnull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Copyright © Jorge Garrido Oval 2016
 */
public class Naviganto<C> implements INaviganto<C> {

    private static Naviganto INSTANCE;

    private ArrayList<ComplexRoute> history = new ArrayList<>();

    private class ComplexRoute {

        final Route route;
        final ArrayDeque<Route> viewHistory;

        ComplexRoute(@Nonnull Route route, @Nonnull ArrayDeque<Route> viewHistory) {
            this.route = route;
            this.viewHistory = viewHistory;
        }
    }

    public static <C> Naviganto get() {
        return (INSTANCE == null) ? INSTANCE = new Naviganto() : INSTANCE;
    }

    @Override @SuppressWarnings("unchecked") public void routeTo(@Nonnull C context, @Nonnull Route route) {
        final Route prev = history.isEmpty() ? null : history.get(history.size() - 1).viewHistory.peek();
        try {
            if (prev == null || route.viewParent == null || !areRoutesEqual(prev, route)) {

                if (route.bundle != null)
                    ((Routable) route.clazz.newInstance()).route(context, route.bundle, route.viewParent);
                else
                    ((org.firezenk.naviganto.processor.interfaces.Routable) route.clazz.newInstance()).route(context, route.params, route.viewParent);

                if (history.size() == 0)
                    createStartRoute();
                else if (route.viewParent == null)
                    createIntermediateRoute(route);
                else
                    createViewRoute(route);
            }
        } catch (ClassCastException e1) {
            System.out.println("Params has to be instance of Object[] or Android's Bundle");
            e1.printStackTrace();
        } catch (ParameterNotFoundException | NotEnoughParametersException
                | InstantiationException | IllegalAccessException
                | org.firezenk.naviganto.processor.exceptions.NotEnoughParametersException
                | org.firezenk.naviganto.processor.exceptions.ParameterNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    @Override public boolean back(@Nonnull C context) {
        if (history.isEmpty()) {
            return false;
        } else if (history.get(getHistoryLast()).viewHistory.isEmpty()) {
            history.remove(getHistoryLast());

            return false;
        } else {
            history.get(getHistoryLast()).viewHistory.pop();

            if (!history.get(getHistoryLast()).viewHistory.isEmpty()) {
                this.routeTo(context, history.get(getHistoryLast()).viewHistory.pop());
                return true;
            } else {
                return back(context);
            }
        }
    }

    @Override public boolean backTimes(@Nonnull C context, @Nonnull Integer times) {
        try {
            for (int i = 0; i < times; i++) {
                if (!back(context)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Is not possible to go back " +  times +
                                       " times, the history length is " + history.size());
            e.printStackTrace();
            return false;
        }
    }

    @Override public boolean backTo(@Nonnull C context, @Nonnull Route route) {
        if (history.isEmpty()) {
            System.out.println("Is not possible to go back, history is empty");
            return false;
        } else if (history.get(getHistoryLast()).viewHistory.isEmpty()) {
            history.remove(getHistoryLast());
            return backTo(context, route);
        } else {
            ComplexRoute complexRoute = history.get(getHistoryLast());

            if (history.size() == 1 && complexRoute.route == null) {
                System.out.println("Is not possible to go back, there is no route like: "
                                           + route.clazz.getName());
                return false;
            } else if (complexRoute.route.clazz.equals(route.clazz)) {
                history.remove(getHistoryLast());
                this.routeTo(context, complexRoute.route);
                return true;
            } else if (!complexRoute.viewHistory.isEmpty()) {
                int size = complexRoute.viewHistory.size();
                for (int i = size; i > 0; i--) {
                    Route prevRoute = complexRoute.viewHistory.pop();
                    if (route.clazz.equals(prevRoute.clazz)) {
                        this.routeTo(context, prevRoute);
                        return true;
                    }
                }
            }
            history.remove(getHistoryLast());
            return backTo(context, route);
        }
    }

    public void clearHistory() {
        this.history.clear();
    }

    @SuppressWarnings("ConstantConditions") private void createStartRoute() {
        history.add(new ComplexRoute(null, new ArrayDeque<Route>()));
    }

    private void createIntermediateRoute(@Nonnull Route route) {
        history.add(new ComplexRoute(route, new ArrayDeque<Route>()));
    }

    private void createViewRoute(@Nonnull Route route) {
        history.get(getHistoryLast()).viewHistory.addFirst(route);
    }

    private int getHistoryLast() {
        return history.size() - 1;
    }

    private boolean areRoutesEqual(Route prev, Route next) {
        return prev.equals(next)
                && ((prev.bundle != null && prev.bundle.equals(next.bundle))
                || (prev.params != null && Arrays.equals(prev.params, next.params))
        );
    }
}
