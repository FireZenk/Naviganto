package org.firezenk.conceptrouter.sample.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import org.firezenk.conceptrouter.library.ConceptRouter;
import org.firezenk.conceptrouter.library.Route;
import org.firezenk.conceptrouter.sample.R;
import org.firezenk.conceptrouter.sample.info.InfoRoute;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class HomeActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        final ViewGroup placeholder = (ViewGroup) findViewById(R.id.placeholder);

        ConceptRouter.get().routeTo(this, new Route<>(InfoRoute.class, new Bundle(), placeholder));
    }

    @Override public void onBackPressed() {
        if (!ConceptRouter.get().back(this))
            super.onBackPressed();
    }
}
