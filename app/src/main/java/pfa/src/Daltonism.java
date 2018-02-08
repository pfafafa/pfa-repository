package pfa.src;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Daltonism contains matrix used by FrameProcFactory to
 * simulate daltonism sight
 */

class Daltonism {

    // Matrices for changing color space
    final static private Mat RGBA2LMS = new Mat(4, 4, CvType.CV_32FC1);
    final static private Mat LMS2RGBA = new Mat(4, 4, CvType.CV_32FC1);
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

    // Matrices for simulating daltonism
    final static Mat lmsDeuteranopia = new Mat(4, 4, CvType.CV_32FC1);
    final static Mat lmsProtanopia   = new Mat(4, 4, CvType.CV_32FC1);
    final static Mat lmsTritanopia   = new Mat(4, 4, CvType.CV_32FC1);
    static {
        lmsDeuteranopia.put(0, 0,
                1.,       0., 0.,      0.,
                0.494207, 0., 1.24827, 0.,
                0.,       0., 1.,      0.,
                0.,       0., 0.,      1.);

        lmsProtanopia.put(0, 0,
                0., 2.02344, -2.52581, 0.,
                0., 1.,       0.,      0.,
                0., 0.,       1.,      0.,
                0., 0,        0,       1.);

        lmsTritanopia.put(0, 0,
                 1.,       0.,       0., 0.,
                 0.,       1.,       0., 0.,
                -0.395913, 0.801109, 0., 0.,
                 0.,       0.,       0., 1.);
    }

    // Temporary matrices for calculus
    final static private Mat useless  = new Mat(4, 4, CvType.CV_32FC1);
    static private Mat tmpRes = new Mat(4, 4, CvType.CV_32FC1);



    private Daltonism() {}

    static Mat rgbaTransform(Mat lmsTransform) {

        Mat res = new Mat(4, 4, CvType.CV_32FC1);
        Core.gemm(lmsTransform, RGBA2LMS,  1, useless, 0, tmpRes);
        Core.gemm(LMS2RGBA, tmpRes, 1, useless, 0, res);
        return res;
    }


    static void release() {
        useless.release();
        tmpRes.release();

        RGBA2LMS.release();
        LMS2RGBA.release();

        lmsDeuteranopia.release();
        lmsProtanopia.release();
        lmsTritanopia.release();
    }
}
