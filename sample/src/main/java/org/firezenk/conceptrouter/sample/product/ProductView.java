package org.firezenk.conceptrouter.sample.product;

import android.content.Context;
import android.widget.FrameLayout;

import org.firezenk.conceptrouter.processor.annotations.RoutableView;
import org.firezenk.conceptrouter.sample.R;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
@RoutableView({})
class ProductView extends FrameLayout {

    public static ProductView newInstance(Context context) {
        return new ProductView(context);
    }

    public ProductView(Context context) {
        super(context);
        inflate(getContext(), R.layout.feature_product, this);
    }
}
