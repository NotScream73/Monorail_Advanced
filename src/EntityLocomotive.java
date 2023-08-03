import java.awt.*;
import java.util.Random;

public class EntityLocomotive {
    private int speed;
    public int GetSpeed() {
        return speed;
    }
    private float weight;
    public float GetWeight() {
        return weight;
    }
    private Color bodyColor;
    public Color GetBodyColor() {
        return bodyColor;
    }
    public float Step () {
        return speed * 100 / weight;
    }
    public EntityLocomotive(int speed, float weight, Color bodyColor)
    {
        Random rnd = new Random();
        this.speed = speed <= 0 ? 50 + rnd.nextInt(100) : speed;
        this.weight = weight <= 0 ? 50 + rnd.nextInt(100) : weight;
        this.bodyColor = bodyColor;
    }
}