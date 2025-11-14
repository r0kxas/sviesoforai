package lt.game.traffic.entities;

import lt.game.traffic.world.SpawnLane;
import lt.game.traffic.world.WeatherStrategy;

import java.awt.*;

public class Car extends Vehicle {
    public Car(SpawnLane lane, WeatherStrategy w) { super(lane, w); }
    protected double speedMult() { return 1.0; }
    protected Color color() { return new Color(220, 80, 80); }
}
