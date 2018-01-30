package pfa.src;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * First shot of a basic Camera for process each frame
 *
 *
 */

public class Camera implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "Camera";
    private CameraBridgeViewBase cvCamera;
    private BaseLoaderCallback loaderCallback;
    private Context appContext;

    private Mat mRgba;


    Camera(Context context, CameraBridgeViewBase c) {

        appContext = context;

        loaderCallback = new BaseLoaderCallback(appContext) {
            @Override
            public void onManagerConnected(int status) {
                switch (status){
                    case LoaderCallbackInterface.SUCCESS:
                        open();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };

        cvCamera = c;
        cvCamera.setVisibility(SurfaceView.VISIBLE);
        cvCamera.setCvCameraViewListener(this);
    }

    public void load() {
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV loaded successfully");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else{
            Log.d(TAG, "OpenCV loader not loaded ");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, appContext, loaderCallback);
        }
    }

    public void open() {
        cvCamera.enableFpsMeter();
        cvCamera.enableView();
    }

    public void close() {
        if (cvCamera != null) {
            cvCamera.disableView();
        }
    }



    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        Imgproc.cvtColor(mRgba, mRgba, Imgproc.COLOR_BGR2HSV);
        return mRgba;
    }

}
