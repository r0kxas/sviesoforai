package lt.game.traffic.world;

public enum SpawnLane {
    NORTH(Axis.VERTICAL, 0, 1),
    SOUTH(Axis.VERTICAL, 0, -1),
    WEST (Axis.HORIZONTAL, 1, 0),
    EAST (Axis.HORIZONTAL, -1, 0);

    public enum Axis { VERTICAL, HORIZONTAL }

    private final Axis axis;
    private final int dx;
    private final int dy;

    SpawnLane(Axis axis, int dx, int dy) { this.axis = axis; this.dx = dx; this.dy = dy; }
    public Axis axis() { return axis; }
    public int dx() { return dx; }
    public int dy() { return dy; }
}
