import java.awt.*;

public class DrawningRectWheels extends DrawningWheels {
    private NumberWheels wheelsCount;
    @Override
    public void SetNumberWheels(int wheelsCount){
        super.SetNumberWheels(wheelsCount);
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
        super.DrawWheels(_startPosX, _startPosY, color, g);
        g.setColor(Color.BLACK);
        g.drawRect( _startPosX + 11+ 3, _startPosY + 27+2, 11- 6, 8-4);

        g.drawRect( _startPosX + 69+ 3, _startPosY + 27+2, 11- 6, 8-4);
        switch (wheelsCount){
            case Four:
                g.drawRect(_startPosX + 51 + 3, _startPosY + 27 + 2, 11 - 6, 8 - 4);
            case Three:
                g.drawOval(_startPosX + 29 + 3, _startPosY + 27 + 2, 11 - 6, 8 - 4);
                break;
            default:
                break;
        }
    }
}
