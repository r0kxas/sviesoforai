package lt.game.traffic.entities;

import lt.game.traffic.core.Constants;
import lt.game.traffic.world.SpawnLane;
import lt.game.traffic.world.WeatherStrategy;

import java.awt.*;

public abstract class Vehicle extends Entity {
    protected final SpawnLane lane;
    protected final WeatherStrategy weather;
    protected final double maxSpeed;
    protected double speed;

    private boolean inIntersection = false;

    private long waitingMs = 0;

    private static final int STOP_BUFFER_PX = 25;

    public Vehicle(SpawnLane lane, WeatherStrategy weather) {
        this.lane = lane; this.weather = weather;
        this.w = Constants.VEHICLE_WIDTH; this.h = Constants.VEHICLE_LENGTH;
        this.maxSpeed = Constants.BASE_SPEED * weather.speedFactor() * speedMult();
        this.speed = this.maxSpeed;
        placeAtSpawn();
    }

    protected abstract double speedMult();

    private void placeAtSpawn() {
        int cx = 480, cy = 320;
        int off = 360; // off-screen
        double laneOffset = Constants.LANE_WIDTH / 4.0; // lane center
        switch (lane) {
            case NORTH -> { x = cx - laneOffset; y = cy - off; }           // top -> down
            case SOUTH -> { x = cx + laneOffset; y = cy + off; }           // bottom -> up
            case WEST  -> { x = cx - off; y = cy + laneOffset; rotate(); } // left -> right
            case EAST  -> { x = cx + off; y = cy - laneOffset; rotate(); } // right -> left
        }
    }

    private void rotate() { int t = w; w = h; h = t; }

    private double signedDistanceToStopLine() {
        double cx = 480, cy = 320;
        double half = Constants.ROAD_WIDTH / 2.0;
        return switch (lane) {
            case NORTH -> (cy - half) - y;
            case SOUTH -> y - (cy + half);
            case WEST  -> (cx - half) - x;
            case EAST  -> x - (cx + half);
        };
    }

    public void update(boolean greenForLane) {
        if (!inIntersection && signedDistanceToStopLine() <= 0) {
            inIntersection = true;
        }

        int stopDist = Math.min(weather.brakingDistance(), STOP_BUFFER_PX);

        boolean shouldStop = !greenForLane
                && signedDistanceToStopLine() > 0
                && !inIntersection
                && signedDistanceToStopLine() < stopDist;

        speed = shouldStop ? 0 : maxSpeed;

        x += lane.dx() * speed;
        y += lane.dy() * speed;
    }

    public void stopThisTick() { this.speed = 0; }

    public void snapBehind(Vehicle front, int gapPx) {
        switch (lane) {
            case NORTH -> this.y = front.centerY() - (front.height()/2.0 + this.height()/2.0 + gapPx);
            case SOUTH -> this.y = front.centerY() + (front.height()/2.0 + this.height()/2.0 + gapPx);
            case WEST  -> this.x = front.centerX() - (front.width()/2.0 + this.width()/2.0 + gapPx);
            case EAST  -> this.x = front.centerX() + (front.width()/2.0 + this.width()/2.0 + gapPx);
        }
        this.speed = 0;
    }

    public void accumulateWaitingIfQueued(long deltaMs) {
        if (isQueuedNearIntersection()) waitingMs += deltaMs;
        else waitingMs = 0;
    }

    public long getWaitingMs() { return waitingMs; }

    public void render(Graphics2D g, Dimension size) {
        g.setColor(color());
        g.fill(getBounds());
    }

    protected abstract Color color();

    public boolean isOutOfBounds() {
        return x < -100 || x > sizeW() + 100 || y < -100 || y > sizeH() + 100;
    }

    private int sizeW() { return 960; }
    private int sizeH() { return 640; }

    public double distanceToIntersection() {
        double cx = 480, cy = 320;
        return switch (lane) {
            case NORTH -> Math.abs((cy - Constants.ROAD_WIDTH/2.0) - y);
            case SOUTH -> Math.abs((cy + Constants.ROAD_WIDTH/2.0) - y);
            case WEST  -> Math.abs((cx - Constants.ROAD_WIDTH/2.0) - x);
            case EAST  -> Math.abs((cx + Constants.ROAD_WIDTH/2.0) - x);
        };
    }

    public double distanceToSpawn() {
        double cx = 480, cy = 320; double off = 360;
        return switch (lane) {
            case NORTH -> Math.abs((cy - off) - y);
            case SOUTH -> Math.abs((cy + off) - y);
            case WEST  -> Math.abs((cx - off) - x);
            case EAST  -> Math.abs((cx + off) - x);
        };
    }

    public boolean isQueuedNearIntersection() {
        double d = signedDistanceToStopLine();
        return speed == 0 && d > 0 && d < 160;
    }

    public SpawnLane getLane() { return lane; }

    public double centerX() { return x; }
    public double centerY() { return y; }
    public int width() { return w; }
    public int height() { return h; }
}
