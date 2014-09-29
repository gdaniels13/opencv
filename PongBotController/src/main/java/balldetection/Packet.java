/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balldetection;

/**
 *
 * @author gregor
 */
public class Packet {
    public static final byte SET_GOAL='a';
    public static final byte SET_MIN=1;
    public static final byte SET_MAX=2;
    public static final byte RESET_ENCODER=3;
    public static final byte DRIVE_LEFT=4;
    public static final byte DRIVE_RIGHT=5;
    public static final byte PADDLE_FORWARD=6;
    public static final byte PADDLE_BACKWARD=7;
    public static final byte AUTOMATIC=9;
    
    byte command;
    byte data;

    public Packet(byte command, byte data) {
        this.command = command;
        this.data = data;
    }

    public Packet(byte command, int data) {
        this.command = command;
        this.data = (byte) data;
    }
    
    
    
    
    
    public byte[] getBytes(){
        return new byte[]{command,data};
    }
    
    public static byte[] getBytes(byte command, byte data){
        return new byte[]{command,data};
    }
    
    public static byte[] getBytes(byte command,int data){
        return getBytes(command, (byte) data);
    }
    
    
}
