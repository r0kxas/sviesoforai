package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import java.util.List;

public class GameLogic {
    public void evaluate(Game game, List<Vehicle> vehicles, CollisionDetector detector,
                         QueueManager queue, long elapsedMs) {

        if (detector.hasCollision(vehicles)) {
            game.endGame("Avarija! Susidūrė automobiliai.");
            return;
        }
        if (queue.isJamExceeded()) {
            String modeText = (queue.getMode() == QueueManager.JamMode.MAX)
                    ? "maksimalus laukimas" : "vidutinis laukimas";
            game.endGame("Per ilga spūstis – " + modeText + " virš "
                    + (QueueManager.JAM_LIMIT_MS / 1000) + " s.");
            return;
        }
        if (elapsedMs / 1000 >= Constants.WIN_TIME_SEC) {
            game.endGame("Pergalė! Išlaikėte eismą " + (elapsedMs / 1000) + " s.");
        }
    }
}
