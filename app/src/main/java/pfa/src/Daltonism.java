package pfa.src;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Daltonism is a Factory class for FrameProc
 *
 * Each public method return a FrameProc for Camera.setFrameProc()
 */

class Daltonism {

    static private Mat useless  = new Mat();
    static private Mat RGBA2LMS = new Mat(4, 4, CvType.CV_32FC1);
    static private Mat LMS2RGBA = new Mat(4, 4, CvType.CV_32FC1);
    static {
        RGBA2LMS.put(0, 0,
                17.8824,   43.5161,  4.11935, 0.,
                3.45565,   27.1554,  3.86714, 0.,
                0.0299566, 0.184309, 1.46709, 0.,
                0.,        0.,       0.,      1.);

        LMS2RGBA.put(0, 0,
                 8.09444479e-02, -1.30504409e-01,  1.16721066e-01, 0.,
                -1.02485335e-02,  5.40193266e-02, -1.13614708e-01, 0.,
                -3.65296938e-04, -4.12161469e-03,  6.93511405e-01, 0.,
                 0.,              0.,              0.,             1.);
    }

    static private Mat lmsDeuteranopia = new Mat(4, 4, CvType.CV_32FC1);
    static {
        lmsDeuteranopia.put(0, 0,
                1.,       0., 0.,      0.,
                0.494207, 0., 1.24827, 0.,
                0.,       0., 1.,      0.,
                0.,       0., 0.,      1.);
    }


    static private Mat rgbaTransform(Mat lmsTransform) {
        Mat tmp = new Mat();
        Core.gemm(lmsTransform, RGBA2LMS,  1, useless, 0, tmp);
        Mat res = new Mat();
        Core.gemm(LMS2RGBA, tmp, 1, useless, 0, res);
        return res;
    }


    static public FrameProc testGreen() {
        FrameProc res = new FrameProc() {

            private Mat colorTransform;
            private Mat useless;

            @Override
            public void start() {
                useless = new Mat();
                colorTransform = Mat.zeros(4, 4, CvType.CV_32FC1);
                colorTransform.put(1, 1, 1.);
            }

            @Override
            public Mat process(Mat rgbaFloatFrame) {
                int width = rgbaFloatFrame.width();
                int height = rgbaFloatFrame.height();

                Mat colors = rgbaFloatFrame.reshape(1, width * height);
                Core.gemm(colors, colorTransform, 1, useless, 0, colors);
                rgbaFloatFrame = colors.reshape(4, height);

                return rgbaFloatFrame;
            }

            @Override
            public void release() {
                colorTransform.release();
                useless.release();
            }
        };
        return res;
    }

    static public FrameProc deuteranope() {

        FrameProc res = new FrameProc() {

            private Mat d;
            private Mat u;

            @Override
            public void start() {
                d = rgbaTransform(lmsDeuteranopia);
                u = new Mat();
            }

            @Override
            public Mat process(Mat rgbaFloatFrame) {
                int width = rgbaFloatFrame.width();
                int height = rgbaFloatFrame.height();

                Mat colors = rgbaFloatFrame.reshape(1, width * height);
                Core.gemm(colors, d, 1, u, 0, colors);
                rgbaFloatFrame = colors.reshape(4, height);

                return rgbaFloatFrame;
            }

            @Override
            public void release() {
                d.release();
                u.release();
            }
        };
        return res;
    }
}
