package org.firezenk.conceptrouter.sample.product;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.firezenk.conceptrouter.annotations.RoutableView;
import org.firezenk.conceptrouter.sample.R;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
@RoutableView({Double.class})
class ProductView extends FrameLayout {

    private static Double aDouble;

    public static ProductView newInstance(Context context, Double aDouble) {
        final ProductView tmp = new ProductView(context);
        ProductView.aDouble = aDouble; // cause didactic purposes, better pass to a presenter
        return tmp;
    }

    public ProductView(Context context) {
        super(context);
        inflate(getContext(), R.layout.feature_product, this);
        ((TextView) findViewById(R.id.productId)).setText("Product " + aDouble);
    }
}
