package balldetection;

import javax.swing.JFrame;
import org.opencv.core.Mat;

public class MatrixFrame extends JFrame{
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
        this.repaint();
    }
}
