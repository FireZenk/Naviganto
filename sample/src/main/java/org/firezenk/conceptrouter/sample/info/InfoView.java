package org.firezenk.conceptrouter.sample.info;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.firezenk.conceptrouter.library.ConceptRouter;
import org.firezenk.conceptrouter.library.Route;
import org.firezenk.conceptrouter.sample.R;
import org.firezenk.conceptrouter.sample.detail.DetailRoute;

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
                final Bundle bundle = new Bundle();
                bundle.putString("model1", "hi!");
                bundle.putString("model2", "bye!");

                ConceptRouter.get().routeTo(getContext(), new Route(DetailRoute.class, bundle));
            }
        });
    }
}
