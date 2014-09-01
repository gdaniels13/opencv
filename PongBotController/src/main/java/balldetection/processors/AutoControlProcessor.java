/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import balldetection.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.opencv.core.Mat;
import thermite.pongbotcontroller.BotController;


public class AutoControlProcessor implements Processor {
    ContourProcessor cp = new ContourProcessor();
    SerialPort serialPort;

    public AutoControlProcessor() throws SerialPortException, InterruptedException {
        serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200,8,1,0);
        Thread.sleep(2000);
    }
    
    
    
    @Override
    public Mat process(Mat input) {
        input = cp.process(input);
        try {
            moveBot(input);
        } catch (SerialPortException ex) {
            Logger.getLogger(AutoControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    private void moveBot(Mat input) throws SerialPortException {
        int middle = input.cols()/2;
        
        int ball = (int) cp.getTopN(1).get(0).x;
        State state = new State();
        if(ball<middle){
            state.setMove(State.BACKWARD);
        }
        else{
            state.setMove(State.FORWARD);
        }
        state.setMoveSpeed((byte) 150);
        state.setPaddle(State.STOP);
        state.setPaddleSpeed((byte) 0);
        serialPort.writeBytes(state.getBytes());
    }
}
