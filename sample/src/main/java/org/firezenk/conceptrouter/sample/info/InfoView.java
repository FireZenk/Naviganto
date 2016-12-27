package org.firezenk.conceptrouter.sample.info;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import org.firezenk.conceptrouter.library.ConceptRouter;
import org.firezenk.conceptrouter.library.Route;
import org.firezenk.conceptrouter.sample.R;
import org.firezenk.conceptrouter.sample.detail.DetailActivityRoute;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
class InfoView extends FrameLayout {

    public InfoView(Context context) {
        super(context);
        inflate(getContext(), R.layout.feature_info, this);

        findViewById(R.id.open_detail).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                final Object[] params = new Object[3];
                params[0] = "This is the detail param 0";
                params[1] = " and this is the detail param 1 ";
                params[2] = 101;

                ConceptRouter.get().routeTo(getContext(), new Route(DetailActivityRoute.class, params));
            }
        });
    }
}
