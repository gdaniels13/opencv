package balldetection;

import balldetection.processors.ColorConverterProcessor;
import balldetection.processors.HoughCirclesProcessor;
import balldetection.processors.InRangeProcessor;
import balldetection.processors.MatrixProvidingProcessor;
import balldetection.processors.Processor;
import balldetection.processors.VideoProvidorProcessor;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.loadLibrary("opencv_java249");
        ArrayList<Processor> processors = new ArrayList<>();
//        processors.add(new MatrixProvidingProcessor("/home/gregor/git/opencv/balldetection/src/resources/OrangeBall1.jpg", null));
//        processors.add(new MatrixProvidingProcessor("/home/gregor/git/opencv/balldetection/src/resources/ball3.jpg", null));
        
//        processors.add(new VideoProvidorProcessor(0));
        processors.add(new VideoProvidorProcessor("/home/gregor/git/opencv/balldetection/src/resources/ballmovie.avi"));
        processors.add(new MatrixFrame("Before alteration"));
        processors.add(new ColorConverterProcessor(Imgproc.COLOR_BGR2HSV));
        processors.add(new MatrixFrame("after HSV"));
//        processors.add(new ColorConverterProcessor(Imgproc.COLOR_BGR2GRAY));
        processors.add(new InRangeProcessor(new Scalar(0, 4 * 2.55, 0), new Scalar(25 * 2.55, 44 * 2.55, 255)));
        processors.add(new MatrixFrame("after rangeing"));

//        processors.add(new HoughCirclesProcessor());
//        processors.add(new TuningInRangeProcessor(new Scalar(0, 0, 0), new Scalar(250, 250, 250)));
//        processors.add(new MatrixFrame("After alteration"));
        ImageProcessor proce = new ImageProcessor(processors);
        while (true) {
            proce.process();
        }

    }

}
