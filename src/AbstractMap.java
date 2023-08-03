import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class AbstractMap {
    private IDrawningObject _drawningObject = null;
    protected int[][] _map = null;
    protected int _width;
    protected int _height;
    protected float _size_x;
    protected float _size_y;
    protected final Random _random = new Random();
    protected final int _freeRoad = 0;
    protected final int _barrier = 1;

    public BufferedImage CreateMap(int width, int height, IDrawningObject drawningObject)
    {
        _width = width;
        _height = height;
        _drawningObject = drawningObject;
        GenerateMap();
        while (!SetObjectOnMap())
        {
            GenerateMap();
        }
        return DrawMapWithObject();
    }
    public BufferedImage MoveObject(Direction direction)
    {
        float[] cortege = _drawningObject.GetCurrentPosition();
        int leftCell = (int)(cortege[0] / _size_x);
        int upCell = (int)(cortege[1] / _size_y);
        int downCell = (int)(cortege[3] / _size_y);
        int rightCell = (int)(cortege[2] / _size_x);
        int step = (int)_drawningObject.Step;
        boolean canMove = true;
        switch (direction)
        {
            case Left:
                for (int i = leftCell - (int)(step / _size_x) - 1 >= 0 ? leftCell - (int)(step / _size_x) - 1 : leftCell; i < leftCell; i++)
                {
                    for (int j = upCell; j < downCell; j++)
                    {
                        if (_map[i][j] == _barrier)
                        {
                            canMove = false;
                        }
                    }
                }
                break;
            case Up:
                for (int i = leftCell; i <= rightCell; i++)
                {
                    for (int j = upCell - (int)(step / _size_x) - 1 >= 0 ? upCell - (int)(step / _size_x) - 1 : downCell - (int)(step / _size_x); j < downCell - (int)(step / _size_x); j++)
                    {
                        if (_map[i][j] == _barrier)
                        {
                            canMove = false;
                        }
                    }
                }
                break;
            case Down:
                for (int i = leftCell; i <= rightCell; i++)
                {
                    for (int j = downCell + (int)(step / _size_x) + 1 <= _map.length - 1 ? downCell + (int)(step / _size_x) + 1 : upCell; j > upCell; j--)
                    {
                        if (_map[i][j] == _barrier)
                        {
                            canMove = false;
                        }
                    }
                }
                break;
            case Right:
                for (int i = rightCell + (int)(step / _size_x) + 1 <= _map.length - 1 ? rightCell + (int)(step / _size_x) + 1 : rightCell; i > rightCell; i--)
                {
                    for (int j = upCell; j < downCell; j++)
                    {
                        if (_map[i][j] == _barrier)
                        {
                            canMove = false;
                        }
                    }
                }
                break;
        }
        if (canMove)
        {
            _drawningObject.MoveObject(direction);
        }
        return DrawMapWithObject();
    }
    private boolean SetObjectOnMap()
    {
        if (_drawningObject == null || _map == null)
        {
            return false;
        }
        int x = _random.nextInt(0, 10);
        int y = _random.nextInt(0, 10);
        _drawningObject.SetObject(x, y, _width, _height);
        float[] cortege = _drawningObject.GetCurrentPosition();
        int leftCell = (int)(cortege[0] / _size_x);
        int upCell = (int)(cortege[1] / _size_y);
        int downCell = (int)(cortege[3] / _size_y);
        int rightCell = (int)(cortege[2] / _size_x);
        for (int i = leftCell; i < rightCell; i++)
        {
            for (int j = upCell; j < downCell; j++)
            {
                if (_map[i][j] == _barrier)
                {
                    return false;
                }
            }
        }
        return true;
    }
    private BufferedImage DrawMapWithObject()
    {
        BufferedImage bmp =  new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
        if (_drawningObject == null || _map == null)
        {
            return bmp;
        }
        Graphics gr = bmp.getGraphics();
        for (int i = 0; i < _map.length; ++i)
        {
            for (int j = 0; j < _map[0].length; ++j)
            {
                if (_map[i][j] == _freeRoad)
                {
                    DrawRoadPart(gr, i, j);
                }
                    else if (_map[i][j] == _barrier)
                {
                    DrawBarrierPart(gr, i, j);
                }
            }
        }
        _drawningObject.DrawningObject(gr);
        return bmp;
    }
    protected abstract void GenerateMap();
    protected abstract void DrawRoadPart(Graphics g, int i, int j);
    protected abstract void DrawBarrierPart(Graphics g, int i, int j);
    @Override
    public boolean equals(Object other){
        if (other == null)
        {
            return false;
        }
        var otherMap = (AbstractMap)other;
        if (otherMap == null)
        {
            return false;
        }
        if (_width != otherMap._width)
        {
            return false;
        }
        if (_height != otherMap._height)
        {
            return false;
        }
        if (_size_x != otherMap._size_x)
        {
            return false;
        }
        if (_size_y != otherMap._size_y)
        {
            return false;
        }
        for (int i = 0; i < _map.length; i++)
        {
            for (int j = 0; j < _map[i].length; j++)
            {
                if (_map[i][j] != otherMap._map[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
