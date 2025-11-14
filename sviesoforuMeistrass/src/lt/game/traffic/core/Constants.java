package lt.game.traffic.core;

public final class Constants {
    private Constants() {}
    public static final int LANE_WIDTH = 70;
    public static final int ROAD_WIDTH = LANE_WIDTH * 2;
    public static final int VEHICLE_LENGTH = 46;
    public static final int VEHICLE_WIDTH = 22;
    public static final double BASE_SPEED = 2.2;
    public static final int SPAWN_INTERVAL_MS = 900;
    public static final int TICK_MS = 16;
    public static final int WIN_TIME_SEC = 120;
    public static final int JAM_QUEUE_LIMIT = 10;
    public static final int JAM_MAX_SECONDS = 12;
}
