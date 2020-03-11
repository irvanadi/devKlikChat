package com.xeylyne.klikchat.Testing.utils;

import android.content.res.Resources;

/**
 * Created by teyepeee on 05-Jun-17.
 */

public class DensityUtils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
