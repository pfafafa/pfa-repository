package pfa.src;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Daltonism is a Factory class for FrameProc
 *
 * Each public method return a FrameProc for Camera.setFrameProc()
 */

class Daltonism {

    static public FrameProc testRed() {

        FrameProc res = new FrameProc() {

            private Mat colorTransform = null;
            private Mat useless = null;

            @Override
            public void start() {
                useless = new Mat();
                colorTransform = Mat.zeros(4, 4, CvType.CV_32FC1);
                colorTransform.put(0, 0, 1.);
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

}
