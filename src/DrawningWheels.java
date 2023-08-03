import java.awt.*;

public class DrawningWheels implements IWheelsDrawingObject {
    private NumberWheels wheelsCount;
    @Override
    public void SetNumberWheels(int wheelsCount){
        if (wheelsCount == 2){
            this.wheelsCount = NumberWheels.Two;
        }else if (wheelsCount == 3){
            this.wheelsCount = NumberWheels.Three;
        }else if (wheelsCount == 4){
            this.wheelsCount = NumberWheels.Four;
        }
    }
    @Override
    public void DrawWheels(int _startPosX, int _startPosY, Color color, Graphics2D g){
        g.setColor(color);
        g.fillOval( _startPosX + 11, _startPosY + 27, 11, 8);
        g.fillOval(_startPosX + 69, _startPosY + 27, 11, 8);
        g.setColor(Color.BLACK);
        g.drawOval( _startPosX + 11, _startPosY + 27, 11, 8);
        g.drawOval( _startPosX + 69, _startPosY + 27, 11, 8);
        switch (wheelsCount){
            case Four:
                g.setColor(color);
                g.fillOval(_startPosX + 51, _startPosY + 27, 11, 8);
                g.setColor(Color.BLACK);
                g.drawOval(_startPosX + 51, _startPosY + 27, 11, 8);
            case Three:
                g.setColor(color);
                g.fillOval(_startPosX + 29, _startPosY + 27, 11, 8);
                g.setColor(Color.BLACK);
                g.drawOval(_startPosX + 29, _startPosY + 27, 11, 8);
                break;
            default:
                break;
        }
    }
}
