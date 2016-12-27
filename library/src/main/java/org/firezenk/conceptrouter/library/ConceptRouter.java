package org.firezenk.conceptrouter.library;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class ConceptRouter {

    private static ConceptRouter INSTANCE;

    private ArrayList<ComplexRoute> history = new ArrayList<>();

    private class ComplexRoute {

        Route route;
        ArrayDeque<Route> viewHistory = new ArrayDeque<>();

        ComplexRoute(@NonNull Route route, @NonNull ArrayDeque<Route> viewHistory) {
            this.route = route;
            this.viewHistory = viewHistory;
        }
    }

    public static ConceptRouter get() {
        return (INSTANCE == null) ? INSTANCE = new ConceptRouter() : INSTANCE;
    }

    public void routeTo(@NonNull Context context, @NonNull Route route) {
        final Route prev = history.isEmpty() ? null : history.get(history.size() - 1).viewHistory.peek();
        try {
            if (prev == null || route.viewParent == null || !prev.equals(route)) {

                if (route.bundle != null)
                    ((Routable) route.clazz.newInstance()).route(context, route.bundle, route.viewParent);
                else
                    ((org.firezenk.conceptrouter.processor.interfaces.Routable) route.clazz.newInstance()).route(context, route.params, route.viewParent);

                if (history.size() == 0)
                    createStartRoute();
                else if (route.viewParent == null)
                    createIntermediateRoute(route);
                else
                    createViewRoute(route);
            }
        } catch (ParameterNotFoundException | NotEnoughParametersException
                | InstantiationException | IllegalAccessException
                | org.firezenk.conceptrouter.processor.exceptions.NotEnoughParametersException
                | org.firezenk.conceptrouter.processor.exceptions.ParameterNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean back(@NonNull Context context) {
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

    public void clearHistory() {
        this.history.clear();
    }

    @SuppressWarnings("ConstantConditions") private void createStartRoute() {
        history.add(new ComplexRoute(null, new ArrayDeque<Route>()));
    }

    private void createIntermediateRoute(@NonNull Route route) {
        history.add(new ComplexRoute(route, new ArrayDeque<Route>()));
    }

    private void createViewRoute(@NonNull Route route) {
        history.get(getHistoryLast()).viewHistory.addFirst(route);
    }

    private int getHistoryLast() {
        return history.size() - 1;
    }
}
