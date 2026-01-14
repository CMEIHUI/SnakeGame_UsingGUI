package hellofx;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class GameScreen {


    
    private static int score = 0;
    private static Snake snake;
    private static Food food;
    private static List<Obstacle> obstacles;
    private static final Random rnd = new Random();
    private static boolean gameOverHandled = false;

    // particles for eat effect (simple)
    private static final List<Particle> particles = new ArrayList<>();

    public static Scene create(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Platform.runLater(() -> Audio.playSound("eat"));

        Label scoreLabel = new Label("Score: 0");
        scoreLabel.getStyleClass().add("score-label");
        root.setTop(scoreLabel);

        Canvas canvas = new Canvas(GameConfig.WIDTH * GameConfig.CELL, GameConfig.HEIGHT * GameConfig.CELL);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        Scene sc = new Scene(root);
        var css = Main.class.getResource("/CSS/GameScreen.css");
        if (css != null) sc.getStylesheets().add(css.toExternalForm());

        initGameObjects();

        score = 0;
        scoreLabel.setText("Score: 0");

        // input
        sc.setOnKeyPressed(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.UP) snake.setDirection(0, -1);
            else if (c == KeyCode.DOWN) snake.setDirection(0, 1);
            else if (c == KeyCode.LEFT) snake.setDirection(-1, 0);
            else if (c == KeyCode.RIGHT) snake.setDirection(1, 0);
            else if (c == KeyCode.ESCAPE) stage.setScene(MenuScreen.create(stage));
            else if (c == KeyCode.SPACE) paused = !paused;
        });

        final long[] lastTick = {0};
        final double targetMs = GameRules.speed();
        final double[] accumulator = {0};

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTick[0] == 0) {
                    lastTick[0] = now;
                    render(gc, scoreLabel);
                    return;
                }
                double deltaMs = (now - lastTick[0]) / 1_000_000.0;
                lastTick[0] = now;

                if (paused) {
                    render(gc, scoreLabel);
                    return;
                }

                accumulator[0] += deltaMs;
                // step while to avoid skipped ticks when slow
                while (accumulator[0] >= targetMs) {
                    accumulator[0] -= targetMs;
                    boolean alive = snake.move(GameConfig.WIDTH, GameConfig.HEIGHT, GameRules.canWrap(), obstacles);
if (!alive && !gameOverHandled) {

    Audio.playSound("gameover.wav");
    gameOverHandled = true;

    System.out.println("Saving score: " + score); // DEBUG
    
    Leaderboard.addScore(score);   // ✅ SAVE ONCE

    stop();

    Platform.runLater(() -> {
        Stage popup = new Stage();
        popup.setTitle("GAME OVER");

    BorderPane root = new BorderPane();
    root.getStyleClass().add("game-over-root");

    Label header = new Label("GAME OVER");
    header.getStyleClass().add("game-over-header");
    header.setAlignment(Pos.CENTER);
    root.setTop(header);
    BorderPane.setAlignment(header, Pos.CENTER);

    Label scoreLabel = new Label("Final Score: " + score);
    scoreLabel.getStyleClass().add("final-score");
    scoreLabel.setAlignment(Pos.CENTER);
    root.setCenter(scoreLabel);
    BorderPane.setAlignment(scoreLabel, Pos.CENTER);

    HBox buttons = new HBox(20);
    buttons.setAlignment(Pos.CENTER);
    Button playAgain = new Button("Play Again");
    Button backHome = new Button("Back to Homepage");
    Button quit = new Button("Quit");

    playAgain.getStyleClass().add("play-again");
    backHome.getStyleClass().add("back-home");
    quit.getStyleClass().add("quit");

    buttons.getChildren().addAll(playAgain, backHome, quit);
    root.setBottom(buttons);
    BorderPane.setAlignment(buttons, Pos.CENTER);

    playAgain.setOnAction(e -> {
        popup.close();
        stage.setScene(GameScreen.create(stage));
    });

    backHome.setOnAction(e -> {
        popup.close();
        stage.setScene(MenuScreen.create(stage));
    });

    quit.setOnAction(e -> {
        popup.close();
        stage.close();
    });

    Scene scene = new Scene(root, 400, 250); // fixed size: square-ish
    scene.getStylesheets().add(getClass().getResource("/CSS/GameScreen.css").toExternalForm());
    popup.setScene(scene);
    popup.setResizable(false);
    popup.initOwner(stage);
    popup.show();
});

    return;
}


                    // eat?
                    if (snake.getHead().equals(food.getPoint())) {
                        snake.grow();
                        Audio.playSound("eat.wav");
                        Audio.playSound("eat.wav");   // or "click", "gameover"
                        score += 10 * GameRules.scoreMultiplier();
                        spawnParticlesAt(food.getX(), food.getY());
                        placeFood(); // pick new food
                    }
                }
                updateParticles(deltaMs);
                render(gc, scoreLabel);
            }
        };

        timer.start();
        return sc;
    }

    // Pause flag
    private static boolean paused = false;

    private static void initGameObjects() {
        gameOverHandled = false;
        snake = new Snake(GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2);
        food = new Food(5, 5);
        obstacles = new ArrayList<>();
        if (GameRules.hasObstacles()) {
            int attempts = 0;
            int placed = 0;
            while (placed < GameConfig.START_OBSTACLES && attempts < 1000) {
                attempts++;
                int ox = rnd.nextInt(GameConfig.WIDTH);
                int oy = rnd.nextInt(GameConfig.HEIGHT);
                boolean ok = !snake.occupies(ox, oy) && (food == null || (ox != food.getX() || oy != food.getY()))
                        && obstacles.stream().noneMatch(o -> o.getX() == ox && o.getY() == oy);
                if (ok) {
                    obstacles.add(new Obstacle(ox, oy));
                    placed++;
                }
            }
        }
        // ensure food not on snake/obstacle
        placeFood();
    }

        private static void placeFood() {
    int fx, fy;
    int attempts = 0;

    do {
        fx = rnd.nextInt(GameConfig.WIDTH);
        fy = rnd.nextInt(GameConfig.HEIGHT);
        attempts++;

        if (attempts > 2000) break;

    } while (snake.occupies(fx, fy) || isOnObstacle(fx, fy));

    food = new Food(fx, fy);
}

private static boolean isOnObstacle(int x, int y) {
    for (Obstacle o : obstacles) {
        if (o.getX() == x && o.getY() == y) {
            return true;
        }
    }
    return false;
}

    // particles
    private static void spawnParticlesAt(int gx, int gy) {
        particles.clear();
        int px = gx * GameConfig.CELL + GameConfig.CELL / 2;
        int py = gy * GameConfig.CELL + GameConfig.CELL / 2;
        for (int i = 0; i < 12; i++) {
            particles.add(new Particle(px, py));
        }
    }

    private static void updateParticles(double deltaMs) {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update(deltaMs);
            if (p.life <= 0) it.remove();
        }
    }

    private static void render(GraphicsContext gc, Label scoreLabel) {
        // background gradient
        gc.setFill(Color.web("#000000ff"));
        gc.fillRect(0, 0, GameConfig.WIDTH * GameConfig.CELL, GameConfig.HEIGHT * GameConfig.CELL);

        for (int y = 0; y < GameConfig.HEIGHT; y++) {
            double a = 0.04 + 0.02 * (y % 2);
            gc.setFill(Color.rgb(255,255,255, a));
            // subtle horizontal stripes
            gc.fillRect(0, y * GameConfig.CELL, GameConfig.WIDTH * GameConfig.CELL, 1);
        }

        // grid faint
        gc.setStroke(Color.web("#000000ff"));
        for (int x = 0; x <= GameConfig.WIDTH; x++) gc.strokeLine(x * GameConfig.CELL, 0, x * GameConfig.CELL, GameConfig.HEIGHT * GameConfig.CELL);
        for (int y = 0; y <= GameConfig.HEIGHT; y++) gc.strokeLine(0, y * GameConfig.CELL, GameConfig.WIDTH * GameConfig.CELL, y * GameConfig.CELL);

        // obstacles
        gc.setFill(Color.GRAY);
        for (Obstacle o : obstacles) {
            gc.fillRoundRect(o.getX() * GameConfig.CELL + 2, o.getY() * GameConfig.CELL +2, GameConfig.CELL -4, GameConfig.CELL -4, 6, 6);
        }

        // food (glowing)
        gc.setFill(Color.GOLD);
        gc.fillOval(food.getX() * GameConfig.CELL + 4, food.getY() * GameConfig.CELL + 4, GameConfig.CELL - 8, GameConfig.CELL - 8);

        // snake
        boolean first = true;
        List<Point> body = snake.getBody();
        for (Point p : body) {
            if (first) {
                // head style depends on skin
                Color headColor = switch (SettingsScreen.selectedSkin) {
                    case NEON -> Color.web("#00ffcc");
                    case RETRO -> Color.web("#FFD166");
                    case DARK -> Color.web("#66ff66");
                    case LIGHT -> Color.web("#7CFC00");
                    default -> Color.web("#00FF66");
                };
                gc.setFill(headColor);
                first = false;
            } else {
                Color bodyColor = switch (SettingsScreen.selectedSkin) {
                    case NEON -> Color.web("#00ff88");
                    case RETRO -> Color.web("#FFB26B");
                    case DARK -> Color.web("#1f7a1f");
                    case LIGHT -> Color.web("#8CE99A");
                    default -> Color.LIME;
                };
                gc.setFill(bodyColor);
            }
            gc.fillRoundRect(p.x * GameConfig.CELL + 2, p.y * GameConfig.CELL + 2, GameConfig.CELL - 4, GameConfig.CELL - 4, 8, 8);
        }

        // particles
        for (Particle p : particles) {
            gc.setGlobalAlpha(Math.max(0, Math.min(1, p.life / p.maxLife)));
            gc.fillOval(p.x - 3, p.y - 3, 6, 6);
            gc.setGlobalAlpha(1.0);
        }

        // HUD: score glow
        scoreLabel.setText("Score: " + score + (paused ? "  (PAUSED)" : ""));
    }

    // Simple particle class
    private static class Particle {
        double x, y;
        double vx, vy;
        double life;
        final double maxLife = 400; // ms
        Particle(double x, double y) {
            this.x = x; this.y = y;
            double ang = rnd.nextDouble() * Math.PI * 2;
            double sp = 30 + rnd.nextDouble() * 60;
            vx = Math.cos(ang) * sp;
            vy = Math.sin(ang) * sp;
            life = maxLife;
        }
        void update(double deltaMs) {
            double dt = deltaMs / 1000.0;
            x += vx * dt;
            y += vy * dt;
            life -= deltaMs;
        }
    }
}
