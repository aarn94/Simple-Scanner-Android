package garin.artemiy.simplescanner.library.views;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.*;
import garin.artemiy.simplescanner.library.utils.SimpleCameraUtils;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleCameraView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Camera.AutoFocusCallback autoFocusCallback;
    private Camera.PreviewCallback previewCallback;
    private Context context;

    private boolean isPreviewing;

    @SuppressWarnings("deprecated")
    public SimpleCameraView(Context context,
                            Camera.AutoFocusCallback autoFocusCallback,
                            Camera.PreviewCallback previewCallback) {
        super(context);
        this.autoFocusCallback = autoFocusCallback;
        this.previewCallback = previewCallback;
        this.context = context;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        getCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        startCamera();
    }

    public Camera getCamera() {
        try {
            if (camera == null) {
                camera = Camera.open();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }

    public void configureCamera(Configuration configuration) {
        camera.setDisplayOrientation(SimpleCameraUtils.getOrientationDegree(configuration));

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        ViewGroup.LayoutParams cameraHolderParams = getLayoutParams();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            cameraHolderParams.height = display.getHeight();
            cameraHolderParams.width = (int) (display.getHeight() / aspect);
        } else {
            cameraHolderParams.width = display.getWidth();
            cameraHolderParams.height = (int) (display.getWidth() / aspect);
        }

        setLayoutParams(cameraHolderParams);
    }

    public boolean stopCamera() {
        camera.stopPreview();
        camera.setPreviewCallback(null);
        camera.release();
        isPreviewing = false;

        return isPreviewing;
    }

    public boolean startCamera() {
        try {
            if (surfaceHolder.getSurface() == null) {
                return isPreviewing;
            }

            camera.reconnect();
            camera.stopPreview();
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(previewCallback);
            camera.startPreview();
            camera.autoFocus(autoFocusCallback);

            isPreviewing = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPreviewing;
    }

}
