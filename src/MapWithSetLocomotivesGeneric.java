import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class MapWithSetLocomotivesGeneric <T extends IDrawningObject, U extends AbstractMap> implements Comparable<MapWithSetLocomotivesGeneric<T,U>> {
    private final int _pictureWidth;
    private final int _pictureHeight;
    private final int _placeSizeWidth = 183;
    private final int _placeSizeHeight = 39;
    private SetLocomotivesGeneric<T> _setLocomotives;
    private U _map;
    private Queue<T> _deteledLocomotives;
    public MapWithSetLocomotivesGeneric(int picWidth, int picHeight, U map)
    {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _setLocomotives = new SetLocomotivesGeneric<T>(width * height);
        _pictureWidth = picWidth;
        _pictureHeight = picHeight;
        _map = map;
        _deteledLocomotives = new LinkedList<>();
    }
    public int Add(T locomotive) throws StorageOverflowException {
        return _setLocomotives.Insert(locomotive);
    }
    public T Delete(int position) throws LocomotiveNotFoundException {
        T temp =  _setLocomotives.Remove(position);
        _deteledLocomotives.add(temp);
        return temp;
    }
    public BufferedImage ShowSet()
    {
        BufferedImage bmp = new BufferedImage(_pictureWidth, _pictureHeight, BufferedImage.TYPE_INT_RGB);
        Graphics gr = bmp.getGraphics();
        DrawBackground(gr);
        DrawLocomotives(gr);
        return bmp;
    }
    public BufferedImage ShowOnMap()
    {
        Shaking();
        for (var locomotive : _setLocomotives) {
            return _map.CreateMap(_pictureWidth, _pictureHeight, locomotive);
        }
        return new BufferedImage(_pictureWidth, _pictureHeight, BufferedImage.TYPE_INT_RGB);
    }
    public BufferedImage MoveObject(Direction direction)
    {
        if (_map != null)
        {
            return _map.MoveObject(direction);
        }
        return new BufferedImage(_pictureWidth, _pictureHeight, BufferedImage.TYPE_INT_RGB);
    }
    public String GetData(char separatorType, char separatorData)
    {
        String data = "" + _map.getClass().getName() + separatorType;
        for (var locomotive : _setLocomotives)
        {
            data += "" + locomotive.GetInfo() + separatorData;
        }
        return data;
    }
    public String GetDataMap(char separatorType, char separatorData)
    {
        String data = "" + _map.getClass().getName() + separatorType + "\n";
        for (var locomotive : _setLocomotives)
        {
            data += "" + locomotive.GetInfo() + separatorData;
        }
        return data;
    }
    public void LoadData(String[] records) throws StorageOverflowException {
        for(var rec : records){
            _setLocomotives.Insert((T)DrawningObjectLocomotive.Create(rec));
        }
    }
        public void Sort(Comparator<T> comparer)
    {
        _setLocomotives.SortSet(comparer);
    }

    @Override
    public int compareTo(MapWithSetLocomotivesGeneric<T, U> o) {
        if (o == null)
        {
            return 1;
        }
        if (this == o)
        {
            return 0;
        }
        Integer temp1 = _setLocomotives.Count();
        Integer temp2 = o._setLocomotives.Count();
        return temp1.compareTo(temp2);
    }
    private void Shaking()
    {
        int j = _setLocomotives.Count() - 1;
        for (int i = 0; i < _setLocomotives.Count(); i++)
        {
            if (_setLocomotives.Get(i) == null)
            {
                for (; j > i; j--)
                {
                    var locomotive = _setLocomotives.Get(j);
                    if (locomotive != null)
                    {
                        try {
                            _setLocomotives.Insert(locomotive, i);
                        } catch (StorageOverflowException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            _setLocomotives.Remove(j);
                        } catch (LocomotiveNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
                if (j <= i)
                {
                    return;
                }
            }
        }
    }
    private void DrawBackground(Graphics g)
    {
        Graphics2D gr = (Graphics2D)g;
        gr.fillRect( 0, 0, _pictureWidth, _pictureHeight);
        gr.setStroke(new BasicStroke(3));
        gr.setColor(Color.BLACK);
        for (int i = 0; i < _pictureWidth / _placeSizeWidth; i++)
        {
            for (int j = 0; j < _pictureHeight / _placeSizeHeight + 1; ++j)
            {
                gr.drawLine(i * _placeSizeWidth, j * _placeSizeHeight - 4, i * _placeSizeWidth + _placeSizeWidth, j * _placeSizeHeight - 4);
                gr.drawLine(i * _placeSizeWidth + _placeSizeWidth, j * _placeSizeHeight - 9, i * _placeSizeWidth + _placeSizeWidth, j * _placeSizeHeight - 4);
            }
        }
        gr.setStroke(new BasicStroke(1));
    }
    private void DrawLocomotives(Graphics g)
    {
        int currentWidth = 0;
        int currentHeight = _pictureHeight / _placeSizeHeight;
        int index = 0;
        for(var locomotive : _setLocomotives)
        {
            if (_setLocomotives.Get(index) != null){
                _setLocomotives.Get(index).SetObject(currentWidth * _placeSizeWidth, (currentHeight - 1) * _placeSizeHeight, _pictureWidth, _pictureHeight);
                _setLocomotives.Get(index).DrawningObject(g);
                currentWidth++;
                if (currentWidth > _pictureWidth / _placeSizeWidth - 1)
                {
                    currentWidth %= (_pictureWidth / _pictureWidth);
                    currentHeight--;
                }
            }
            index++;
        }
    }
    public T GetLocomotiveInList(int ind){
        return _setLocomotives.Get(ind);
    }
    public T GetLocomotiveInDeleted()
    {
        if(_deteledLocomotives.isEmpty())
            return null;
        return _deteledLocomotives.poll();
    }
    public void Clear() {
        _setLocomotives.Clear();
    }
}