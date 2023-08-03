import java.awt.*;

public class EntityMonorail extends EntityLocomotive{
    private Color dopColor;
    public Color GetDopColor(){
        return dopColor;
    }
    private boolean magneticRail;
    public boolean GetMagneticRail(){
        return magneticRail;
    }
    private boolean secondCabin;
    public boolean GetSecondCabin(){
        return secondCabin;
    }
    public EntityMonorail(int speed, float weight, Color bodyColor, Color dopColor, boolean magneticRail, boolean secondCabin) {
        super(speed, weight, bodyColor);
        this.dopColor = dopColor;
        this.magneticRail = magneticRail;
        this.secondCabin = secondCabin;
    }
}
