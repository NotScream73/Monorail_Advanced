import java.awt.*;

public class DrawningTriangleWheels extends DrawningWheels{
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
        g.drawLine( _startPosX + 11+ 3, _startPosY + 27+2, _startPosX + 11+ 3 + 11- 6, _startPosY + 27+2);
        g.drawLine( _startPosX + 11+ 3, _startPosY + 27+2, _startPosX + 11+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4 );
        g.drawLine( _startPosX + 11+ 3 + 11- 6, _startPosY + 27+2, _startPosX + 11+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4);

        g.drawLine( _startPosX + 69+ 3, _startPosY + 27+2, _startPosX + 69+ 3 + 11- 6, _startPosY + 27+2);
        g.drawLine( _startPosX + 69+ 3, _startPosY + 27+2, _startPosX + 69+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4 );
        g.drawLine( _startPosX + 69+ 3 + 11- 6, _startPosY + 27+2, _startPosX + 69+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4);
        switch (wheelsCount){
            case Four:
                g.drawLine( _startPosX + 51+ 3, _startPosY + 27+2, _startPosX + 51+ 3 + 11- 6, _startPosY + 27+2);
                g.drawLine( _startPosX + 51+ 3, _startPosY + 27+2, _startPosX + 51+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4 );
                g.drawLine( _startPosX + 51+ 3 + 11- 6, _startPosY + 27+2, _startPosX + 51+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4);
            case Three:
                g.drawLine( _startPosX + 29+ 3, _startPosY + 27+2, _startPosX + 29+ 3 + 11- 6, _startPosY + 27+2);
                g.drawLine( _startPosX + 29+ 3, _startPosY + 27+2, _startPosX + 29+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4 );
                g.drawLine( _startPosX + 29+ 3 + 11- 6, _startPosY + 27+2, _startPosX + 29+ 3 + (11- 6)/2, _startPosY + 27+2 + 8 - 4);
                break;
            default:
                break;
        }
    }
}
