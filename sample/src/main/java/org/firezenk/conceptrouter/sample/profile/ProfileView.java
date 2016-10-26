package org.firezenk.conceptrouter.sample.profile;

import android.content.Context;
import android.widget.FrameLayout;

import org.firezenk.conceptrouter.sample.R;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 26/10/16.
 * Project: ConceptRouter
 */
class ProfileView extends FrameLayout {

    public ProfileView(Context context) {
        super(context);
        inflate(getContext(), R.layout.feature_profile, this);
    }
}
