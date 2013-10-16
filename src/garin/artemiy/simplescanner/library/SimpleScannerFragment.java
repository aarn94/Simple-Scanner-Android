package garin.artemiy.simplescanner.library;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import garin.artemiy.simplescanner.library.views.SimpleCameraView;

/**
 * Author: Artemiy Garin
 * Date: 16.10.13
 */
public class SimpleScannerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new SimpleCameraView(inflater.getContext());
    }

}
