import java.util.Comparator;

public class LocomotiveCompareByType implements Comparator<IDrawningObject> {

    @Override
    public int compare(IDrawningObject o1, IDrawningObject o2) {
        if (o1 == null && o2 == null)
        {
            return 0;
        }
        if (o1 == null)
        {
            return 1;
        }
        if (o2 == null)
        {
            return -1;
        }
        var xLocomotive = (DrawningObjectLocomotive)o1;
        var yLocomotive = (DrawningObjectLocomotive)o2;
        if (!xLocomotive.GetLocomotive().getClass().getName().equals(yLocomotive.GetLocomotive().getClass().getName())) {
            if (xLocomotive.GetLocomotive().getClass().getName().equals("DrawningLocomotive")) {
                return -1;
            }
            return 1;
        }
        String xStyleWheels = xLocomotive.GetLocomotive().wheels.getClass().getName();
        String yStyleWheels = yLocomotive.GetLocomotive().wheels.getClass().getName();
        int styleCompare = xStyleWheels.compareTo(yStyleWheels);
        if(styleCompare != 0){
            return styleCompare;
        }
        Integer xCountWheels = xLocomotive.GetLocomotive().wheelsCount;
        Integer yCountWheels = yLocomotive.GetLocomotive().wheelsCount;
        int countWheelsCompare = xCountWheels.compareTo(yCountWheels);
        if(countWheelsCompare != 0){
            return countWheelsCompare;
        }
        Integer xSpeed = xLocomotive.GetLocomotive().Locomotive.GetSpeed();
        Integer ySpeed = yLocomotive.GetLocomotive().Locomotive.GetSpeed();
        var speedCompare = xSpeed.compareTo(ySpeed);
        if (speedCompare != 0)
        {
            return speedCompare;
        }
        Float xWeight = xLocomotive.GetLocomotive().Locomotive.GetWeight();
        Float yWeight = yLocomotive.GetLocomotive().Locomotive.GetWeight();
        return xWeight.compareTo(yWeight);
    }
}
