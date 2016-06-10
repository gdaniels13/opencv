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
    public static volatile int goal;

    CircleFinderProcessor cfp;
    MatrixFrame mf;

    GcodeStreamer streamer;

    private Timer t;
    private final AiInterface ai;

    public AutoControlProcessor(final boolean streamGcode, AiInterface ail) throws SerialPortException, InterruptedException {
        cfp = new CircleFinderProcessor();
        mf = new MatrixFrame("circles");
        if(streamGcode)
            streamer = new GcodeStreamer();
        
        t = new Timer();
        ai = ail;
        t.schedule(new TimerTask() {
            private int lastGoal = 0;
            private boolean lastResult = false;
            private int nmc=0;

            @Override
            public void run() {
                if(!streamGcode) {
                    return;
                }
                try {
                    if ((Math.abs(goal - lastGoal) > 1 && streamer.checkIdle()) || nmc >10 ) {
                        streamer.moveToPercent(goal);
                        goal = lastGoal;
                        nmc =0;
                    }
                else{
                        nmc ++;
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to send new goal");
                }
            }
        }, 0, 50);
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

    private void calculateGoal(Mat input) throws SerialPortException {
        ai.calculateGoal(cfp.getCircles(), input);
        
     }

    private void drawCircles(Mat input, List<Circle> circles) {
        for (Circle circle : circles) {
            Core.ellipse(input, new Point(circle.getX(), circle.getY()), new Size(circle.getRadius(), circle.getRadius()), 0, 0, 360, new Scalar(255, 0, 255), 4, 8, 0);
        }
    }

    private void drawGoal(Mat temp) {
        int location = (int) (temp.size().width * ai.getGoal()/100.0);
        
        Core.arrowedLine(temp, new Point(location, temp.size().height), new Point(location, temp.size().height-100), new Scalar(255, 0, 255), 5, Core.LINE_8, 0, .25);
    }
}
