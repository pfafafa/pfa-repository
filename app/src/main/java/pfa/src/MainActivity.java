package pfa.src;

import android.Manifest;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.opencv.android.CameraBridgeViewBase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OpenCVCamera";
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        camera = new Camera(this, (CameraBridgeViewBase) findViewById(R.id.camera_view));

        // Button
        final Button button1 = findViewById(R.id.tritanopia);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // set FrameProc when the camera is ready, (not ready yet after new and load)
                camera.setFrameProc(FrameProcFactory.tritanopia());
            }
        });

        final Button button2 = findViewById(R.id.protanopia);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                camera.setFrameProc(FrameProcFactory.protanopia());
            }
        });

        final Button button3 = findViewById(R.id.deuteranopia);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                camera.setFrameProc(FrameProcFactory.deuteranopia());
            }
        });

        final Button button4 = findViewById(R.id.noProcess);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                camera.setFrameProc(FrameProcFactory.noProcess());
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        camera.load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.close();
        // Daltonism.release();  // while quiting the application
    }
}
