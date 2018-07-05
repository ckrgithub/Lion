package com.ruzhan.lion.util;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by ruzhan123 on 2018/7/5.
 */
public class AnimUtils {

    private static Interpolator fastOutSlowIn;

    private AnimUtils() {}

    public static Interpolator getFastOutSlowInInterpolator(Context context) {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in);
        }
        return fastOutSlowIn;
    }
}
