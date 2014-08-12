package balldetection;

import balldetection.processors.Processor;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class MatrixFrame extends JFrame implements Processor{
    private MatrixPanel matrixPanel;
    
    public MatrixFrame(String title){
        super(title);
        this.matrixPanel = new MatrixPanel();
        add(matrixPanel);
        this.setSize(640,480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void update(Mat mat){
        matrixPanel.setimagewithMat(mat);
        this.setSize(dimensionFromSize(mat.size()));
        this.repaint();
    }

    @Override
    public Mat process(Mat input) {
        this.update(input);
        return input;
    }

    private Dimension dimensionFromSize(Size size) {
        return new Dimension((int)size.width, (int)size.height);
    }
}
