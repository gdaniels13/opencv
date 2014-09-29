package thermite.pongbotcontroller;


import balldetection.Packet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

public class Main {
    static Timer t;
    private static BotController bc;
    
    public static void main(String[] args) throws SerialPortException, InterruptedException {
        
        bc = new BotController();
        t = new Timer();
        t.schedule(new TimerTask() {
            private byte lastGoal = 50;
            @Override
            public void run() {
                try {
                    byte curGoal = bc.getGoal();
                    if(curGoal != lastGoal){
                        SerialCommunicator.SendPacket(new Packet(Packet.SET_GOAL, curGoal));
                        lastGoal = curGoal;
                        System.out.println(curGoal);
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to send new goal");
                }
            }
        }, 0, 100);
        
    }

}
