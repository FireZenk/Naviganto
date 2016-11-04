package org.firezenk.conceptrouter.sample.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.firezenk.conceptrouter.library.ConceptRouter;
import org.firezenk.conceptrouter.library.Route;
import org.firezenk.conceptrouter.processor.annotations.RoutableActivity;
import org.firezenk.conceptrouter.sample.R;
import org.firezenk.conceptrouter.sample.home.HomeRoute;
import org.firezenk.conceptrouter.sample.product.ProductRoute;
import org.firezenk.conceptrouter.sample.profile.ProfileRoute;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
@RoutableActivity(
        target = DetailActivity.class,
        extras = {String.class, View.class}
)
public class DetailActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        final ViewGroup placeholder = (ViewGroup) findViewById(R.id.placeholder);
        final Button nextButton = (Button) findViewById(R.id.next);
        final Button actButton = (Button) findViewById(R.id.act);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ConceptRouter.get().routeTo(DetailActivity.this, new Route(ProductRoute.class, new Bundle(), placeholder));
            }
        });

        actButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ConceptRouter.get().routeTo(DetailActivity.this, new Route(HomeRoute.class, new Bundle()));
            }
        });

        ConceptRouter.get().routeTo(this, new Route(ProfileRoute.class, new Bundle(), placeholder));
    }

    @Override public void onBackPressed() {
        if (!ConceptRouter.get().back(this))
            super.onBackPressed();
    }
}
