package balldetection;

import balldetection.gameStrategies.DefaultAi;
import balldetection.processors.AutoControlProcessor;
import balldetection.processors.CircleFinderProcessor;
import balldetection.processors.ImageRotatingProcessor;
import balldetection.processors.PausingProcessor;
import balldetection.processors.PerspectiveTransformProcessor;
import balldetection.processors.Processor;
import balldetection.processors.VideoProvidorProcessor;
import balldetection.processors.WindowSelectingProcessor;
import balldetection.processors.warpAfineProcessor;
import java.util.ArrayList;
import jssc.SerialPortException;

public class Main {

    static {
//        System.loadLibrary("opencv_java249");
//        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
    }

    public static void main(String[] args) throws SerialPortException, InterruptedException {
        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(new VideoProvidorProcessor(0));
//        processors.add(new PausingProcessor());
//        processors.add(new ImageRotatingProcessor(ImageRotatingProcessor.rotate90()));
//        processors.add(new WindowSelectingProcessor());
        processors.add(new PerspectiveTransformProcessor());
        //processors.add(new PausingProcessor());
//        processors.add(new MatrixFrame("asdf"));
        processors.add(new AutoControlProcessor(true,new DefaultAi()));

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
