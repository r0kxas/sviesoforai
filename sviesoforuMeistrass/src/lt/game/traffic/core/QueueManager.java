package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import java.util.List;

public class QueueManager {
    public static final int JAM_LIMIT_MS = 11_000;

    public enum JamMode { AVERAGE, MAX }
    private static final JamMode JAM_MODE = JamMode.MAX;

    private long avgWaitingMs = 0;
    private long maxWaitingMs = 0;

    public void update(List<Vehicle> vehicles) {
        long sum = 0; int count = 0; long max = 0;

        for (Vehicle v : vehicles) {
            if (v.isQueuedNearIntersection()) {
                long w = v.getWaitingMs();
                sum += w;
                count++;
                if (w > max) max = w;
            }
        }

        avgWaitingMs = (count == 0) ? 0 : (sum / count);
        maxWaitingMs = max;
    }

    public boolean isJamExceeded() {
        return switch (JAM_MODE) {
            case AVERAGE -> avgWaitingMs > JAM_LIMIT_MS;
            case MAX     -> maxWaitingMs > JAM_LIMIT_MS;
        };
    }

    public boolean isAverageWaitTooHigh() { return avgWaitingMs > JAM_LIMIT_MS; }
    public boolean isJamTimeout()         { return isJamExceeded(); }

    public long getAverageWaitingMs() { return avgWaitingMs; }
    public long getMaxWaitingMs()     { return maxWaitingMs; }
    public JamMode getMode()          { return JAM_MODE; }
}
