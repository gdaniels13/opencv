/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection.processors;

import balldetection.processors.Processor;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.opencv.core.Mat;
import thermite.pongbotcontroller.BotController;

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
        serialPort.addEventListener(new SerialPortEventListener() {

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                try {
                    System.out.println(serialPort.readString());
                } catch (SerialPortException ex) {
                    Logger.getLogger(ManualControlProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
//        serialPort.writeBytes(botController.getStateObject());
    }
    
}
