/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;


public class CompositeProcessor implements Processor {
    private List<Processor> delegates;

    public CompositeProcessor() {
        delegates = new ArrayList<>();
    }
    
    public void addProcessor(Processor p){
        delegates.add(p);
    }
            
    @Override
    public Mat process(Mat input) {
        for (Processor processor : delegates) {
            input = processor.process(input);
        }
        return input;
    }
    
}
