package balldetection.gameStrategies;

import balldetection.Circle;
import java.util.List;
import org.opencv.core.Mat;


public class DefaultAi implements AiInterface {
    private int goal = 50;
    
    @Override
    public void calculateGoal(List<Circle> balls,Mat input) {
        if (!balls.isEmpty()) {
            Circle closest = balls.get(0);
            for (Circle circle : balls) {
                if (closest.getY() < circle.getY()) {
                    closest = circle;
                }
            }
            goal = (int) Math.round((closest.getX() / input.cols()) * 100);
            if (goal < 15) {
                goal = 0;
            } else if (goal > 85) {
                goal = 100;
            }
        }
    }

    @Override
    public int getGoal() {
        return goal;
    }

}
