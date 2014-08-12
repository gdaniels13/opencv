/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balldetection.processors;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class MatrixProvidingProcessor implements Processor {

    String imagePath;
    Integer flag;

    public MatrixProvidingProcessor(String imagePath, Integer flag) {
        this.imagePath = imagePath;
        this.flag = flag;
    }

    @Override
    public Mat process(Mat input) {
        if (flag == null) {

            return Highgui.imread(imagePath, Highgui.CV_LOAD_IMAGE_COLOR);
        }
        return Highgui.imread(imagePath, flag);

    }

}
