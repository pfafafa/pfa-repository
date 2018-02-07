package pfa.src;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.opencv.android.CameraBridgeViewBase;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OpenCVCamera";
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        camera = new Camera(this, (CameraBridgeViewBase) findViewById(R.id.camera_view));
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // set FrameProc when the camera is ready, (not ready yet after new and load)
        camera.setFrameProc(Daltonism.testRed());
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.close();
    }
}
