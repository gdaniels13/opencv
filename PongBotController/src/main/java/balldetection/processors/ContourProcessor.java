/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

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
        List<float[]> copy = new ArrayList<>();
        copy.addAll(radii);
        for (int max : findTopN(copy,2)) {
            
            Point maxPoint = centers.get(max);
            Core.ellipse(input, maxPoint, new Size(radii.get(max)[0],radii.get(max)[0]), 0, 0, 360,new Scalar(255, 0, 255), 4, 8, 0);
        }        
        
        return input;
    }

    private int[] findTopN(List<float[]> list, int count){
        int[] toReturn = new int[count];
        for (int i = 0; i < count; i++) {
            toReturn[i] = findMax(list);
            list.remove(toReturn[i]);
        }
        return toReturn;
        
    }
    private int findMax(List<float[]> list){
        float max = list.get(0)[0];
        int maxpos=0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i)[0]>max){
                max=list.get(i)[0];
                maxpos=i;
            }
        }
        
        return maxpos;
    }
    public List<Point> getTopN(int n){
      List<float[]> copy = new ArrayList<>();
        copy.addAll(radii);
        List<Point> toReturn = new ArrayList<>();
        for (int max : findTopN(copy,2)) {
            
            toReturn.add(centers.get(max));
        }
        
        return  toReturn;
    }
}
