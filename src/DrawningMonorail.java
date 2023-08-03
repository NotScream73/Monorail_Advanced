import java.awt.*;

public class DrawningMonorail extends DrawningLocomotive{
    private Color dopColor;
    protected DrawningMonorail(int speed, float weight, Color bodyColor, Color dopColor, boolean magneticRail, boolean secondCabin) {
        super(speed, weight, bodyColor, 182, 38);
        Locomotive = new EntityMonorail(speed, weight, bodyColor, dopColor, magneticRail, secondCabin);
        fields = new Object[]{ speed, weight, bodyColor, wheels.getClass().getName(), wheelsCount, dopColor, magneticRail, secondCabin };
    }
    protected DrawningMonorail(EntityLocomotive locomotive, IWheelsDrawingObject wheels)
    {
        super(locomotive, wheels);
        Locomotive = locomotive;
    }
    public void ChangeDopColor(Color color) {
        var temp = (EntityMonorail) Locomotive;
        Locomotive = new EntityMonorail(temp.GetSpeed(), temp.GetWeight(), temp.GetBodyColor(), color, temp.GetMagneticRail(), temp.GetSecondCabin());
        dopColor = color;
        fields[5] = color;
    }
    @Override
    public void ChangeColor(Color color){
        var temp = (EntityMonorail) Locomotive;
        dopColor = dopColor==null ? Color.WHITE : dopColor;
        Locomotive = new EntityMonorail(temp.GetSpeed(), temp.GetWeight(), color, dopColor, temp.GetMagneticRail(), temp.GetSecondCabin());
        fields[2] = color;
    }
    @Override
    public void DrawTransport(Graphics g2d){
        if (!(GetLocomotive() instanceof EntityMonorail monorail)){
            return;
        }
        super.DrawTransport(g2d);
        Graphics2D g = (Graphics2D) g2d;

        if (monorail.GetMagneticRail())
        {
            g.setColor(Color.BLACK);
            int[] pointsXMagneticRail = {
                    (int)_startPosX + 1,(int)_startPosX + 5,(int)_startPosX + 90,(int)_startPosX + 94, (int)_startPosX + 90, (int)_startPosX + 5
            };
            int[] pointsYMagneticRail = {
                    (int)_startPosY + 31, (int)_startPosY + 25, (int)_startPosY + 25, (int)_startPosY + 31, (int)_startPosY + 37, (int)_startPosY + 37
            };
            g.fillPolygon(pointsXMagneticRail,pointsYMagneticRail, 6);
        }

        if (monorail.GetSecondCabin())
        {
            g.setColor(monorail.GetDopColor());
            int[] pointsX = {
                    (int)_startPosX + 93,(int)_startPosX + 98,(int)_startPosX + 179,(int)_startPosX + 179, (int)_startPosX + 93
            };
            int[] pointsY = {
                    (int)_startPosY + 1, (int)_startPosY + 1, (int)_startPosY + 1, (int)_startPosY + 25, (int)_startPosY + 25
            };
            g.fillPolygon(pointsX,pointsY, 5);
            // Окна
            g.setColor(Color.WHITE);
            g.fillRect((int)_startPosX + 11 + 89, (int)_startPosY + 3, 7, 8);
            g.fillRect((int)_startPosX + 22 + 89, (int)_startPosY + 3, 7, 8);
            g.fillRect((int)_startPosX + 80 + 89, (int)_startPosY + 3, 7, 8);
            g.setColor(Color.BLUE);
            g.drawRect((int)_startPosX + 11 + 89, (int)_startPosY + 3, 7, 8);
            g.drawRect((int)_startPosX + 22 + 89, (int)_startPosY + 3, 7, 8);
            g.drawRect((int)_startPosX + 80 + 89, (int)_startPosY + 3, 7, 8);
            // Полоса и дверь
            g.setColor(Color.BLACK);
            g.drawLine((int)_startPosX + 4 + 89, (int)_startPosY + 13, (int)_startPosX + 32 + 89, (int)_startPosY + 13);
            g.drawLine((int)_startPosX + 42 + 89, (int)_startPosY + 13, (int)_startPosX + 90 + 89, (int)_startPosY + 13);
            g.drawRect((int)_startPosX + 32 + 89, (int)_startPosY + 5, 10, 17);
            // Крепление к вагону
            g.fillRect((int)_startPosX + 90 + 89, (int)_startPosY + 3, 3, 20);
            // Нижняя часть
            int[] pointsXMagneticRail = {
                    (int)_startPosX + 1 + 89,(int)_startPosX + 5 + 89,(int)_startPosX + 90 + 89,(int)_startPosX + 94 + 89, (int)_startPosX + 90 + 89, (int)_startPosX + 5 + 89
            };
            int[] pointsYMagneticRail = {
                    (int)_startPosY + 31, (int)_startPosY + 25, (int)_startPosY + 25, (int)_startPosY + 31, (int)_startPosY + 37, (int)_startPosY + 37
            };
            g.fillPolygon(pointsXMagneticRail,pointsYMagneticRail, 6);
        }
    }
}
