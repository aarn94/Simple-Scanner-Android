package garin.artemiy.simplescanner.library.utils;

import android.content.res.Configuration;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleCameraUtils {

    private static final int DEGREES_0 = 0;
    private static final int DEGREES_90 = 90;

    public static int getOrientationDegree(Configuration configuration) {
        switch (configuration.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return DEGREES_0;
            case Configuration.ORIENTATION_PORTRAIT:
                return DEGREES_90;
            default:
                return DEGREES_0;
        }
    }

}
