/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection;

import balldetection.processors.ColorConverterProcessor;
import balldetection.processors.InRangeProcessor;
import balldetection.processors.Processor;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author gregor
 */
public class Balldetection {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//             System.loadLibrary("opencv_java249");  
        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(new ColorConverterProcessor(Imgproc.COLOR_BGR2HSV));
        processors.add(new InRangeProcessor(new Scalar(0, 0, 0), new Scalar(250, 250, 250)));
        
        ImageProcessor proce = new ImageProcessor("test", "/home/gregor/git/Opencv/balldetection/src/resources/OrangeBall1.jpg", processors);
        while(true){
           proce.runAgain();
        }
    }
    
}
