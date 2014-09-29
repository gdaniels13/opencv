/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

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
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class AutoControlProcessor implements Processor {
    ContourProcessor cp = new ContourProcessor();
    SerialPort serialPort;
    private int goal;

    private Timer t;
    public AutoControlProcessor() throws SerialPortException, InterruptedException {
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
                        System.out.println(goal);
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to send new goal");
                }
            }
        }, 0, 100);
        
    }
    
    
    
    @Override
    public Mat process(Mat input) {
        input = cp.process(input);
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
        
//        System.out.println(cp.getLargestRadii());
        List<Point> topN = cp.getTopN(3);
        if(topN.isEmpty()){
            return;
        }
        // get closest one to the edge y
        Point max = topN.get(0);
        
        for (Point point : topN) {
            if(point.x<max.x){
                max = point;
            }
        }
        
        
        
        
        
        if(topN.isEmpty()){
            return;
        }
        goal  =  (int) ((max.x/input.cols())*100);
        
//        System.out.println(goal);
        
//        serialPort.writeBytes(Packet.getBytes(SET_GOAL, goal));
    }
}
