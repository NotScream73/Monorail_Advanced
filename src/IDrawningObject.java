import java.awt.*;

public interface IDrawningObject {
    public float Step = 0;
    void SetObject(int x, int y, int width, int height);
    void MoveObject(Direction direction);
    void DrawningObject(Graphics g);
    float[] GetCurrentPosition();
    String GetInfo();
}
