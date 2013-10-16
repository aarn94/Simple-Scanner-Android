package garin.artemiy.simplescanner.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import garin.artemiy.simplescanner.R;

public class MainActivity extends FragmentActivity {

    private static final long DELAY = 5000;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        setContentView(R.layout.main_layout);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                configureFullScreen(activity, true);
            }
        }, DELAY);
    }

    private static void configureFullScreen(Activity activity, boolean isEnabled) {
        if (isEnabled) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
        } else {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
        }
    }

}
