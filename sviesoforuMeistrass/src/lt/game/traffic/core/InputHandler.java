package lt.game.traffic.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class InputHandler extends KeyAdapter {
    private Consumer<Character> onKey;
    public void setOnKey(Consumer<Character> onKey) { this.onKey = onKey; }

    @Override
    public void keyPressed(KeyEvent e) {
        if (onKey != null) {
            char c = Character.toUpperCase(e.getKeyChar());
            if (c == 'Q' || c == 'W' || c == 'R') onKey.accept(c);
        }
    }
}
