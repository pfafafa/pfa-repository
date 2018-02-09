package pfa.src;

import android.icu.util.Freezable;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Factory of FrameProc
 *
 * Used in Camera.setFrameProc()
 */

class FrameProcFactory {

    private FrameProcFactory() {}

    /**
     * @return an empty FrameProc that do nothing
     */
    static FrameProc noProcess() {

        return new FrameProc() {
            @Override
            public void start() {}

            @Override
            public Mat process(Mat rgbaFloatFrame) {
                return rgbaFloatFrame;
            }

            @Override
            public void release() {}
        };
    }

    /**
     * @param transformMat
     * @return a FrameProc that apply transformMat on each frame
     */
    static FrameProc makeFrameProc(final Mat transformMat) {

        return new FrameProc() {

            private Mat transform;
            private Mat useless;

            @Override
            public void start() {
                useless = new Mat();
                transform = transformMat;
            }

            @Override
            public Mat process(Mat rgbaFloatFrame) {
                int width = rgbaFloatFrame.width();
                int height = rgbaFloatFrame.height();

                Mat colors = rgbaFloatFrame.reshape(1, width * height);
                Core.gemm(colors, transform, 1, useless, 0, colors);
                rgbaFloatFrame = colors.reshape(4, height);

                return rgbaFloatFrame;
            }

            @Override
            public void release() {
                useless.release();
            }
        };
    }

    /**
     * @param channel in {0:red, 1:green, 2:blue}, else the value is clamped in this set
     * @return a FrameProc that select one channel of the frame
     */
    static FrameProc oneColor(int channel) {

        if (channel < 0) {
            channel = 0;
        } else if (2 < channel) {
            channel = 2;
        }

        Mat transform = Mat.zeros(4, 4, CvType.CV_32FC1);
        transform.put(channel, channel, 1.);
        return makeFrameProc(transform);
    }

    /**
     * @return a FrameProc simulating Deuteranopia
     */
    static FrameProc deuteranopia() {
        Mat deuteranope = Daltonism.deuteranopia;
        return makeFrameProc(deuteranope);
    }

    static FrameProc protanopia() {
        Mat protanope = Daltonism.protanopia;
        return makeFrameProc(protanope);
    }

    static FrameProc tritanopia() {
        Mat tritanope = Daltonism.tritanopia;
        return makeFrameProc(tritanope);
    }

    static FrameProc noDaltonism() {
        Mat nothing = Mat.eye(4, 4, CvType.CV_32FC1);
        return makeFrameProc(nothing);
    }

}
