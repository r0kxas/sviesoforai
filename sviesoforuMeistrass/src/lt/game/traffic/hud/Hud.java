package lt.game.traffic.hud;

import lt.game.traffic.core.Constants;
import lt.game.traffic.world.WeatherStrategy;

import java.awt.*;

public class Hud {
    public static void render(Graphics2D g, Dimension size, long elapsedMs, WeatherStrategy weather, boolean running, String gameOver) {
        g.setColor(new Color(255,255,255,200));
        g.fillRoundRect(12, 12, 280, 76, 12, 12);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Laikas: " + (elapsedMs/1000) + " s / tikslas: " + Constants.WIN_TIME_SEC + " s", 24, 36);
        g.drawString("Oras: " + weather.name(), 24, 54);
        g.drawString("Q: N–S fazė, W: W–E fazė, R: restart", 24, 72);
        if (!running && gameOver != null) {
            drawGameOver(g, size, gameOver);
        }
    }
    private static void drawGameOver(Graphics2D g, Dimension size, String text) {
        g.setColor(new Color(0,0,0,160));
        g.fillRect(0,0,size.width,size.height);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 28f));
        g.drawString(text, 40, size.height/2 - 10);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 18f));
        g.drawString("Spauskite R – pradėti iš naujo", 40, size.height/2 + 20);
    }
}
