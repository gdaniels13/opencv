/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


public class InRangeProcessor implements Processor {
    Scalar lower, upper;
    ScalarInput lowerGui,upperGui;
    public InRangeProcessor(Scalar lower, Scalar upper) {
        this.lower = lower;
        this.upper = upper;
        lowerGui = new ScalarInput();
        lowerGui.setTitle("Lower Bounds");
        upperGui = new ScalarInput();
        upperGui.setTitle("Upper Bounds");
        
        lowerGui.init();
        upperGui.init();
    }

    
    @Override
    public Mat process(Mat input) {
        lower= lowerGui.getScalar();
        upper = upperGui.getScalar();
        System.out.println(lower.toString());
        Core.inRange(input, lower, upper, input);
        
        
        return input;
    }
    
}
