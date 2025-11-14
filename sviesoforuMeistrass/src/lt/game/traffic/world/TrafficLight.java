package lt.game.traffic.world;

import lt.game.traffic.world.lightstate.*;

import java.awt.*;

public class TrafficLight {
    private LightState state = new RedState();

    public void next() { state = state.next(); }
    public boolean isGreenLike() { return state.isGreenLike(); }
    public void setRed() { state = new RedState(); }
    public void setGreen() { state = new GreenState(); }

    public void render(Graphics2D g, Dimension size, boolean vertical) {
        int cx = size.width/2, cy = size.height/2;
        int x = vertical ? cx + 45 : cx + 45;
        int y = vertical ? cy + 50 : cy - 100;
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x, y, 18, 48, 6, 6);
        g.setColor(Color.RED); g.fillOval(x+3, y+3, 12, 12);
        g.setColor(Color.YELLOW); g.fillOval(x+3, y+18, 12, 12);
        g.setColor(Color.GREEN); g.fillOval(x+3, y+33, 12, 12);
        g.setColor(state.color());
        int oy = state instanceof RedState ? 3 : state instanceof YellowState ? 18 : 33;
        g.drawOval(x+2, y+oy-1, 14, 14);
    }
}
