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
    private static Boolean DEBUG = Boolean.FALSE;
    private static String DEBUG_TAG = "Naviganto::";

    private ArrayList<ComplexRoute> history = new ArrayList<>();

    private class ComplexRoute {

        final Route route;
        final ArrayDeque<Route> viewHistory;

        ComplexRoute(@Nonnull Route route, @Nonnull ArrayDeque<Route> viewHistory) {
            this.route = route;
            this.viewHistory = viewHistory;
        }

        @Override public String toString() {
            return route + " viewHistory size: " + viewHistory.size();
        }
    }

    public static <C> Naviganto get() {
        return (INSTANCE == null) ? INSTANCE = new Naviganto() : INSTANCE;
    }

    public Naviganto debug(boolean debugMode) {
        DEBUG = debugMode;
        return INSTANCE;
    }

    @Override @SuppressWarnings("unchecked") public <C> void routeTo(@Nonnull C context, @Nonnull Route route) {
        final Route prev = history.isEmpty() ? null : history.get(history.size() - 1).viewHistory.peek();
        try {
            if (prev == null || route.viewParent == null || !areRoutesEqual(prev, route)) {

                log(" --->> Next");
                log(" Navigating to: ", route);

                if (route.bundle != null) {
                    ((Routable) route.clazz.newInstance()).route(context, route.bundle, route.viewParent);
                } else {
                    ((org.firezenk.naviganto.processor.interfaces.Routable) route.clazz.newInstance()).route(context, route.params, route.viewParent);
                }

                if (history.size() == 0) {
                    createStartRoute();
                }

                if (route.viewParent == null) {
                    createIntermediateRoute(route);
                } else {
                    createViewRoute(route);
                }
            }
        } catch (ClassCastException e1) {
            log(" Params has to be instance of Object[] or Android's Bundle ", e1);
        } catch (ParameterNotFoundException | NotEnoughParametersException
                | InstantiationException | IllegalAccessException
                | org.firezenk.naviganto.processor.exceptions.NotEnoughParametersException
                | org.firezenk.naviganto.processor.exceptions.ParameterNotFoundException e2) {
            log(" Navigation error; ", e2);
        }
    }

    @Override public <C> void routeToLast(@Nonnull C context) {
        routeTo(context, history.get(getHistoryLast()).viewHistory.pop());
    }

    @Override public <C1> void routeToLast(@Nonnull C1 context, @Nonnull Object viewParent) {
        Route route = history.get(getHistoryLast()).viewHistory.pop();
        route.viewParent = viewParent;
        routeTo(context, route);
    }

    @Override public <C> boolean back(@Nonnull C context) {
        log(" <<--- Back");
        log(" History: ", history);

        if (history.isEmpty()) {
            return false;
        } else if (!history.get(getHistoryLast()).viewHistory.isEmpty()) {
            log(" Removing last: ", history.get(getHistoryLast()).viewHistory.pop());

            if (!history.get(getHistoryLast()).viewHistory.isEmpty()) {
                routeTo(context, history.get(getHistoryLast()).viewHistory.pop());
                return true;
            }
        } else {
            history.remove(getHistoryLast());
        }

        return back(context);
    }

    @Override public <C> boolean backTimes(@Nonnull C context, @Nonnull Integer times) {
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
            if (DEBUG) e.printStackTrace();
            return false;
        }
    }

    @Override public <C> boolean backTo(@Nonnull C context, @Nonnull Route route) {
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

    @Override public void clearHistory() {
        history.clear();
    }

    @Override public boolean hasHistory() {
        return !history.isEmpty();
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

    private void log(String actionDesc) {
        if (DEBUG) {
            System.out.println(DEBUG_TAG + actionDesc);
        }
    }

    private Route log(String actionDesc, Route route) {
        if (DEBUG) {
            System.out.println(DEBUG_TAG + actionDesc + route);
        }
        return route;
    }

    private ArrayList<ComplexRoute> log(String actionDesc, ArrayList<ComplexRoute> history) {
        if (DEBUG) {
            if (history.size() > 0 && history.get(getHistoryLast()) != null) {
                System.out.println(DEBUG_TAG + actionDesc + "size: " + history.size());
                System.out.println(DEBUG_TAG + actionDesc + "last: " + history.get(getHistoryLast()));
                if (history.get(getHistoryLast()) != null && history.get(getHistoryLast()).viewHistory.size() > 0) {
                    System.out.println(DEBUG_TAG + actionDesc + "internal history size: " + history.get(getHistoryLast()).viewHistory.size());
                    System.out.println(DEBUG_TAG + actionDesc + "internal history last: " + history.get(getHistoryLast()).viewHistory.peek());
                }
            }
        }
        return history;
    }

    private void log(String actionDesc, Throwable throwable) {
        if (DEBUG) {
            System.out.println(DEBUG_TAG + actionDesc);
            throwable.printStackTrace();
        }
    }
}
