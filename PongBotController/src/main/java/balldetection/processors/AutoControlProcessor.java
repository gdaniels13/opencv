/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import balldetection.Circle;
import balldetection.MatrixFrame;
import balldetection.Packet;
import static balldetection.Packet.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

public class AutoControlProcessor implements Processor {
    CircleFinderProcessor cfp;
    MatrixFrame mf;
    SerialPort serialPort;
    private volatile int goal;

    private Timer t;
    public AutoControlProcessor() throws SerialPortException, InterruptedException {
        cfp = new CircleFinderProcessor();
        mf = new MatrixFrame("circles");
        serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200,8,1,0);
        Thread.sleep(4000);
        serialPort.writeBytes(Packet.getBytes(PADDLE_BACKWARD, 255));
        
        t = new Timer();
        t.schedule(new TimerTask() {
            private int lastGoal = 50;
            @Override
            public void run() {
                try {
                    if(goal != lastGoal){
                        goal = goal/25;
                        goal *=25;
                        writeGoalToBot();
                        lastGoal = goal;
//                        System.out.println("goal is :" + goal);
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to send new goal");
                }
            }
        }, 0, 100);
        
    }
    
    
    
    @Override
    public Mat process(Mat input) {
        Mat temp = input.clone();
                //new Mat(input, new Rect(0, 0, input.width(), input.height()));
//        input.copyTo(temp);
        input = cfp.process(input);
        drawCircles(temp,cfp.getCircles());
        
        mf.process(temp);
        
        
        try {
            calculateGoal(input);
        } catch (SerialPortException ex) {
            Logger.getLogger(AutoControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    private void writeGoalToBot(){
        try {
            serialPort.writeBytes(Packet.getBytes(SET_GOAL, goal));
        } catch (SerialPortException ex) {
            Logger.getLogger(AutoControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void calculateGoal(Mat input) throws SerialPortException {
         List<Circle> balls = cfp.getCircles();
         if(balls.isEmpty()) return ;
         Circle closest = balls.get(0);
         for (Circle circle : balls) {
            if(closest.getY()<circle.getY()){
                closest = circle;
            }
         }
         
         goal =(int) Math.round((closest.getX()/input.cols())*100);
          System.out.println("Goal:" + goal);
         
          if(goal<10){
              goal = 0;
          }
          else if (goal>80){
              goal +=15;
          }
          else if(goal > 30){
              goal +=10;
          }
    }

    private void drawCircles(Mat input, List<Circle> circles) {
        for (Circle circle : circles) {
            Core.ellipse(input, new Point(circle.getX(), circle.getY()), new Size(circle.getRadius(),circle.getRadius()), 0, 0, 360,new Scalar(255, 0, 255), 4, 8, 0);
        }
    }
}
