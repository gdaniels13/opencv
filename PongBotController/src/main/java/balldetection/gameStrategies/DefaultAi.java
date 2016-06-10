package balldetection.gameStrategies;

import balldetection.Circle;
import balldetection.processors.AutoControlProcessor;
import java.util.List;
import org.opencv.core.Mat;


public class DefaultAi implements AiInterface {
       
    @Override
    public void calculateGoal(List<Circle> balls,Mat input) {
        if (!balls.isEmpty()) {
            Circle closest = balls.get(0);
            for (Circle circle : balls) {
                if (closest.getY() < circle.getY()) {
                    closest = circle;
                }
            }
            int goal = (int) Math.round((closest.getX() / input.cols()) * 100);
            if (goal < 20) {
                goal = 0;
            } else if (goal<86 ){
                goal +=2;
            }
            else if (goal > 85) {
                goal = 100;
            } 
            AutoControlProcessor.goal = goal;
        }
    }

    @Override
    public int getGoal() {
        return AutoControlProcessor.goal;
    }

}
