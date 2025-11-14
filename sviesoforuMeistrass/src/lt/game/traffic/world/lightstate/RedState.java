package lt.game.traffic.world.lightstate;

import java.awt.*;

public class RedState implements LightState {
    public LightState next() { return new GreenState(); }
    public boolean isGreenLike() { return false; }
    public Color color() { return Color.RED; }
}
