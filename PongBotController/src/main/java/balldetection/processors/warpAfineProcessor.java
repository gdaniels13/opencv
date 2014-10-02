/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class warpAfineProcessor implements Processor {

    @Override
    public Mat process(Mat input) {
        Point center = new Point(input.cols()/2,input.cols()/2 );
        Mat rot_mat = Imgproc.getRotationMatrix2D(center, 90, 1);
        Imgproc.warpAffine(input, input, rot_mat, new Size(input.height(),input.width()));
        return input;
    }
    
}
