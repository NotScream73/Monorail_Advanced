import java.awt.*;

public class SimpleMap extends AbstractMap{
    private final Color barrierColor = Color.black;
    private final Color roadColor = Color.gray;

    protected void DrawBarrierPart(Graphics g, int i, int j)
    {
        g.setColor(barrierColor);
        g.fillRect((int)(i * _size_x), (int)(j * _size_y), (int)_size_x, (int)_size_y);
    }
    protected void DrawRoadPart(Graphics g, int i, int j)
    {
        g.setColor(roadColor);
        g.fillRect((int)(i * _size_x), (int)(j * _size_y), (int)_size_x, (int)_size_y);
    }
    protected void GenerateMap()
    {
        _map = new int[100][100];
        _size_x = (float)_width / _map.length;
        _size_y = (float)_height / _map[0].length;
        int counter = 0;
        for (int i = 0; i < _map.length; ++i)
        {
            for (int j = 0; j < _map[0].length; ++j)
            {
                _map[i][j] = _freeRoad;
            }
        }
        while (counter < 50)
        {
            int x = _random.nextInt(0, 100);
            int y = _random.nextInt(0, 100);
            if (_map[x][y] == _freeRoad)
            {
                _map[x][y] = _barrier;
                counter++;
            }
        }
    }
}
