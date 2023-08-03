import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
public class SetLocomotivesGeneric<T extends Object> implements Iterable<T> {
    private ArrayList<T> _places;
    public int Count(){
        return _places.size();
    }
    private final int _maxCount;
    public SetLocomotivesGeneric(int count)
    {
        _maxCount = count;
        _places = new ArrayList<>(count);
    }
    public int Insert(T locomotive) throws StorageOverflowException {
        return Insert(locomotive, 0);
    }
    public int Insert(T locomotive, int position) throws StorageOverflowException {
        if (_places.contains(locomotive))
        {
            return -1;
        }
        if (Count() == _maxCount){
            throw new StorageOverflowException(_maxCount);
        }
        if (position >= _maxCount || position < 0)
        {
            return -1;
        }
        _places.add(position, locomotive);
        return position;
    }
    public T Remove(int position) throws LocomotiveNotFoundException {
        if (position < 0 || position >= Count())
        {
            throw new LocomotiveNotFoundException(position);
        }
        if (_places.get(position) == null)
        {
            throw new LocomotiveNotFoundException(position);
        }
        return (T) _places.remove(position);
    }
    public T Get(int position)
    {
        if (position < 0 || position >= Count())
        {
            return null;
        }
        return _places.get(position);
    }
    public void Set(int position, T locomotive){
        if (position < _maxCount && position >= 0)
        {
            try {
                Insert(locomotive, position);
            } catch (StorageOverflowException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void Clear(){
        _places.clear();
    }

    public void SortSet(Comparator<T> comparer)
    {
        if (comparer == null)
        {
            return;
        }
        _places.sort(comparer);
    }
    @Override
    public Iterator<T> iterator() {
        return _places.iterator();
    }
}