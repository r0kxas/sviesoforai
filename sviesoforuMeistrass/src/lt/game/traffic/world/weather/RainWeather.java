package lt.game.traffic.world.weather;

import lt.game.traffic.world.WeatherStrategy;

public class RainWeather implements WeatherStrategy {
    public double speedFactor() { return 0.8; }
    public int brakingDistance() { return 95; }
    public String name() { return "Lietus"; }
}
