package balldetection;

public class Circle {
    private double x;
    private double y;
    private double radius;
    public static final double THRESHOLD=7;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean isTheSame(Circle other){
        if( distance(this,other)<THRESHOLD)
        {
            return true;
        }
        return false;
    }
 
    
    public static double distance(Circle a, Circle b){
        return Math.sqrt(Math.pow(a.x-b.x,2) + Math.pow(b.y-a.y,2));
    }
    
    
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" + "x=" + x + ", y=" + y + ", radius=" + radius + '}';
    }
    
}
