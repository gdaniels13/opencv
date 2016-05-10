/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thermite.pongbotcontroller;

import balldetection.Packet;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author gregor
 */
public class SerialCommunicator {
    private static final SerialPort serialPort =       
                    new SerialPort(SerialPortList.getPortNames()[0]);
//    static{
//        try {
//            serialPort.openPort();
//            serialPort.setParams(SerialPort.BAUDRATE_115200,8,1,0);
//            Thread.sleep(2000);
//        } catch (SerialPortException ex) {
//            Logger.getLogger(SerialCommunicator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SerialCommunicator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//    
    public static void SendPacket(Packet p) throws SerialPortException{
        serialPort.writeBytes(p.getBytes());
    }
    
    public static void SendBytes(byte[] b) throws SerialPortException{
        serialPort.writeBytes(b);
    }
    
    public static String getString(){
        try {
            if(serialPort.getOutputBufferBytesCount()>0){
                return serialPort.readString();
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(SerialCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
