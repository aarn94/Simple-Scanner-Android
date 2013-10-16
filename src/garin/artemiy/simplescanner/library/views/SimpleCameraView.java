package garin.artemiy.simplescanner.library.views;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleCameraView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Camera.AutoFocusCallback autoFocusCallback;
    private Camera.PreviewCallback previewCallback;

    @SuppressWarnings("deprecated")
    public SimpleCameraView(Context context, Camera camera,
                            Camera.AutoFocusCallback autoFocusCallback,
                            Camera.PreviewCallback previewCallback) {
        super(context);
        this.camera = camera;
        this.autoFocusCallback = autoFocusCallback;
        this.previewCallback = previewCallback;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("empty")
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(previewCallback);
            camera.startPreview();
            camera.autoFocus(autoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
