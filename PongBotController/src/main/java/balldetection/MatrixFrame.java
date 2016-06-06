package balldetection;

import balldetection.processors.Processor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayDeque;
import javax.swing.JFrame;
import org.opencv.core.Mat;
import org.opencv.core.Point;
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
        super.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
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
        return new Dimension((int)size.width + 10, (int)size.height + 10);
    }
    
    public double[][] getLast4(){
        return matrixPanel.getLast4();
    }

    public MatrixFrame() throws HeadlessException {
    }
    
    
}
