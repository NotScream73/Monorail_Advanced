import java.awt.*;

public interface IWheelsDrawingObject {
    void SetNumberWheels(int wheelsCount);
    void DrawWheels(int _startPosX, int _startPosY, Color color, Graphics2D g);
}