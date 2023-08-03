import java.awt.*;
import java.util.Objects;

public class DrawningObjectLocomotive implements IDrawningObject {
    private DrawningLocomotive _locomotive;

    public DrawningLocomotive GetLocomotive(){
        return _locomotive;
    }

    public DrawningObjectLocomotive(DrawningLocomotive  locomotive){
        _locomotive = locomotive;
    }
    public float GetStep(){
        if (_locomotive.Locomotive != null){
            return _locomotive.Locomotive.Step();
        }
        return 0;
    }
    public void SetObject(int x, int y, int width, int height) {
        _locomotive.SetPosition(x, y, width, height);
    }

    public void MoveObject(Direction direction) {
        if (_locomotive != null){
            _locomotive.MoveTransport(direction);
        }
    }

    public void DrawningObject(Graphics g) {
        _locomotive.DrawTransport(g);
    }

    public float[] GetCurrentPosition() {
        if (_locomotive != null){
            return _locomotive.GetCurrentPosition();
        }
        return null;
    }

    @Override
    public String GetInfo() {
        if (_locomotive != null){
            return ExtentionLocomotive.GetDataForSave(_locomotive);
        }
        return null;
    }

    public DrawningLocomotive GetDrawingObjectLocomotive() {
        return _locomotive;
    }

    public static IDrawningObject Create(String data){
        return new DrawningObjectLocomotive(ExtentionLocomotive.CreateDrawningLocomotive(data));
    }
    @Override
    public boolean equals(Object other){
        if (other == null)
        {
            return false;
        }
        var otherLocomotive = (DrawningObjectLocomotive)other;
        if (otherLocomotive == null)
        {
            return false;
        }
        var locomotive = _locomotive.Locomotive;
        var otherLocomotiveLocomotive = otherLocomotive._locomotive.Locomotive;
        if (!Objects.equals(locomotive.getClass().getName(), otherLocomotiveLocomotive.getClass().getName()))
        {
            return false;
        }
        if (locomotive.GetSpeed() != otherLocomotiveLocomotive.GetSpeed())
        {
            return false;
        }
        if (locomotive.GetWeight() != otherLocomotiveLocomotive.GetWeight())
        {
            return false;
        }
        if (!Objects.equals(locomotive.GetBodyColor().toString(), otherLocomotiveLocomotive.GetBodyColor().toString()))
        {
            return false;
        }
        if (!_locomotive.wheels.getClass().getName().equals(otherLocomotive._locomotive.wheels.getClass().getName())){
            return false;
        }
        if (_locomotive.wheelsCount != otherLocomotive._locomotive.wheelsCount){
            return false;
        }
        if (locomotive instanceof EntityMonorail monorail && otherLocomotiveLocomotive instanceof EntityMonorail otherMonorail)
        {
            if (!Objects.equals(monorail.GetDopColor().toString(), otherMonorail.GetDopColor().toString()))
            {
                return false;
            }
            if (monorail.GetMagneticRail() != otherMonorail.GetMagneticRail())
            {
                return false;
            }
            if (monorail.GetSecondCabin() != otherMonorail.GetSecondCabin())
            {
                return false;
            }
        }
        return true;
    }
}
