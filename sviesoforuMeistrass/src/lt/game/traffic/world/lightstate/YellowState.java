package lt.game.traffic.world.lightstate;

import java.awt.*;

public class YellowState implements LightState {
    public LightState next() { return new RedState(); }
    public boolean isGreenLike() { return false; }
    public Color color() { return Color.YELLOW; }
}
