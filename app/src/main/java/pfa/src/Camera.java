package pfa.src;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Basic Camera to process each frame
 *
 */

public class Camera implements CameraBridgeViewBase.CvCameraViewListener2 {

    static private final String TAG = "Camera";
    static private Context appContext;
    private CameraBridgeViewBase cvCamera;
    private BaseLoaderCallback loaderCallback;

    private Mat rgba8uFrame;
    private Mat rgba32fFrame;

    // Do nothing by default
    private FrameProc frameProc = FrameProcFactory.noProcess();


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

    void load() {
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV loaded successfully");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else{
            Log.d(TAG, "OpenCV loader not loaded ");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, appContext, loaderCallback);
        }
    }

    void open() {
        cvCamera.enableFpsMeter();
        cvCamera.enableView();
    }

    /**
     * @param proc can be given by FrameProcFactory
     */
    void setFrameProc(FrameProc proc) {
        proc.start();
        FrameProc old = frameProc;
        frameProc = proc;
        old.release();
    }

    void close() {
        cvCamera.disableView();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        rgba8uFrame  = new Mat(height, width, CvType.CV_8UC4);
        rgba32fFrame = new Mat(height, width, CvType.CV_32FC4);
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        rgba8uFrame = inputFrame.rgba();
        rgba8uFrame.convertTo(rgba32fFrame, CvType.CV_32FC4);

        rgba32fFrame = frameProc.process(rgba32fFrame);

        rgba32fFrame.convertTo(rgba8uFrame, CvType.CV_8UC4);
        return rgba8uFrame;
    }

    @Override
    public void onCameraViewStopped() {
        rgba8uFrame.release();
        rgba32fFrame.release();
    }

}
