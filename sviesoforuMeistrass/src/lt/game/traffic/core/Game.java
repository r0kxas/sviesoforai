package lt.game.traffic.core;

import lt.game.traffic.entities.Vehicle;
import lt.game.traffic.entities.VehicleFactory;
import lt.game.traffic.hud.Hud;
import lt.game.traffic.world.TrafficLight;
import lt.game.traffic.world.WeatherStrategy;
import lt.game.traffic.world.weather.ClearWeather;
import lt.game.traffic.world.weather.RainWeather;
import lt.game.traffic.world.weather.SnowWeather;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    private final InputHandler input = new InputHandler();
    private final CollisionDetector collisionDetector = new CollisionDetector();
    private final QueueManager queueManager = new QueueManager();
    private final Random rng = new Random();

    private final VehicleFactory vehicleFactory = new VehicleFactory();
    private final VehicleManager vehicleManager = new VehicleManager();
    private final SpawnManager spawnManager = new SpawnManager();
    private final GameLogic logic = new GameLogic();

    private final TrafficLight nsLight = new TrafficLight();
    private final TrafficLight weLight = new TrafficLight();
    private WeatherStrategy weather = new ClearWeather();

    private javax.swing.Timer loop;
    private long elapsedMs = 0;
    private long lastTickTime = 0;
    private boolean running = false;
    private String gameOverMsg = null;

    public Game(Dimension size) {
        input.setOnKey(this::onKey);
    }

    public void start(Runnable repaint) {
        reset();
        loop = new javax.swing.Timer(Constants.TICK_MS, e -> {
            if (running) {
                step();
                repaint.run();
            }
        });
        loop.start();
    }

    private void reset() {
        vehicleManager.clear();
        elapsedMs = 0;
        lastTickTime = 0;
        gameOverMsg = null;
        running = true;

        nsLight.setRed();
        weLight.setGreen();

        spawnManager.reset();

        weather = switch (rng.nextInt(3)) {
            case 1 -> new RainWeather();
            case 2 -> new SnowWeather();
            default -> new ClearWeather();
        };
    }

    private void onKey(char c) {
        if (c == 'Q') nsLight.next();
        if (c == 'W') weLight.next();
        // allow restart at any time
        if (c == 'R') reset();
    }

    private void step() {
        long now = System.currentTimeMillis();
        if (lastTickTime == 0) lastTickTime = now;
        long deltaMs = now - lastTickTime;
        elapsedMs += deltaMs;
        lastTickTime = now;

        spawnManager.spawn(vehicleManager.getVehicles(), vehicleFactory, weather, elapsedMs);
        vehicleManager.updateAll(nsLight, weLight);
        for (Vehicle v : vehicleManager.getVehicles()) {
            v.accumulateWaitingIfQueued(deltaMs);
        }

        queueManager.update(vehicleManager.getVehicles());
        logic.evaluate(this, vehicleManager.getVehicles(), collisionDetector, queueManager, elapsedMs);
    }

    public void endGame(String message) {
        running = false;
        gameOverMsg = message;
    }

    public void render(Graphics2D g, Dimension size) {
        RoadRenderer.draw(g, size);
        nsLight.render(g, size, true);
        weLight.render(g, size, false);
        vehicleManager.renderAll(g, size);
        Hud.render(g, size, elapsedMs, weather, running, gameOverMsg);
    }

    public InputHandler getInputHandler() {
        return input;
    }
}
