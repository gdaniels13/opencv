package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ErosionProcessor implements Processor{
//    static SingleSlider ss = new SingleSlider("erosion size", 1, 50);
//    static SingleSlider ss2 = new SingleSlider("Iterations", 1, 50);
    
    @Override
    public Mat process(Mat input) {
        
        
//        Imgproc.erode(input, input, Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(ss.getValue(), ss.getValue())),new Point(-1,-1),ss2.getValue() );
        Imgproc.erode(input, input, Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(15, 15)) );
        
        return input;
        
        
    }
    
}
