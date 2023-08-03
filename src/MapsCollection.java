import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsCollection {

    private final HashMap<String, MapWithSetLocomotivesGeneric<IDrawningObject, AbstractMap>> _mapStorages;
    public ArrayList<String> Keys(){
        return new ArrayList<>(_mapStorages.keySet());
    }
    private final int _pictureWidth;
    private final int _pictureHeight;
    private final char separatorDict = '|';
    private final char separatorData = ';';
    public MapsCollection(int pictureWidth, int pictureHeight)
    {
        _mapStorages = new HashMap<>();
        _pictureWidth = pictureWidth;
        _pictureHeight = pictureHeight;
    }
    public void AddMap(String name, AbstractMap map)
    {
        if (_mapStorages.containsKey(name))
        {
            return;
        }
        _mapStorages.put(name, new MapWithSetLocomotivesGeneric<>(_pictureWidth, _pictureHeight, map));
    }
    public void DelMap(String name)
    {
        if (_mapStorages.containsKey(name))
        {
            _mapStorages.remove(name);
        }
    }
    public MapWithSetLocomotivesGeneric<IDrawningObject, AbstractMap> Get (String ind)
    {
        if (_mapStorages.containsKey(ind))
        {
            return _mapStorages.get(ind);
        }
        return null;
    }

    public IDrawningObject get(String name, int ind) {
        if (_mapStorages.containsKey(name))
        {
            return _mapStorages.get(name).GetLocomotiveInList(ind);
        }
        return null;
    }
    public void SaveData(String filename)
    {
        File file = new File(filename);
        if (file.exists()){
            file.delete();
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(filename)))
        {
            br.write("MapsCollection\n");
            for(Map.Entry<String, MapWithSetLocomotivesGeneric<IDrawningObject, AbstractMap>> entry : _mapStorages.entrySet()) {
                br.write("" + entry.getKey() + separatorDict + entry.getValue().GetData(separatorDict, separatorData) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadData(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()){
            throw new FileNotFoundException("Файл не найден");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String str = br.readLine();
            if (!str.contains("MapsCollection"))
            {
                throw new IllegalArgumentException("Формат данных в файле не правильный");
            }
            _mapStorages.clear();
            str = br.readLine();
            while (str != null)
            {
                var elem = str.split(String.format("\\%c",separatorDict));
                AbstractMap map = null;
                switch (elem[1])
                {
                    case "SimpleMap":
                        map = new SimpleMap();
                        break;
                    case "DesertMap":
                        map = new DesertMap();
                        break;
                    case "LawnMap":
                        map = new LawnMap();
                        break;
                }
                _mapStorages.put(elem[0], new MapWithSetLocomotivesGeneric<IDrawningObject, AbstractMap>(_pictureWidth, _pictureHeight, map));
                _mapStorages.get(elem[0]).LoadData(elem[2].split(String.format("\\%c", separatorData)));
                str = br.readLine();
            }
        } catch (IOException | StorageOverflowException e) {
            throw new RuntimeException(e);
        }
    }
    public void SaveMap(String filename, String index)
    {
        File file = new File(filename);
        if (file.exists()){
            file.delete();
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(filename)))
        {
            br.write("MapCollection\n");
            var entry = _mapStorages.get(index);
            br.write(index + separatorDict);
            br.write(entry.GetDataMap(separatorDict, separatorData) + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadMap(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()){
            throw new FileNotFoundException("Файл не найден");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String str = br.readLine();
            if (!str.contains("MapCollection"))
            {
                throw new IllegalArgumentException("Формат данных в файле не правильный");
            }
            str = br.readLine();
            var elem = str.split(String.format("\\%c",separatorDict));
            AbstractMap map = null;
            switch (elem[1])
            {
                case "SimpleMap":
                    map = new SimpleMap();
                    break;
                case "DesertMap":
                    map = new DesertMap();
                    break;
                case "LawnMap":
                    map = new LawnMap();
                    break;
            }
            if (_mapStorages.containsKey(elem[0])){
                _mapStorages.get(elem[0]).Clear();
            }
            _mapStorages.put(elem[0], new MapWithSetLocomotivesGeneric<IDrawningObject, AbstractMap>(_pictureWidth, _pictureHeight, map));
            str = br.readLine();
            _mapStorages.get(elem[0]).LoadData(str.split(String.format("\\%c", separatorData)));
        } catch (IOException | StorageOverflowException e) {
            throw new RuntimeException(e);
        }
    }
}