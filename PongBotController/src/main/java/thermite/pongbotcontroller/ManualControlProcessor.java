/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thermite.pongbotcontroller;

import balldetection.processors.Processor;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.opencv.core.Mat;

/**
 *
 * @author gregor
 */
public class ManualControlProcessor implements Processor {

    BotController botController;
    SerialPort serialPort;

    public ManualControlProcessor() throws SerialPortException, InterruptedException {
        botController = new BotController();
        serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200,8,1,0);
        Thread.sleep(2000);
    }
    
    
    
    @Override
    public Mat process(Mat input) {
        try {
            move();
        } catch (SerialPortException ex) {
            Logger.getLogger(ManualControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return input;
    }

    private void move() throws SerialPortException {
        serialPort.writeBytes(botController.getStateObject().getBytes());
    }
    
}
