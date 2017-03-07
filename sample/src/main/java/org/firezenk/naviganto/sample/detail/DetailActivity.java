package org.firezenk.naviganto.sample.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.firezenk.naviganto.annotations.RoutableActivity;
import org.firezenk.naviganto.library.Naviganto;
import org.firezenk.naviganto.library.Route;
import org.firezenk.naviganto.sample.R;
import org.firezenk.naviganto.sample.home.HomeActivity;
import org.firezenk.naviganto.sample.home.HomeRoute;
import org.firezenk.naviganto.sample.product.ProductViewRoute;
import org.firezenk.naviganto.sample.profile.ProfileRoute;

import java.util.Random;

/**
 * Project: Naviganto
 *
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 */
@RoutableActivity(params = {String.class, String.class, Integer.class}, requestCode = -1)
public class DetailActivity extends AppCompatActivity {

    private int times = 0;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        final ViewGroup placeholder = (ViewGroup) findViewById(R.id.placeholder);
        final TextView title = (TextView) findViewById(R.id.title);
        final Button nextButton = (Button) findViewById(R.id.next);
        final Button prevButton = (Button) findViewById(R.id.prev);
        final Button actButton = (Button) findViewById(R.id.act);
        final Button prevActButton = (Button) findViewById(R.id.prev_act);
        final Button timesButton = (Button) findViewById(R.id.times);

        title.setText(getIntent().getStringExtra("string0") + getIntent().getStringExtra("string1") + getIntent().getIntExtra("int2", 0));

        updateTimesButton(timesButton, times);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                updateTimesButton(timesButton, times++);
                final Object[] params = new Object[1];
                params[0] = new Random().nextDouble();
                Naviganto.get().routeTo(DetailActivity.this, new Route<>(ProductViewRoute.class, params, placeholder));
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                updateTimesButton(timesButton, times--);
                onBackPressed();
            }
        });

        actButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Naviganto.get().routeTo(DetailActivity.this, new Route<>(HomeRoute.class, new Bundle()));
            }
        });

        prevActButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Naviganto.get().backTo(DetailActivity.this, new Route<>(HomeRoute.class, new Bundle()));
            }
        });

        Naviganto.get().routeTo(this, new Route<>(ProfileRoute.class, new Bundle(), placeholder));
    }

    @Override public void onBackPressed() {
        if (!Naviganto.get().back(this))
            super.onBackPressed();
    }

    private void updateTimesButton(Button timesButton, int times) {
        timesButton.setText("go back " + times + " times");
    }
}
