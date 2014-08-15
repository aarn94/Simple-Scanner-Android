package garin.artemiy.simplescanner.example;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.Toast;
import garin.artemiy.simplescanner.R;
import garin.artemiy.simplescanner.library.fragments.SimpleScannerFragment;
import garin.artemiy.simplescanner.library.listeners.ScannerListener;

@SuppressWarnings("SameParameterValue")
public class MainActivity extends FragmentActivity implements ScannerListener {

    private static final long DELAY = 5000;
    private static final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        SimpleScannerFragment simpleScannerFragment =
                (SimpleScannerFragment) getSupportFragmentManager().findFragmentById(R.id.scannerFragment);
        simpleScannerFragment.setScannerListener(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                configureFullScreen(true);
            }
        }, DELAY);
    }

    private void configureFullScreen(boolean isEnabled) {
        if (isEnabled) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
        } else {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
        }
    }

    @Override
    public void onDataReceive(String data, int barcodeType) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

}
