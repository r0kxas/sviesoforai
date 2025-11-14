package lt.game.traffic.world.weather;

import lt.game.traffic.world.WeatherStrategy;

public class ClearWeather implements WeatherStrategy {
    public double speedFactor() { return 1.0; }
    public int brakingDistance() { return 70; }
    public String name() { return "Giedra"; }
}
