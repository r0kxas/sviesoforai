package lt.game.traffic.core;

import java.awt.*;

public class RoadRenderer {
    public static void draw(Graphics2D g, Dimension size) {
        int cx = size.width/2, cy = size.height/2;
        g.setColor(new Color(40,40,40));
        g.fillRect(cx - Constants.ROAD_WIDTH/2, 0, Constants.ROAD_WIDTH, size.height);
        g.fillRect(0, cy - Constants.ROAD_WIDTH/2, size.width, Constants.ROAD_WIDTH);

        g.setColor(Color.YELLOW);
        g.drawLine(cx - Constants.LANE_WIDTH/2, 0, cx - Constants.LANE_WIDTH/2, size.height);
        g.drawLine(cx + Constants.LANE_WIDTH/2, 0, cx + Constants.LANE_WIDTH/2, size.height);
        g.drawLine(0, cy - Constants.LANE_WIDTH/2, size.width, cy - Constants.LANE_WIDTH/2);
        g.drawLine(0, cy + Constants.LANE_WIDTH/2, size.width, cy + Constants.LANE_WIDTH/2);
    }
}

