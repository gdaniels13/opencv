package gcode;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import thermite.pongbotcontroller.SerialCommunicator;

public class GcodeStreamer {

    private final SerialPort serialPort ;//= new SerialPort(SerialPortList.getPortNames()[0]);
    private int max = 500;
    
    private static final String OK = "ok";

    public GcodeStreamer() {
        serialPort = new SerialPort("COM4");
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200, 8, 1, 0);
            Thread.sleep(2000);
        } catch (SerialPortException | InterruptedException ex) {
            Logger.getLogger(SerialCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean sendGcode(String command) {
//        System.out.println(command);
        try {
            return sendCommand(command);
        } catch (Exception ex) {
            Logger.getLogger(GcodeStreamer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public void dumpInput() throws SerialPortException{
        System.out.println(serialPort.readString());
    }
    
    public boolean moveXTo(int x){
        return sendGcode("G0 x" + x + "\n");
    }

    public boolean moveToPercent(int x){
        if(x>100 || x < 0 ){
            return false;
        }
        Double target = (x/100.0) * max;
        return moveXTo(target.intValue());
    }
    
    private boolean sendCommand(String command) throws SerialPortException {
        boolean result;
        if(checkIdle()){
            result = serialPort.writeString(command);
            return result;
        }
        return false;
    }
    
    private String waitForResponse() throws SerialPortException{
        while(serialPort.getInputBufferBytesCount()==0);
        StringBuilder sb = new StringBuilder();
        while(serialPort.getInputBufferBytesCount()>0 ){
            sb.append(serialPort.readString());
        }
        return sb.toString();
    }
    
    private boolean  waitForStop() throws SerialPortException{
        while(checkMoving());
        return true;
    }
    
    private boolean checkMoving() throws SerialPortException{
        return !checkIdle();
    }
    
    private boolean checkIdle() throws SerialPortException {
        serialPort.writeString("?");
        String response = waitForResponse();
        System.out.println(response);
        if(response.contains("Idle")){
            return true;
        }
        return false;
    }
    
    
    public static void main(String[] args) throws InterruptedException, SerialPortException {
        GcodeStreamer str = new GcodeStreamer();
        str.moveXTo(100);
        str.moveXTo(200);
        str.moveXTo(100);
        str.moveXTo(200);
        str.moveXTo(110); 
        str.moveXTo(100);
        str.moveXTo(110);
        str.moveXTo(100);
        str.moveXTo(110);
        str.moveXTo(100);
        str.moveXTo(110);
        str.moveXTo(100);
        str.moveXTo(110);
        str.moveXTo(100);
        str.moveXTo(110);
        str.moveXTo(0);
        
    }

}
