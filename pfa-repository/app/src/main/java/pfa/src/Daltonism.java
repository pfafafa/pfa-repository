package pfa.src;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Daltonism contains matrix used by FrameProcFactory to
 * simulate daltonism sight
 */

class Daltonism {

    // Useful matrix
    final static private Mat idendity = Mat.eye(4, 4, CvType.CV_32F);

    // Matrices for simulating daltonism
    final static Mat deuteranopia = new Mat(4, 4, CvType.CV_32FC1);
    final static Mat protanopia   = new Mat(4, 4, CvType.CV_32FC1);
    final static Mat tritanopia   = new Mat(4, 4, CvType.CV_32FC1);
    static {
        deuteranopia.put(0, 0,
                0.625, 0.375, 0,   0,
                0.7,   0.3,   0,   0,
                0,     0.3,   0.7, 0,
                0,     0,     0,   1);

        protanopia.put(0, 0,
                0.567, 0.433, 0,     0,
                0.558, 0.442, 0,     0,
                0,     0.242, 0.758, 0,
                0,     0,     0,     1);

        tritanopia.put(0, 0,
                0.95, 0.05,  0,     0,
                0,    0.433, 0.567, 0,
                0,    0.475, 0.525, 0,
                0,    0,     0,     1);
    }

    // Matrix used to correct daltonism
    final static private Mat correction = new Mat(4, 4, CvType.CV_32FC1);
    final static private Mat tmpMult = new Mat(4, 4, CvType.CV_32FC1);
    final static private Mat tmpSub  = new Mat(4, 4, CvType.CV_32FC1);
    static {
        correction.put(0, 0,
                    0,   0, 0, 0,
                    0.7, 1, 0, 0,
                    0.7, 0, 1, 0,
                    0,   0, 0, 0);
    }



    private Daltonism() {}

    /**
     * Return an allocated matrix
     *
     * @param target of type CvType.CV_32F1C
     * @param alpha in [0, 1]
     * @return (alpha . matrix) + ((1 - alpha) . Identity)
     */
    static Mat shade(Mat target, double alpha) {
        Mat res = new Mat(target.rows(), target.cols(), CvType.CV_32FC1);

        Core.addWeighted(target, alpha, idendity, (1. - alpha), 0, res);

        return res;
    }

    /**
     * Use the simulation matrix to create a correction matrix for a type of blindness
     *
     * @param blindnessMat a 4x4 matrix of CvType.CV_32F1C
     * @return an allocated matrix
     */
    static Mat correctionMat(Mat blindnessMat) {
        Mat res = new Mat(4, 4, CvType.CV_32FC1);

        Core.gemm(blindnessMat, correction, 1, idendity, 0, tmpMult); //matrix multiplication
        Core.subtract(correction, tmpMult, tmpSub); //Calculates the per-element difference between two arrays or array and a scalar.
        Core.add(tmpSub, idendity, res); //Computes the per-element sum of two arrays or an array and a scalar.

        return res;
    }

    static void release() {
        idendity.release();

        deuteranopia.release();
        protanopia.release();
        tritanopia.release();

        correction.release();
        tmpMult.release();
        tmpSub.release();
    }
}
