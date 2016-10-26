package org.firezenk.conceptrouter.sample.product;

import android.content.Context;
import android.widget.FrameLayout;

import org.firezenk.conceptrouter.sample.R;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
class ProductView extends FrameLayout {

    public ProductView(Context context) {
        super(context);
        inflate(getContext(), R.layout.feature_product, this);
    }
}
