package garin.artemiy.simplescanner.library.utils;

import android.hardware.Camera;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleCameraUtils {

    public static Camera.Size getBestPreviewSize(int displayOrientation,
                                                 int width,
                                                 int height,
                                                 Camera.Parameters parameters) {
        double targetRatio = (double) width / height;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = (double) height / width;
        }

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            double ratio = (double) size.width / size.height;

            if (Math.abs(ratio - targetRatio) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(ratio - targetRatio);
            }
        }

        return optimalSize;
    }

}
