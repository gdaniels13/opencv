/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class ColorConverterProcessor implements Processor {
    int flag;

    public ColorConverterProcessor(int flag) {
        this.flag = flag;
    }
    
    
    
    @Override
    public Mat process(Mat input) {
        Imgproc.cvtColor(input,input,flag);
        return input;
    }
    
}
