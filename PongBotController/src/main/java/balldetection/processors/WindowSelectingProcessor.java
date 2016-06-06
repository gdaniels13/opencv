package balldetection.processors;

import balldetection.MatrixFrame;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class WindowSelectingProcessor implements Processor {

    private WindowSelectingPane wps = new WindowSelectingPane();
    private static final List<Point> dst_pnt = new ArrayList<>();
    private MatrixFrame mf = new MatrixFrame("Corner Selecting");

    static {
        dst_pnt.add(new Point(0, 0));
        dst_pnt.add(new Point(480, 0));
        dst_pnt.add(new Point(0, 640));
        dst_pnt.add(new Point(480, 640));
    }

    @Override
    public Mat process(Mat input) {
        Mat temp = input.clone();
        List<Point> points = wps.getPoints();
        for (Point point : points) {
            Core.circle(temp, point, 2, new Scalar(255, 0, 255), 2);
        }
        mf.process(temp);

        Mat a = Imgproc.getPerspectiveTransform(Converters.vector_Point2f_to_Mat(wps.getPoints()), Converters.vector_Point2f_to_Mat(dst_pnt));
        Mat output = input.clone();

        Imgproc.warpPerspective(input, output, a, input.size());
        return output;
    }

}
