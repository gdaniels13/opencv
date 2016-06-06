package balldetection.gameStrategies;

import balldetection.Circle;
import java.util.List;
import org.opencv.core.Mat;

public interface AiInterface {
    public void calculateGoal(List<Circle> balls,Mat input);
    public int getGoal();
    
}
