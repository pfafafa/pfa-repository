package pfa.src;

import org.opencv.core.Mat;


/**
 * Interface for process frame
 *
 * Implementation will use methods from OpenCv for complexity reasons.
 * Do not make new object in process() method,
 * all objects had to be initialized in start() and destroyed in released()
 */


public interface FrameProc {

    public void start();

    public Mat process(Mat rgbaFloatFrame);

    public void release();

}
