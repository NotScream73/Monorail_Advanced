import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class DrawningLocomotive implements Iterable<Object>, Iterator<Object> {
    EntityLocomotive Locomotive;
    protected IWheelsDrawingObject wheels;
    protected int wheelsCount;
    public EntityLocomotive GetLocomotive() {
        return Locomotive;
    }
    protected float _startPosX;
    protected float _startPosY;
    private Integer _pictureWidth = null;
    private Integer _pictureHeight = null;
    protected int _LocomotiveWidth = 120;
    protected int _LocomotiveHeight = 40;
    // переменная, чтобы определить, нужно отрисовывать задний фон или нет
    private boolean drawBack;
    private int currentIndex = 0;
    protected Object[] fields;
    public void SetDrawBack(boolean mode){
        drawBack = mode;
    }
    public void ChangeColor(Color color){
        Locomotive = new EntityLocomotive(Locomotive.GetSpeed(), Locomotive.GetWeight(), color);
        fields[2] = color;
    }
    public DrawningLocomotive(int speed, float weight, Color bodycolor) {
        Random rand = new Random();
        Locomotive = new EntityLocomotive(speed, weight, bodycolor);
        int randomNumber=rand.nextInt(3);
        if (randomNumber==0){
            wheels = new DrawningRectWheels();
        }
        else if (randomNumber==1){
            wheels = new DrawningTriangleWheels();
        }
        else{
            wheels = new DrawningWheels();
        }
        wheelsCount = rand.nextInt(2,5);
        wheels.SetNumberWheels(wheelsCount);
        fields = new Object[]{speed, weight, bodycolor, wheels.getClass().getName(), wheelsCount};
    }
    public void SetWheels(int Count, IWheelsDrawingObject Wheels){
        wheels = Wheels;
        wheelsCount = Count;
        wheels.SetNumberWheels(Count);
        fields[3] = Wheels.getClass().getName();
        fields[4] = Count;
    }
    protected DrawningLocomotive(int speed, float weight, Color bodyColor, int locomotiveWidth, int locomotiveHeight){
        this(speed, weight, bodyColor);
        _LocomotiveWidth = locomotiveWidth;
        _LocomotiveHeight = locomotiveHeight;
    }
    protected DrawningLocomotive(EntityLocomotive locomotive, IWheelsDrawingObject wheel)
    {
        Locomotive = locomotive;
        wheels = wheel;
        Random rand = new Random();
        wheelsCount = rand.nextInt(2,5);
        wheels.SetNumberWheels(wheelsCount);
        fields = new Object[]{locomotive.GetSpeed(), locomotive.GetWeight(), locomotive.GetBodyColor(), wheel.getClass().getName(), wheelsCount};
    }

    public void SetPosition(int x, int y, int width, int height) {
        if (width <= _LocomotiveWidth + x || height <= _LocomotiveHeight + y || x < 0 || y < 0) {
            _pictureWidth = null;
            _pictureHeight = null;
            return;
        }
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    public void MoveTransport(Direction direction) {
        if (GetLocomotive() == null) {
            return;
        }
        switch (direction) {
            case Up:
                if (_startPosY - Locomotive.Step() > 0) {
                    _startPosY -= Locomotive.Step();
                }
                break;
            case Down:
                if (_startPosY + _LocomotiveHeight + Locomotive.Step() < _pictureHeight) {
                    _startPosY += Locomotive.Step();
                }
                break;
            case Left:
                if (_startPosX - Locomotive.Step() > 0) {
                    _startPosX -= Locomotive.Step();
                }
                break;
            case Right:
                if (_startPosX + _LocomotiveWidth + Locomotive.Step() < _pictureWidth) {
                    _startPosX += Locomotive.Step();
                }
                break;
        }
    }

    public void DrawTransport(Graphics g2d) {
        if (GetLocomotive() == null) {
            return;
        }
        if (_startPosX < 0 || _startPosY < 0 || _pictureWidth == null || _pictureHeight == null) {
            return;
        }
        Graphics2D g = (Graphics2D) g2d;
        if (drawBack){
            g.setColor(Color.WHITE);
            g.fillRect(0,0,_pictureWidth,_pictureHeight);
        }
        g.setColor(Color.BLACK);
        g.setColor(Locomotive.GetBodyColor());
        int[] pointX = {
                (int)_startPosX + 4,(int)_startPosX + 9,(int)_startPosX + 90,
                (int)_startPosX + 90,(int)_startPosX + 4
        };
        int[] pointY = {
                (int)_startPosY + 12, (int)_startPosY + 1, (int)_startPosY + 1,
                (int)_startPosY + 25, (int)_startPosY + 25
        };
        g.fillPolygon(pointX, pointY,5);
        g.setColor(Color.WHITE);
        g.fillRect( (int)_startPosX + 11, (int)_startPosY + 3, 7, 8);
        g.fillRect( (int)_startPosX + 22, (int)_startPosY + 3, 7, 8);
        g.fillRect( (int)_startPosX + 80, (int)_startPosY + 3, 7, 8);
        g.setColor(Color.BLUE);
        g.drawRect((int)_startPosX + 11, (int)_startPosY + 3, 7, 8);
        g.drawRect((int)_startPosX + 22, (int)_startPosY + 3, 7, 8);
        g.drawRect((int)_startPosX + 80, (int)_startPosY + 3, 7, 8);
        g.setColor(Color.BLACK);
        g.drawLine( (int)_startPosX + 4, (int)_startPosY + 13, (int)_startPosX + 32, (int)_startPosY + 13);
        g.drawLine( (int)_startPosX + 42, (int)_startPosY + 13, (int)_startPosX + 90, (int)_startPosY + 13);
        g.drawRect( (int)_startPosX + 32, (int)_startPosY + 5, 10, 17);
        g.fillRect( (int)_startPosX + 90, (int)_startPosY + 3, 3, 20);
        int[] pointXDown = {
                (int)_startPosX + 1,(int)_startPosX + 5,(int)_startPosX + 37,(int)_startPosX + 34
        };
        int[] pointYDown = {
                (int)_startPosY + 31, (int)_startPosY + 26, (int)_startPosY + 26, (int)_startPosY + 31
        };
        g.fillPolygon(pointXDown,pointYDown, 4);
        int[] pointXDown2 = {
                (int)_startPosX + 54,(int)_startPosX + 58,(int)_startPosX + 94,(int)_startPosX + 90
        };
        int[] pointYDown2 = {
                (int)_startPosY + 26, (int)_startPosY + 31, (int)_startPosY + 31, (int)_startPosY + 26
        };
        g.fillPolygon(pointXDown2, pointYDown2, 4);
        wheels.DrawWheels((int)_startPosX, (int)_startPosY,Locomotive.GetBodyColor(),g );
    }

    public void ChangeBorders(int width, int height) {
        _pictureWidth = width;
        _pictureHeight = height;
        if (_pictureWidth <= _LocomotiveWidth || _pictureHeight <= _LocomotiveHeight) {
            _pictureWidth = null;
            _pictureHeight = null;
            return;
        }
        if (_startPosX + _LocomotiveWidth > _pictureWidth) {
            _startPosX = _pictureWidth - _LocomotiveWidth;
        }
        if (_startPosY + _LocomotiveHeight > _pictureHeight) {
            _startPosY = _pictureHeight - _LocomotiveHeight;
        }
    }

    public float[] GetCurrentPosition(){
        return new float[]{_startPosX, _startPosY, _startPosX + _LocomotiveWidth, _startPosY + _LocomotiveHeight};
    }
    @Override
    public Iterator iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        return currentIndex < fields.length && fields[currentIndex] != null;
    }

    @Override
    public Object next()
    {
        return fields[currentIndex++];
    }
}