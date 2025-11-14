package lt.game.traffic.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity implements Updatable {
    protected double x, y;
    protected int w, h;

    public Rectangle2D getBounds() { return new Rectangle2D.Double(x - w/2.0, y - h/2.0, w, h); }
    public abstract void render(Graphics2D g, Dimension size);
}
