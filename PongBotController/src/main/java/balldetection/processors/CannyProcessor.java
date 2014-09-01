/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyProcessor implements Processor {

    SingleSlider upper;
    SingleSlider lower;
    boolean debug;

    public CannyProcessor(boolean debug) {
        this.debug = debug;
        if (debug) {

            upper = new SingleSlider("Upper threshold", 0, 1000);
            lower = new SingleSlider("Lower threshold", 0, 1000);
        }
    }

    @Override
    public Mat process(Mat input) {
        if (debug) {
            Imgproc.Canny(input, input, upper.getValue(), lower.getValue());
        } else {
            Imgproc.Canny(input, input, 274, 242);
        }
        return input;
    }

}
