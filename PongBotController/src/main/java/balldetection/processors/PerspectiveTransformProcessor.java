/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balldetection.processors;

import balldetection.MatrixFrame;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class PerspectiveTransformProcessor implements Processor {

    private MatrixFrame mf;
    private Mat perspectiveTranform = null;

    public PerspectiveTransformProcessor() {
        mf = new MatrixFrame("Corner Selecting");
    }

    @Override
    public Mat process(Mat input) {
        mf.process(input);
        if (perspectiveTranform == null && mf.getLast4()[3] != null) {
//            if(mf.getLast4().size()<4){
            double[][] points = mf.getLast4();
            for (double[] ds : points) {
                if (ds == null) {
                    System.out.println("*************************");
                    for (double[] d : points) {
                        if (d == null) {
                            System.out.println("null");
                        } else {
                            System.out.println(d[0] + " " + d[1]);
                        }
                    }
                    return input;
                }

            }

            //Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
            List<Point> src_pnt = new ArrayList<>();
            src_pnt.add(new Point(points[0])); //top right
            src_pnt.add(new Point(points[1])); //bottom right
            src_pnt.add(new Point(points[2])); //top left
            src_pnt.add(new Point(points[3])); //bottom left
            Mat startM = Converters.vector_Point2f_to_Mat(src_pnt);

            List<Point> dst_pnt = new ArrayList<>();
            dst_pnt.add(new Point(0, 0));
            dst_pnt.add(new Point(480, 0));
            dst_pnt.add(new Point(0, 640));
            dst_pnt.add(new Point(480, 640));

            System.out.println(src_pnt);
//                dst_pnt.add(new Point(640, 0));
//                dst_pnt.add(new Point(0, 480));
//                dst_pnt.add(new Point(640, 480));
            Mat endM = Converters.vector_Point2f_to_Mat(dst_pnt);
            perspectiveTranform = Imgproc.getPerspectiveTransform(startM, endM);
        }

        if (perspectiveTranform == null) {
            System.out.println("*************************");
            for (double[] d : mf.getLast4()) {
                if (d == null) {
                    System.out.println("null");
                } else {
                    System.out.println(d[0] + " " + d[1]);
                }
            }
            return input;
        }

        Mat outpMat = input.clone();
        Imgproc.warpPerspective(input, outpMat, perspectiveTranform, new Size(input.rows(), input.cols()));

        return outpMat;
    }

}
