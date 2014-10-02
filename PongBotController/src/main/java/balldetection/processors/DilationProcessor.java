/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author gregor
 */
public class DilationProcessor implements Processor{

//    static SingleSlider ss = new SingleSlider("dilation size", 1, 50);
//    static SingleSlider ss2 = new SingleSlider("Iterations", 1, 50);
            
    
    @Override
    public Mat process(Mat input) {
        
        Imgproc.dilate(input, input, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(15.0, 15.0)), new Point(0.0, 0.0),2);
        
        return input;
        
        
    }
    
}
