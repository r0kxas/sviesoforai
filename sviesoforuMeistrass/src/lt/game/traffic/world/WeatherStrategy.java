package lt.game.traffic.world;

public interface WeatherStrategy {
    double speedFactor();
    int brakingDistance();
    String name();
}
