package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class CollisionDetector {
    public boolean hasCollision(List<Vehicle> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle va = vehicles.get(i);
            Rectangle2D a = va.getBounds();
            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle vb = vehicles.get(j);
                if (va.getLane().axis() == vb.getLane().axis()) continue;
                if (a.intersects(vb.getBounds())) return true;
            }
        }
        return false;
    }
}
