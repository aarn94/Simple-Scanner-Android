package garin.artemiy.simplescanner.library.fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.commonsware.cwac.camera.CameraView;
import net.sourceforge.zbar.*;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleScannerFragment extends Fragment {

    private static final String Z_BAR_LIBRARY = "iconv";
    private static final String GREY_COLOR_SPACE = "Y800";

    static {
        System.loadLibrary(Z_BAR_LIBRARY);
    }

    private ImageScanner scanner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new CameraView(inflater.getContext());
    }

    private class CustomPreviewCallback implements Camera.PreviewCallback {

        public void onPreviewFrame(byte[] data, Camera incomingCamera) {
            Camera.Parameters cameraParameters = incomingCamera.getParameters();
            Camera.Size previewSize = cameraParameters.getPreviewSize();

            Image barcode = new Image(previewSize.width, previewSize.height, GREY_COLOR_SPACE);
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {

                SymbolSet scannerResults = scanner.getResults();
                for (Symbol symbol : scannerResults) {
                    Toast.makeText(getActivity(), symbol.getData(), Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
