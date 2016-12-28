package org.firezenk.naviganto.sample.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import org.firezenk.naviganto.library.Naviganto;
import org.firezenk.naviganto.library.Route;
import org.firezenk.naviganto.sample.R;
import org.firezenk.naviganto.sample.info.InfoRoute;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class HomeActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        final ViewGroup placeholder = (ViewGroup) findViewById(R.id.placeholder);

        Naviganto.get().routeTo(this, new Route<>(InfoRoute.class, new Bundle(), placeholder));
    }

    @Override public void onBackPressed() {
        if (!Naviganto.get().back(this))
            super.onBackPressed();
    }
}
