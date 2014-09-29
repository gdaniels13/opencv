package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class GausianSmoothingProcessor implements Processor {

    @Override
    public Mat process(Mat input) {
        Imgproc.GaussianBlur(input, input, new Size(9,9),9);
        return input;
    }
    
}
