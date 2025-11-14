package lt.game.traffic.entities;

import lt.game.traffic.world.SpawnLane;
import lt.game.traffic.world.WeatherStrategy;

public class VehicleFactory {
    public Vehicle createCar(SpawnLane lane, WeatherStrategy weather) {
        return new Car(lane, weather);
    }
}
