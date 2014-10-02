/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import balldetection.ToggleButton;
import org.opencv.core.Mat;


public class PausingProcessor implements Processor {    
    ToggleButton tb;

    public PausingProcessor() {
        tb = new ToggleButton();
        
    }
    
    @Override
    public Mat process(Mat input) {
        
        while(!tb.getButtonState());
        
        return input;
    }
    
}
