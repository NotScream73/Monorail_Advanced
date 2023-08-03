import java.util.ArrayList;
import java.util.Random;

public class DrawningEntities <T extends EntityLocomotive, U extends IWheelsDrawingObject> {
    private ArrayList<T> entityArr;
    private ArrayList<U> wheelArr;
    int entitiesCount = 0;
    int wheelsCount = 0;
    int maxEntityCount;
    int maxWheelsCount;

    public DrawningEntities(int countEntities, int countWheels) {
        entityArr = new ArrayList<>(countEntities);
        wheelArr =  new ArrayList<>(countWheels);
        maxEntityCount = countEntities;
        maxWheelsCount = countWheels;
    }

    public void Insert(T entityLocomotive) {
        if(entitiesCount< maxEntityCount){
            entityArr.add(entityLocomotive);
            entitiesCount++;
        }
    }

    public void Insert (U wheel) {
        if(wheelsCount< maxWheelsCount){
            wheelArr.add(wheel);
            wheelsCount++;
        }
    }

    public DrawningLocomotive getEntity() {
        Random random = new Random();
        int randomEntityIndex = random.nextInt(entitiesCount);
        int randomWheelsIndex = random.nextInt(wheelsCount);
        T locomotive = entityArr.get(randomEntityIndex);
        U wheel = wheelArr.get(randomWheelsIndex);
        if (locomotive instanceof EntityMonorail) {
            return new DrawningMonorail(locomotive, wheel);
        }
        return new DrawningLocomotive(locomotive, wheel);
    }
}
