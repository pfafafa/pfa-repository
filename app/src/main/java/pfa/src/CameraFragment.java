package pfa.src;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;

/**
 * A {@link Fragment} subclass.
 * Fragment to setup the camera with the image processing
 * One button to launch the image processing
 *
 * */
public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";

    private int mFrameProc;

    private Camera mCamera;

    private double valueSeekBar = 1;

    private SeekBar seekBar;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        mCamera = new Camera(getContext(), (CameraBridgeViewBase) view.findViewById(R.id.camera_view));

        final Button button = (Button) view.findViewById(R.id.button);

        // Button Simulation
        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrameImageProcessing(mFrameProc);
                button.setVisibility(View.GONE);
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(50);
            }
        });

        // perform seek bar change listener event used for getting the progress value
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (b) {
                    setValueSeekBar(progress);
                    setFrameImageProcessing(mFrameProc);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(),
                            "Seek bar progress is :" + getValueSeekBar(),
                            Toast.LENGTH_SHORT).show();
                }
        });
        return view;
    }

    /**
     * Set the class' variable mFrameProc with frameProc
     *
     * @param frameProc variable given during the FragmentInteraction
     */
    public void setFragmentInteraction(int frameProc){
        mFrameProc = frameProc;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera.load();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.close();
        // Daltonism.release();  // while quiting the application
    }


    /**
     * Launch the Camera's image processing
     *
     * @param typeImageProcessing Type of the image processing wanted
     */
    private void setFrameImageProcessing(int typeImageProcessing){

        switch (typeImageProcessing){
            case FrameProcFactory.TRITANOPIA:
                mCamera.setFrameProc(FrameProcFactory.tritanopia(valueSeekBar));
                break;

            case FrameProcFactory.DEUTERANOPIA:
                mCamera.setFrameProc(FrameProcFactory.deuteranopia(valueSeekBar));
                break;

            case FrameProcFactory.PROTANOPIA:
                mCamera.setFrameProc(FrameProcFactory.protanopia(valueSeekBar));
                break;

            case FrameProcFactory.CORRECT_TRITANOPIA:
                mCamera.setFrameProc(FrameProcFactory.correctTritanopia(valueSeekBar));
                break;

            case FrameProcFactory.CORRECT_DEUTERANOPIA:
                mCamera.setFrameProc(FrameProcFactory.correctDeuteranopia(valueSeekBar));
                break;

            case FrameProcFactory.CORRECT_PROTANOPIA:
                mCamera.setFrameProc(FrameProcFactory.correctProtanopia(valueSeekBar));
                break;

            default:
                mCamera.setFrameProc(FrameProcFactory.noProcess());
        }

    }

    /**
     * Get the class' variable valueSeekBar
     *
     * @return valueSeekBar
     */
    protected double getValueSeekBar(){
        return valueSeekBar;
    }

    /**
     * Set the class' variable valueSeekBar with frameProc
     *
     * @param progress Progress of the SeekBar
     */
    protected void setValueSeekBar(double progress){
        valueSeekBar = progress /50;
    }


}
