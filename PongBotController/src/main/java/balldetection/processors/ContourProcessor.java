/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import balldetection.Circle;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import static org.opencv.imgproc.Imgproc.*;


public class ContourProcessor implements Processor {

    List<Point> centers = new ArrayList<>();
    List<float[]> radii = new ArrayList<>();
        
    @Override
    public Mat process(Mat input) {
        centers.clear();
        radii.clear();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        
        findContours(input, contours, new Mat(), RETR_TREE, CHAIN_APPROX_SIMPLE);
        
        
        
        for (MatOfPoint mat : contours) {
            Point p = new Point();
            float[] r = new float[4] ;
            minEnclosingCircle(new MatOfPoint2f(mat.toArray()), p, r);
            centers.add(p);
            radii.add(r);
        }
        //System.out.println(centers.size());
        List<float[]> copy = new ArrayList<>();
        copy.addAll(radii);
//        for (int max : findTopN(copy,15)) {
        for(int i = 0; i<centers.size(); ++i){
//            if(max == -1){
//                continue;
//            }
            Point maxPoint = centers.get(i);
            double radius = radii.get(i)[0];

            Core.ellipse(input, maxPoint, new Size(radius,radius), 0, 0, 360,new Scalar(255, 0, 255), 4, 8, 0);
        }        
        
        return input;
    }
    
    public List<Circle> getCircles(){
        List<Circle> toReturn = new ArrayList<>();
        for(int i = 0;i<centers.size(); ++i){
            Point center = centers.get(i);
            double radius = radii.get(i)[0];
            if(radius<15){
                continue;
            }
            toReturn.add(new Circle(center.x+15, center.y+15, radius));
        }
        return toReturn;
    }

}
