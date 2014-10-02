
package balldetection;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.Point;

/**
 *
 * @author gregor
 */
public class MatrixPanel extends JPanel {
    private static final long serialVersionUID = 1L;  
   private BufferedImage image;    
   private double[][] last4;
   private int count;
   // Create a constructor method  
   public MatrixPanel(){  
     super();  
     count =0;
     last4 = new double[4][];
     
       for (double[] ds : last4) {
           ds = null;
       }
     
     this.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent e){
             if(count>3){
                 return;
             }
             last4[count] = new double[2];
             last4[count][0] = (float)e.getX();
             last4[count][1] = (float)e.getY();
             count++;
         }
     });
   }  
   private BufferedImage getimage(){  
     return image;  
   }  
   public void setimage(BufferedImage newimage){  
     image=newimage;  
     return;  
   }  
   public void setimagewithMat(Mat newimage){  
     image=this.matToBufferedImage(newimage);  
     return;  
   }  

   public BufferedImage matToBufferedImage(Mat matrix) {  
     int cols = matrix.cols();  
     int rows = matrix.rows();  
     int elemSize = (int)matrix.elemSize();  
     byte[] data = new byte[cols * rows * elemSize];  
     int type;  
     matrix.get(0, 0, data);  
     switch (matrix.channels()) {  
       case 1:  
         type = BufferedImage.TYPE_BYTE_GRAY;  
         break;  
       case 3:  
         type = BufferedImage.TYPE_3BYTE_BGR;  
         // bgr to rgb  
         byte b;  
         for(int i=0; i<data.length; i=i+3) {  
           b = data[i];  
           data[i] = data[i+2];  
           data[i+2] = b;  
         }  
         break;  
       default:  
         return null;  
     }  
     BufferedImage image2 = new BufferedImage(cols, rows, type);  
     image2.getRaster().setDataElements(0, 0, cols, rows, data);  
     return image2;  
   }  
   @Override  
   protected void paintComponent(Graphics g){  
      super.paintComponent(g);  
      //BufferedImage temp=new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);  
      BufferedImage temp=getimage();  
      //Graphics2D g2 = (Graphics2D)g;
      if( temp != null)
        g.drawImage(temp,0,0,temp.getWidth(),temp.getHeight(), this);  
   }  

    public double[][] getLast4() {
        return last4;
    }
   
}

