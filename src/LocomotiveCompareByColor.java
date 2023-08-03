import java.util.Comparator;

public class LocomotiveCompareByColor implements Comparator<IDrawningObject> {
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
        Integer xLocomotiveColor = xLocomotive.GetLocomotive().Locomotive.GetBodyColor().getRGB();
        Integer yLocomotiveColor = yLocomotive.GetLocomotive().Locomotive.GetBodyColor().getRGB();
        if (!xLocomotiveColor.equals(yLocomotiveColor))
        {
            return xLocomotiveColor.compareTo(yLocomotiveColor);
        }
        if (!xLocomotive.GetLocomotive().getClass().getName().equals(yLocomotive.GetLocomotive().getClass().getName()))
        {
            if (xLocomotive.GetLocomotive().getClass().getName().equals("DrawningLocomotive"))
            {
                return -1;
            }
            return 1;
        }
        if (xLocomotive.GetLocomotive().Locomotive instanceof EntityMonorail xMonorail && yLocomotive.GetLocomotive().Locomotive instanceof EntityMonorail yMonorail)
        {
            Integer xLocomotiveDopColor = xMonorail.GetDopColor().getRGB();
            Integer yLocomotiveDopColor = yMonorail.GetDopColor().getRGB();
            var dopColorCompare = xLocomotiveColor.compareTo(yLocomotiveColor);
            if (dopColorCompare != 0)
            {
                return dopColorCompare;
            }
        }
        Integer xSpeed = xLocomotive.GetLocomotive().Locomotive.GetSpeed();
        Integer ySpeed = yLocomotive.GetLocomotive().Locomotive.GetSpeed();
        var speedCompare = xSpeed.compareTo(ySpeed);
        if (speedCompare != 0)
        {
            return speedCompare;
        }
        Float temp3 = xLocomotive.GetLocomotive().Locomotive.GetWeight();
        Float temp4 = yLocomotive.GetLocomotive().Locomotive.GetWeight();
        return temp3.compareTo(temp4);
    }
}
