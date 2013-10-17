<h3>Version - 1.0</h3>

<h2>Install</h2>
You may import src from project or <a href="https://github.com/kvirair/Simple-Scanner-Android/releases">download jar</a> (recommended)

<h2>Quick start</h2>

— Create your activity with listener (don't forget add this activity to manifest)

```java
public class YourActivity extends FragmentActivity implements ScannerListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_layout);

        SimpleScannerFragment simpleScannerFragment = (SimpleScannerFragment)
            getSupportFragmentManager().findFragmentById(R.id.scannerFragment);
        simpleScannerFragment.setScannerListener(this);

    }

  @Override
    public void onDataReceive(String data) {
    // your code
  }

}
```

— And don't forget add fragment to your_layout, for example:

```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="fill_parent"
                android:layout_height="fill_parent"
                android:gravity="center">

    <fragment
        android:id="@+id/scannerFragment"
        class="garin.artemiy.simplescanner.library.fragments.SimpleScannerFragment"
        android:layout_height="fill_parent"
        android:layout_width="fill_parent"/>

</RelativeLayout>
```

**That's all!** enjoy.

<h2>Examples for scanning</h2>

![QR-Code](http://img208.imageshack.us/img208/4696/ors.gif)

