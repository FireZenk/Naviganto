package org.firezenk.conceptrouter.sample.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.firezenk.conceptrouter.library.ConceptRouter;
import org.firezenk.conceptrouter.library.Route;
import org.firezenk.conceptrouter.sample.R;
import org.firezenk.conceptrouter.sample.detail.DetailRoute;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
public class HomeActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        findViewById(R.id.open_detail).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                final Bundle bundle = new Bundle();
                bundle.putString("model1", "hi!");
                bundle.putString("model2", "bye!");

                ConceptRouter.get().routeTo(HomeActivity.this, new Route(DetailRoute.class, bundle));
            }
        });
    }
}
