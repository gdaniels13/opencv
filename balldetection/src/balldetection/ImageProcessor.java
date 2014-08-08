package balldetection;

import balldetection.processors.Processor;
import java.awt.Graphics;
import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class ImageProcessor {

    private MatrixFrame outputFrame,inputFrame;
    Mat inputImage;
    private ArrayList<Processor> processors;
    private Mat outputImage;
    public ImageProcessor(String title, String img, ArrayList<Processor> processors) {
        inputFrame = new MatrixFrame(title);
        outputFrame = new MatrixFrame(title);
        inputImage = new Mat();
        this.processors = processors;
        
        inputImage = getImage(img);
        
        process();
        outputFrame.update(outputImage);
        inputFrame.update(inputImage);
    }
    
    public void runAgain(){
        process();
        outputFrame.update(outputImage);
        inputFrame.update(inputImage);
    }

    public void setProcessors(ArrayList<Processor> processors){
        this.processors = processors;
        
    }
    
    
    private Mat getImage(String img) {
//        URL img_url = getClass().getResource(img);
//        String img_path = img_url.getPath();
//
//        if (img_path.startsWith("/")) {
//            img_path = img_path.substring(1);
//        }

        return Highgui.imread(img,Highgui.CV_LOAD_IMAGE_COLOR);
    }
    public void process(){
        outputImage = inputImage.clone();
        for (Processor processor : processors) {
            outputImage = processor.process(outputImage);
        }
    }
}
