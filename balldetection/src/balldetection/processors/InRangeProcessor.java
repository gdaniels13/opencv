package balldetection.processors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


public class InRangeProcessor implements Processor {
    Scalar lower, upper;
    public InRangeProcessor(Scalar lower, Scalar upper) {
        this.lower = lower;
        this.upper = upper;
    }

    
    @Override
    public Mat process(Mat input) {
        Core.inRange(input, lower, upper, input);
        
        
        return input;
    }
    
}

    
