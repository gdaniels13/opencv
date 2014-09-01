package thermite.pongbotcontroller;

public class State {

    public static final byte FORWARD = 0;
    public static final byte BACKWARD = 1;
    public static final byte STOP = 2;
    private byte move;//0 forward 1 backward
    private byte paddle;
    private byte moveSpeed;//0-255
    private byte paddleSpeed;
    

    public byte[] getBytes() {
        return new byte[]{move, moveSpeed, paddle, paddleSpeed};
    }

    public byte getMove() {
        return move;
    }

    public void setMove(byte move) {
        this.move = move;
    }

    public byte getPaddle() {
        return paddle;
    }

    public void setPaddle(byte paddle) {
        this.paddle = paddle;
    }

    public byte getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(byte moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public byte getPaddleSpeed() {
        return paddleSpeed;
    }

    public void setPaddleSpeed(byte paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }

}
