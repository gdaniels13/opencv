/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balldetection.processors;

import balldetection.Circle;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author gregor
 */
public class CircleFinderProcessor implements Processor {

    private List<Processor> delegates;
    private ContourProcessor cp;

    public CircleFinderProcessor() {
        delegates = new ArrayList<>();
        delegates.add(new ColorConverterProcessor(Imgproc.COLOR_BGR2HSV));
        delegates.add(new InRangeProcessor(new Scalar(9, 70, 80, 0), new Scalar(19, 250, 250, 0)));
        delegates.add(new ErosionProcessor());
        delegates.add(new DilationProcessor());
        
        cp = new ContourProcessor();
        delegates.add(cp);
    }

    @Override
    public Mat process(Mat input) {
        for (Processor processor : delegates) {
            input = processor.process(input);
        }
        return input;
    }
    
    public List<Circle> getCircles(){
        return cp.getCircles();
    }
    

}
