/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;


public class VideoProvidorProcessor implements Processor {
    VideoCapture capture;
    String file;
    public VideoProvidorProcessor(int device) {
        this.capture=new VideoCapture(device);
    }

    public VideoProvidorProcessor(String file) {
        this.capture = new VideoCapture(file);
        this.file = file;
        if (!capture.isOpened()){
            throw new ExceptionInInitializerError("unable to open specified file");
        }
    }    

    
    @Override
    public Mat process(Mat input) {
        boolean result = capture.read(input);
        
        if(input == null || result ==false){
            capture = new VideoCapture(file);
            capture.read(input);
        }
        return input;
    }
    
}
