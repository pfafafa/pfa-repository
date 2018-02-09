package pfa.src;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Daltonism contains matrix used by FrameProcFactory to
 * simulate daltonism sight
 */

class Daltonism {

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


    private Daltonism() {}

    static void release() {
        deuteranopia.release();
        protanopia.release();
        tritanopia.release();
    }
}
