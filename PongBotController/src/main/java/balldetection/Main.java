package balldetection;

import balldetection.processors.AutoControlProcessor;
import balldetection.processors.CannyProcessor;
import balldetection.processors.ColorConverterProcessor;
import balldetection.processors.ContourProcessor;
import balldetection.processors.GausianSmoothingProcessor;
import balldetection.processors.HoughCirclesProcessor;
import balldetection.processors.InRangeProcessor;
import balldetection.processors.Processor;
import balldetection.processors.VideoProvidorProcessor;
import java.util.ArrayList;
import jssc.SerialPortException;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Main {

    static {
        System.loadLibrary("opencv_java249");
    }

    public static void main(String[] args) throws SerialPortException, InterruptedException {
        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(new VideoProvidorProcessor(0));
        processors.add(new MatrixFrame("Before alteration"));
        processors.add(new ColorConverterProcessor(Imgproc.COLOR_BGR2HSV));

        processors.add(new InRangeProcessor(new Scalar(9, 70, 80, 0), new Scalar(19, 250, 250, 0)));
//        processors.add(new MatrixFrame("after ranging"));

        processors.add(new GausianSmoothingProcessor());
//        processors.add(new CannyProcessor(false));
//        processors.add(new MatrixFrame("after Smoothing"));
//        processors.add(new HoughCirclesProcessor());
//        processors.add(new ContourProcessor());
        processors.add(new AutoControlProcessor());

        processors.add(new MatrixFrame("Circles"));
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
