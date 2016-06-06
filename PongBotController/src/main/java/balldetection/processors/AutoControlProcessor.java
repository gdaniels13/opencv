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
import balldetection.gameStrategies.AiInterface;
import gcode.GcodeStreamer;
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
    private volatile int goal;

    GcodeStreamer streamer;

    private Timer t;
    private final AiInterface ai;

    public AutoControlProcessor(final boolean streamGcode, AiInterface ail) throws SerialPortException, InterruptedException {
        cfp = new CircleFinderProcessor();
        mf = new MatrixFrame("circles");
        streamer = new GcodeStreamer();
        t = new Timer();
        ai = ail;
        t.schedule(new TimerTask() {
            private int lastGoal = 0;
            private boolean lastResult = false;

            @Override
            public void run() {
                goal = ai.getGoal();
                try {
                    if (Math.abs(goal - lastGoal) > 1  && streamGcode && streamer.checkIdle()) {
                        streamer.moveToPercent(goal);
                        goal = lastGoal;
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to send new goal");
                }
            }
        }, 0, 10);
    }

    @Override
    public Mat process(Mat input) {
        Mat temp = input.clone();
        //new Mat(input, new Rect(0, 0, input.width(), input.height()));
//        input.copyTo(temp);
        input = cfp.process(input);
        drawCircles(temp, cfp.getCircles());


        try {
            calculateGoal(input);
        } catch (SerialPortException ex) {
            Logger.getLogger(AutoControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        drawGoal(temp);
        
        mf.process(temp);
        return input;
    }

//    private void writeGoalToBot(){
//        try {
//            serialPort.writeBytes(Packet.getBytes(SET_GOAL, goal));
//        } catch (SerialPortException ex) {
//            Logger.getLogger(AutoControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private void calculateGoal(Mat input) throws SerialPortException {
        ai.calculateGoal(cfp.getCircles(), input);
        
 //        if (!balls.isEmpty()) {
////            goal = 50;
////        } else {
//
//            Circle closest = balls.get(0);
//            for (Circle circle : balls) {
//                if (closest.getY() < circle.getY()) {
//                    closest = circle;
//                }
//            }
//            goal = (int) Math.round((closest.getX() / input.cols()) * 100);
////          System.out.println("Goal:" + goal);
//
//            if (goal < 15) {
//                goal = 0;
//            } else if (goal > 85) {
//                goal = 100;
//            }
//            
//            
////          else if (goal>80){
////              goal +=15;
////          }
////          else if(goal > 30){
////              goal +=10;
////          }
//        }
    }

    private void drawCircles(Mat input, List<Circle> circles) {
        for (Circle circle : circles) {
            Core.ellipse(input, new Point(circle.getX(), circle.getY()), new Size(circle.getRadius(), circle.getRadius()), 0, 0, 360, new Scalar(255, 0, 255), 4, 8, 0);
        }
    }

    private void drawGoal(Mat temp) {
        int location = (int) (temp.size().width * goal/100.0);
        
        Core.arrowedLine(temp, new Point(location, temp.size().height), new Point(location, temp.size().height-100), new Scalar(255, 0, 255), 5, Core.LINE_8, 0, .25);
    }
}
