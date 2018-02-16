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
import android.widget.RadioButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.Button;

import android.widget.SeekBar;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private static final String TAG = "OpenCVCamera";
    private Camera camera;

    private double valueSsbtri = 0;
    private double valueSsbpro = 0;
    private double valueSsbdeu = 0;

    private ToggleButton tb1;
    private ToggleButton tb2;
    private ToggleButton tb3;

    private SeekBar ssbtri;
    private SeekBar ssbdeu;
    private SeekBar ssbpro;

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
        tb1 = findViewById(R.id.tritanopia);
        ssbtri = findViewById(R.id.seekBarTritanopia);
        ssbtri.setVisibility(View.GONE);

        tb2 = findViewById(R.id.protanopia);
        ssbpro= findViewById(R.id.seekBarProtanopia);
        ssbpro.setVisibility(View.GONE);

        tb3 = findViewById(R.id.deuteranopia);
        ssbdeu= findViewById(R.id.seekBarDeuteranopia);
        ssbdeu.setVisibility(View.GONE);

        tb1.setOnCheckedChangeListener(this);

        tb2.setOnCheckedChangeListener(this);

        tb3.setOnCheckedChangeListener(this);

        // perform seek bar change listener event used for getting the progress value
        ssbtri.setOnSeekBarChangeListener(this);

        ssbpro.setOnSeekBarChangeListener(this);

        ssbdeu.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if (compoundButton.getId() == R.id.tritanopia) {
            if (isChecked) {
                tb2.setChecked(false);
                tb3.setChecked(false);
                //camera.setFrameProc(FrameProcFactory.tritanopia(getValueSsb(1)));
                camera.setFrameProc(FrameProcFactory.tritanopia(1));
                ssbtri.setVisibility(View.VISIBLE);
            } else {
                camera.setFrameProc(FrameProcFactory.noProcess());
                ssbtri.setVisibility(View.GONE);
            }
        }
        else if (compoundButton.getId() == R.id.protanopia) {
            if (isChecked) {
                tb1.setChecked(false);
                tb3.setChecked(false);
                //camera.setFrameProc(FrameProcFactory.protanopia( getValueSsb(2)));
                camera.setFrameProc(FrameProcFactory.protanopia(1));
                ssbpro.setVisibility(View.VISIBLE);
            } else {
                camera.setFrameProc(FrameProcFactory.noProcess());
                ssbpro.setVisibility(View.GONE);
            }
        }
        else if (compoundButton.getId() == R.id.deuteranopia) {
            if (isChecked) {
                tb2.setChecked(false);
                tb1.setChecked(false);
                //camera.setFrameProc(FrameProcFactory.deuteranopia(getValueSsb(3)));
                camera.setFrameProc(FrameProcFactory.deuteranopia(1));
                ssbdeu.setVisibility(View.VISIBLE);
            } else {
                camera.setFrameProc(FrameProcFactory.noProcess());
                ssbdeu.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (seekBar.getId() == R.id.tritanopia) {
            setValueSsb(1, progress);
        }

        if (seekBar.getId() == R.id.protanopia) {
            setValueSsb(2, progress);
        }

        if (seekBar.getId() == R.id.deuteranopia) {
            setValueSsb(3, progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (seekBar.getId() == R.id.tritanopia) {
            Toast.makeText(MainActivity.this,
                    "Seek bar progress is :" + getValueSsb(1),
                    Toast.LENGTH_SHORT).show();
        }

        if (seekBar.getId() == R.id.protanopia) {
            Toast.makeText(MainActivity.this,
                    "Seek bar progress is :" + getValueSsb(2),
                    Toast.LENGTH_SHORT).show();
        }

        if (seekBar.getId() == R.id.deuteranopia) {
            Toast.makeText(MainActivity.this,
                    "Seek bar progress is :" + getValueSsb(3),
                    Toast.LENGTH_SHORT).show();
        }

    }



    //valueSsb...
    protected double getValueSsb(int i){

        //1 = Tri
        //2 = Pro
        //3 = Deu
        if (i == 1)
            return valueSsbtri/10;
        if (i == 2)
            return valueSsbpro/10;
        if (i == 3)
            return valueSsbdeu/10;
        return 1;
    }

    protected void setValueSsb(int i, int prog){
        if (i == 1)
            this.valueSsbtri = prog;
        if (i == 2)
            this.valueSsbpro = prog;
        if (i == 3)
            this.valueSsbdeu = prog;
    }


    //
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
