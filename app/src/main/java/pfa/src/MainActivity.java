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
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.Button;

import android.widget.SeekBar;
import android.widget.Toast;

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
        final ToggleButton tb1 = findViewById(R.id.tritanopia);
        final SeekBar ssbtri = findViewById(R.id.seekBarTritanopia);
        ssbtri.setVisibility(View.GONE);

        final ToggleButton tb2 = findViewById(R.id.protanopia);
        final SeekBar ssbpro= findViewById(R.id.seekBarProtanopia);
        ssbpro.setVisibility(View.GONE);

        final ToggleButton tb3 = findViewById(R.id.deuteranopia);
        final SeekBar ssbdeu= findViewById(R.id.seekBarDeuteranopia);
        ssbdeu.setVisibility(View.GONE);

        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tb2.setChecked(false);
                    tb3.setChecked(false);
                    camera.setFrameProc(FrameProcFactory.tritanopia());
                    ssbtri.setVisibility(View.VISIBLE);
                } else {
                    camera.setFrameProc(FrameProcFactory.noProcess());
                    ssbtri.setVisibility(View.GONE);
                }
            }
        });

        tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tb1.setChecked(false);
                    tb3.setChecked(false);
                    camera.setFrameProc(FrameProcFactory.tritanopia());
                    ssbpro.setVisibility(View.VISIBLE);
                } else {
                    camera.setFrameProc(FrameProcFactory.noProcess());
                    ssbpro.setVisibility(View.GONE);
                }
            }
        });

        tb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tb2.setChecked(false);
                    tb1.setChecked(false);
                    camera.setFrameProc(FrameProcFactory.tritanopia());
                    ssbdeu.setVisibility(View.VISIBLE);
                } else {
                    camera.setFrameProc(FrameProcFactory.noProcess());
                    ssbdeu.setVisibility(View.GONE);
                }
            }
        });




        // perform seek bar change listener event used for getting the progress value
        ssbtri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
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
