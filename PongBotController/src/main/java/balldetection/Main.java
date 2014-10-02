package balldetection;

import balldetection.processors.AutoControlProcessor;
import balldetection.processors.CircleFinderProcessor;
import balldetection.processors.PausingProcessor;
import balldetection.processors.PerspectiveTransformProcessor;
import balldetection.processors.Processor;
import balldetection.processors.VideoProvidorProcessor;
import balldetection.processors.warpAfineProcessor;
import java.util.ArrayList;
import jssc.SerialPortException;

public class Main {

    static {
        System.loadLibrary("opencv_java249");
    }

    public static void main(String[] args) throws SerialPortException, InterruptedException {
        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(new VideoProvidorProcessor(0));
        processors.add(new PerspectiveTransformProcessor());
        //processors.add(new PausingProcessor());
        processors.add(new AutoControlProcessor());
        
        ImageProcessor proce = new ImageProcessor(processors);
        
        while (true) {
            try {
                proce.process();
            } catch (Throwable t) {
                //just catch it and keep going
                t.printStackTrace();
            }
        }
    }

}
