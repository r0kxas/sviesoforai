package lt.game.traffic.world.lightstate;

import java.awt.*;

public class GreenState implements LightState {
    public LightState next() { return new YellowState(); }
    public boolean isGreenLike() { return true; }
    public Color color() { return Color.GREEN; }
}
