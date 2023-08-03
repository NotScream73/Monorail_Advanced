import java.awt.*;
import java.lang.reflect.Field;

public class ExtentionLocomotive {
    private static final char _separatorForObject = ':';
    public static DrawningLocomotive CreateDrawningLocomotive(String info)
    {
        String[] strs = info.split(String.valueOf(_separatorForObject));
        IWheelsDrawingObject pattern = null;
        switch (strs[3])
        {
            case "DrawningWheels":
                pattern = new DrawningWheels();
                break;
            case "DrawningRectWheels":
                pattern = new DrawningRectWheels();
                break;
            case "DrawningTriangleWheels":
                pattern = new DrawningTriangleWheels();
                break;
        }
        if (strs.length == 5)
        {
            var temp = new DrawningLocomotive(Integer.parseInt(strs[0]),
                    Float.parseFloat(strs[1]), new Color(Integer.parseInt(strs[2])));
            temp.SetWheels(Integer.parseInt(strs[4]), pattern);
            return temp;
        }
        if (strs.length == 8)
        {
            var temp = new DrawningMonorail(Integer.parseInt(strs[0]),
                    Float.parseFloat(strs[1]), new Color(Integer.parseInt(strs[2])),
                    new Color(Integer.parseInt(strs[5])), Boolean.parseBoolean(strs[6]),
                    Boolean.parseBoolean(strs[7]));
            temp.SetWheels(Integer.parseInt(strs[4]), pattern);
            return temp;
        }
        return null;
    }
    public static String GetDataForSave(DrawningLocomotive drawningLocomotive)
    {
        var locomotive = drawningLocomotive.Locomotive;
        var str = "" + locomotive.GetSpeed() + _separatorForObject + locomotive.GetWeight() + _separatorForObject + Integer.toString(locomotive.GetBodyColor().getRGB()) + _separatorForObject + drawningLocomotive.wheels.getClass().getName() + _separatorForObject + drawningLocomotive.wheelsCount;
        if (!(locomotive instanceof EntityMonorail monorail))
        {
            return str;
        }
        return "" + str + _separatorForObject + Integer.toString(monorail.GetDopColor().getRGB()) + _separatorForObject + monorail.GetMagneticRail() + _separatorForObject + monorail.GetSecondCabin();
    }
}