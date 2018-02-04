package pfa.src;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * First shot of a basic Camera for processing each frame
 *
 */

public class Camera implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "Camera";
    private CameraBridgeViewBase cvCamera;
    private BaseLoaderCallback loaderCallback;
    private Context appContext;

    private static Mat useless;
    private Mat rgb_8U_frame;
    private Mat rgb_32F_frame;

    private Mat colorTransform;


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
        cvCamera.disableView();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        rgb_8U_frame  = new Mat(height, width, CvType.CV_8UC4);
        rgb_32F_frame = new Mat(height, width, CvType.CV_32FC4);
        useless       = new Mat();
        // transform RGBA matrix
        colorTransform = Mat.eye(4, 4, CvType.CV_32FC1);

        // Test channel color order : OK
        //colorTransform.put(1, 1, 0.);
        //colorTransform.put(2, 2, 0.);
        //colorTransform.put(3, 3, 0.);
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        rgb_8U_frame = inputFrame.rgba();
        rgb_8U_frame.convertTo(rgb_32F_frame, CvType.CV_32FC4);

        rgb_32F_frame = process(rgb_32F_frame);

        rgb_32F_frame.convertTo(rgb_8U_frame, CvType.CV_8UC4);
        return rgb_8U_frame;
    }

    private Mat process(Mat float32_frame) {
        Mat colors = float32_frame.reshape(1, float32_frame.width() * float32_frame.height());

        Core.gemm(colors, colorTransform, 1, useless, 0, colors);

        float32_frame = colors.reshape(4, float32_frame.height());
        return float32_frame;
    }

    @Override
    public void onCameraViewStopped() {
        rgb_8U_frame.release();
        rgb_32F_frame.release();
        useless.release();

        colorTransform.release();
    }

}
