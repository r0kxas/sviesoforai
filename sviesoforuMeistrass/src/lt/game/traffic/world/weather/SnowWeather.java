package lt.game.traffic.world.weather;

import lt.game.traffic.world.WeatherStrategy;

public class SnowWeather implements WeatherStrategy {
    public double speedFactor() { return 0.65; }
    public int brakingDistance() { return 120; }
    public String name() { return "Sniegas"; }
}
