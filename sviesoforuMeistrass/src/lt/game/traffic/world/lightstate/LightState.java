package lt.game.traffic.world.lightstate;

import java.awt.*;

public interface LightState {
    LightState next();
    boolean isGreenLike();
    Color color();
}
