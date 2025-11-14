package lt.game.traffic.ui;

import lt.game.traffic.core.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel() {
        setPreferredSize(new Dimension(960, 640));
        setFocusable(true);
        setBackground(new Color(30, 120, 30));
        game = new Game(getPreferredSize());
        addKeyListener(game.getInputHandler());
    }

    public void start() { game.start(this::repaint); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render((Graphics2D) g, getSize());
    }
}
