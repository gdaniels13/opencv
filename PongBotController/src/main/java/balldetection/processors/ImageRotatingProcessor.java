package balldetection.processors;

import balldetection.MatrixFrame;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class ImageRotatingProcessor implements Processor {

    private static final List<Point> dst_pnt = new ArrayList<>();

    static {
        dst_pnt.add(new Point(0, 0));
        dst_pnt.add(new Point(480, 0));
        dst_pnt.add(new Point(0, 640));
        dst_pnt.add(new Point(480, 640));
    }

    
    public static Mat rotate90() {
        List<Point> src_pnt = new ArrayList<>();
        src_pnt.add(new Point(640, 0)); //top right
        src_pnt.add(new Point(640, 480));//bottom right
        src_pnt.add(new Point(0, 0));//top left
        src_pnt.add(new Point(0, 480));//bottom left
        return Imgproc.getPerspectiveTransform(Converters.vector_Point2f_to_Mat(src_pnt), Converters.vector_Point2f_to_Mat(dst_pnt));
    }

    private Mat perspectiveTranform = null;

    public ImageRotatingProcessor(Mat transformationMatrix) {
        this.perspectiveTranform = transformationMatrix;
    }

    @Override
    public Mat process(Mat input) {
        Mat outpMat = input.clone();
        Imgproc.warpPerspective(input, outpMat, perspectiveTranform, new Size(input.rows(), input.cols()));
        return outpMat;
    }
}
