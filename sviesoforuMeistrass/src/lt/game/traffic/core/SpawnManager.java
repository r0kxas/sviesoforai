package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import lt.game.traffic.entities.VehicleFactory;
import lt.game.traffic.world.SpawnLane;
import lt.game.traffic.world.WeatherStrategy;

import java.util.List;
import java.util.Random;

public class SpawnManager {

    private final Random rng = new Random();
    private long lastSpawnMs = 0;

    // Needed for R restart
    public void reset() {
        lastSpawnMs = 0;
    }

    public void spawn(List<Vehicle> vehicles, VehicleFactory factory,
                      WeatherStrategy weather, long elapsedMs) {

        while (elapsedMs - lastSpawnMs >= Constants.SPAWN_INTERVAL_MS) {
            lastSpawnMs += Constants.SPAWN_INTERVAL_MS;

            SpawnLane lane = SpawnLane.values()[rng.nextInt(SpawnLane.values().length)];

            Vehicle v = factory.createCar(lane, weather); // only regular cars

            if (canSpawn(lane, vehicles)) {
                vehicles.add(v);
            }
        }
    }

    private boolean canSpawn(SpawnLane lane, List<Vehicle> vehicles) {
        return vehicles.stream()
                .filter(v -> v.getLane() == lane)
                .noneMatch(v -> v.distanceToSpawn() < 70);
    }
}
