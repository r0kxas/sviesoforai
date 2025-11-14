package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import lt.game.traffic.world.SpawnLane;
import lt.game.traffic.world.TrafficLight;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;


public class VehicleManager {
    private static final int FOLLOW_GAP = 12;
    private final List<Vehicle> vehicles = new ArrayList<>();

    public List<Vehicle> getVehicles() { return vehicles; }
    public void clear() { vehicles.clear(); }

    public void updateAll(TrafficLight ns, TrafficLight we) {
        EnumMap<SpawnLane, List<Vehicle>> byLane = new EnumMap<>(SpawnLane.class);
        for (SpawnLane l : SpawnLane.values()) byLane.put(l, new ArrayList<>());
        for (Vehicle v : vehicles) byLane.get(v.getLane()).add(v);

        for (SpawnLane lane : SpawnLane.values()) {
            List<Vehicle> list = byLane.get(lane);
            if (list.isEmpty()) continue;

            list.sort(getComparatorForLane(lane));
            Vehicle leader = list.get(0);
            leader.update(isGreenForLane(lane, ns, we));

            for (int i = 1; i < list.size(); i++) {
                Vehicle prev = list.get(i - 1);
                Vehicle cur  = list.get(i);

                if (gapTooSmall(prev, cur, lane)) cur.stopThisTick();
                else cur.update(isGreenForLane(lane, ns, we));

                double currentGap = switch (lane.axis()) {
                    case VERTICAL -> Math.abs(prev.centerY() - cur.centerY()) - (prev.height()/2.0 + cur.height()/2.0);
                    case HORIZONTAL -> Math.abs(prev.centerX() - cur.centerX()) - (prev.width()/2.0 + cur.width()/2.0);
                };
                if (currentGap < FOLLOW_GAP) cur.snapBehind(prev, FOLLOW_GAP);
            }
        }
        vehicles.removeIf(Vehicle::isOutOfBounds);
    }

    public void renderAll(Graphics2D g, Dimension size) {
        for (Vehicle v : vehicles) v.render(g, size);
    }

    private Comparator<Vehicle> getComparatorForLane(SpawnLane lane) {
        return switch (lane) {
            case NORTH -> Comparator.comparingDouble(Vehicle::centerY).reversed();
            case SOUTH -> Comparator.comparingDouble(Vehicle::centerY);
            case WEST  -> Comparator.comparingDouble(Vehicle::centerX).reversed();
            case EAST  -> Comparator.comparingDouble(Vehicle::centerX);
        };
    }

    private boolean isGreenForLane(SpawnLane lane, TrafficLight ns, TrafficLight we) {
        return switch (lane.axis()) {
            case VERTICAL -> ns.isGreenLike();
            case HORIZONTAL -> we.isGreenLike();
        };
    }

    private boolean gapTooSmall(Vehicle front, Vehicle back, SpawnLane lane) {
        return switch (lane.axis()) {
            case VERTICAL -> Math.abs(front.centerY() - back.centerY())
                    - (front.height()/2.0 + back.height()/2.0) < FOLLOW_GAP;
            case HORIZONTAL -> Math.abs(front.centerX() - back.centerX())
                    - (front.width()/2.0 + back.width()/2.0) < FOLLOW_GAP;
        };
    }
}
