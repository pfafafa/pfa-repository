package pfa.src;

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
     * You should passe a allocated matrix because it will be release in the release function
     *
     * @param transformMat 4x4 matrix of CvType.CV_32F1C
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
                transform.release();
            }
        };
    }

    /**
     * This method return basic one color filter, it's a good test with simple filter
     *
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
     * @return a FrameProc simulating blindness
     */
    static FrameProc deuteranopia(double alpha) {
        Mat deuteranope = Daltonism.shade(Daltonism.deuteranopia, alpha);
        return makeFrameProc(deuteranope);
    }

    static FrameProc protanopia(double alpha) {
        Mat protanope = Daltonism.shade(Daltonism.protanopia, alpha);
        return makeFrameProc(protanope);
    }

    static FrameProc tritanopia(double alpha) {
        Mat tritanope = Daltonism.shade(Daltonism.tritanopia, alpha);
        return makeFrameProc(tritanope);
    }

    
    /**
     * @return a FrameProc that "correct" blindness, add contrast
     */
    static FrameProc correctDeuteranopia(double alpha) {
        Mat correction = Daltonism.correctionMat(Daltonism.deuteranopia);
        Mat transform = Daltonism.shade(correction, alpha);
        correction.release();
        return makeFrameProc(transform);
    }

    static FrameProc correctProtanopia(double alpha) {
        Mat correction = Daltonism.correctionMat(Daltonism.protanopia);
        Mat transform = Daltonism.shade(correction, alpha);
        correction.release();
        return makeFrameProc(transform);
    }

    static FrameProc correctTritanopia(double alpha) {
        Mat correction = Daltonism.correctionMat(Daltonism.tritanopia);
        Mat transform = Daltonism.shade(correction, alpha);
        correction.release();
        return makeFrameProc(transform);
    }
}
