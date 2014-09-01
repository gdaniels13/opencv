package balldetection;

import balldetection.processors.Processor;
import java.awt.Graphics;
import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class ImageProcessor {

    Mat inputImage;
    private ArrayList<Processor> processors;
    private Mat outputImage;
    public ImageProcessor(ArrayList<Processor> processors) {
        inputImage = new Mat();
        this.processors = processors;
    }
    

    public void setProcessors(ArrayList<Processor> processors){
        this.processors = processors;
        
    }
    
    public void process(){
        outputImage = inputImage.clone();
        for (Processor processor : processors) {
            outputImage = processor.process(outputImage);
        }
    }
}
