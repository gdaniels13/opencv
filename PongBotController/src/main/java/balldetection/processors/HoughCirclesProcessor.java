/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balldetection.processors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.CV_HOUGH_GRADIENT;
import static org.opencv.imgproc.Imgproc.HoughCircles;
import static org.opencv.imgproc.Imgproc.HoughCircles;

public class HoughCirclesProcessor implements Processor {

    Mat circles = new Mat();
    SingleSlider ss;
    private final SingleSlider ss2;

    public HoughCirclesProcessor() {
        ss = new SingleSlider("LowerLimit canny", 1, 100);
        ss2 = new SingleSlider("upper limit", 1, 100);

    }

    @Override
    public Mat process(Mat input) {
        circles= new Mat();
//public static void HoughCircles(Mat image,
//                Mat circles,
//                int method,
//                double dp,
//                double minDist,
//                double param1,
//                double param2,
//                int minRadius,
//                int maxRadius)
//        Imgproc.HoughCircles(input, circles, CV_HOUGH_GRADIENT, 1, input.height()/15, 349, 54, 15, 75);
        
          Imgproc.HoughCircles(input, circles, CV_HOUGH_GRADIENT, 1, input.height()/ 8, 100, 40, 0, 0);
//          Imgproc.HoughCircles(input, circles, CV_HOUGH_GRADIENT, 1, input.height()/ 8, 200, 100, 15, 75);
        int rows = circles.rows();

        int elemSize = (int) circles.elemSize(); // Returns 12 (3 * 4bytes in a float)  
        float[] data = new float[rows * elemSize / 4];
        System.out.println(""+ data.length+ " " + rows);
        
        if (data.length > 0) {
            circles.get(0, 0, data); // Points to the first element and reads the whole thing  

            for (int i = 0; i < data.length; i = i + 3) {
                Point center = new Point(data[i], data[i + 1]);
                //Core.ellipse( this, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
                Core.ellipse(input, center, new Size((double) data[i + 2], (double) data[i + 2]), 0, 0, 360, new Scalar(255, 0, 255), 4, 8, 0);
            }
        }
        return input;
    }

}
