package thermite.pongbotcontroller;


import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {

    public static void main(String[] args) throws SerialPortException, InterruptedException {
//        String[] portNames = SerialPortList.getPortNames();
//
//        for (int i = 0; i < portNames.length; i++) {
//            System.out.println(portNames[i]);
//        }
//        
//        SerialPort serialPort = new SerialPort(portNames[0]);
//        serialPort.openPort();
//        serialPort.setParams(9600, 8, 1, 0);
//        
//        Thread.sleep(2000);
//        State state = new State();
//        state.move=0;
//        state.paddle = 1;
//        state.motorASpeed = 100;
//        state.motorBSpeed = 0;
//        serialPort.writeBytes(state.getBytes());
        
        ManualControlProcessor mcp = new ManualControlProcessor();
        
        while(true)
            mcp.process(null);
        
    
    
    }
}
