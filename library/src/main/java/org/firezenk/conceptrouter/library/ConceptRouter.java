package org.firezenk.conceptrouter.library;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayDeque;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class ConceptRouter {

    private static ConceptRouter INSTANCE;

    private ArrayDeque<Route> history = new ArrayDeque<>();

    public static ConceptRouter get() {
        return (INSTANCE == null) ? INSTANCE = new ConceptRouter() : INSTANCE;
    }

    public void routeTo(@NonNull Context context, @NonNull Route route) {
        try {
            if (!history.contains(route)) {
                ((Routable) route.clazz.newInstance()).route(context, route.bundle, route.viewParent);
                history.addFirst(route);
            }
        } catch (ParameterNotFoundException | NotEnoughParametersException
                | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean back(@NonNull Context context) {
        if (history.isEmpty()) {
            return false;
        } else {
            history.pop();
        }

        if (history.isEmpty()) {
            return false;
        } else if (history.peek().viewParent == null) {
            history.pop();
        }

        if (history.isEmpty()) {
            return false;
        } else {
            this.routeTo(context, history.pop());
        }

        return true;
    }
}
