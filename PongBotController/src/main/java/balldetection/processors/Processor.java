package balldetection.processors;

import org.opencv.core.Mat;

public interface Processor {

    Mat process(Mat input);

}
