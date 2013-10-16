package garin.artemiy.simplescanner.library.fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import garin.artemiy.simplescanner.library.views.SimpleCameraView;
import net.sourceforge.zbar.*;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleScannerFragment extends Fragment {

    private static final String Z_BAR_LIBRARY = "iconv";
    private static final String GREY_COLOR_SPACE = "Y800";
    private static final long REFRESH_TIME = 2000;

    static {
        System.loadLibrary(Z_BAR_LIBRARY);
    }

    private boolean isPreviewing = true;

    private ImageScanner scanner;
    private Camera camera;
    private Handler autoFocusHandler;
    private Camera.PreviewCallback previewCallback = new CustomPreviewCallback();

    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (autoFocusHandler != null && runAutoFocus != null)
                autoFocusHandler.postDelayed(runAutoFocus, REFRESH_TIME);
        }
    };

    private Runnable runAutoFocus = new Runnable() {
        public void run() {
            if (isPreviewing && camera != null)
                camera.autoFocus(autoFocusCallback);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        autoFocusHandler = new Handler();

        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        camera = getCameraInstance();
        camera.setDisplayOrientation(90);
        if (camera != null) {
            camera.setPreviewCallback(previewCallback);
            camera.startPreview();
            camera.autoFocus(autoFocusCallback);
            isPreviewing = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();
        if (camera != null) {
            isPreviewing = false;
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new SimpleCameraView(inflater.getContext(), camera, autoFocusCallback, previewCallback);
    }

    private class CustomPreviewCallback implements Camera.PreviewCallback {

        public void onPreviewFrame(byte[] data, Camera incomingCamera) {
            Camera.Parameters cameraParameters = incomingCamera.getParameters();
            Camera.Size previewSize = cameraParameters.getPreviewSize();

            Image barcode = new Image(previewSize.width, previewSize.height, GREY_COLOR_SPACE);
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                isPreviewing = false;
                camera.setPreviewCallback(null);
                camera.stopPreview();

                SymbolSet scannerResults = scanner.getResults();
                for (Symbol symbol : scannerResults) {
                    Toast.makeText(getActivity(), symbol.getData(), Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
