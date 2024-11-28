package me.bruhdows.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {

    private static final int GRAVITY = 1;
    private static final int BIRD_JUMP_VELOCITY = -9;
    private static final int PIPE_SPAWN_INTERVAL = 1500;
    private static final int PIPE_VELOCITY = -4;

    private final int panelWidth;
    private final int panelHeight;

    private Bird bird;
    private List<Pipe> pipes;
    private boolean isGameOver;
    private double score;

    private Timer gameLoopTimer;
    private Timer pipeSpawnerTimer;

    private final Image backgroundImg;
    private final Image birdImg;
    private final Image topPipeImg;
    private final Image bottomPipeImg;

    public GamePanel(int width, int height) {
        this.panelWidth = width;
        this.panelHeight = height;

        // Load images
        backgroundImg = loadImage("background.png");
        birdImg = loadImage("flappyBird.png");
        topPipeImg = loadImage("topPipe.png");
        bottomPipeImg = loadImage("bottomPipe.png");

        initializeGame();

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setFocusable(true);
        addKeyListener(new GameKeyListener());
        initializeTimers();
    }

    private void initializeGame() {
        bird = new Bird(panelWidth / 8, panelHeight / 2, birdImg);
        pipes = new ArrayList<>();
        isGameOver = false;
        score = 0;
    }

    private void initializeTimers() {
        gameLoopTimer = new Timer(16, e -> {
            updateGameLogic();
            repaint();
        });
        pipeSpawnerTimer = new Timer(PIPE_SPAWN_INTERVAL, e -> spawnPipes());

        gameLoopTimer.start();
        pipeSpawnerTimer.start();
    }

    private Image loadImage(String path) {
        return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
    }

    private void spawnPipes() {
        var random = new Random();
        int openingSpace = panelHeight / 4;
        int topPipeY = -(panelHeight / 4) - random.nextInt(panelHeight / 4);

        pipes.add(new Pipe(panelWidth, topPipeY, topPipeImg));
        pipes.add(new Pipe(panelWidth, topPipeY + Pipe.PIPE_HEIGHT + openingSpace, bottomPipeImg));
    }

    private void updateGameLogic() {
        if (isGameOver) {
            gameLoopTimer.stop();
            pipeSpawnerTimer.stop();
            return;
        }

        bird.applyGravity(GRAVITY);
        pipes.forEach(pipe -> pipe.move(PIPE_VELOCITY));

        // Collision detection and scoring
        pipes.removeIf(pipe -> {
            if (pipe.isOffScreen()) {
                return true;
            }
            if (!pipe.isPassed() && bird.getX() > pipe.getX() + (pipe.getWidth() / 3)) {
                score += 0.5;
                pipe.setPassed(true);
            }
            if (bird.collidesWith(pipe)) {
                isGameOver = true;
            }
            return false;
        });

        if (bird.isOutOfBounds(panelHeight)) {
            isGameOver = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame((Graphics2D) g);
    }

    private void drawGame(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g.drawImage(backgroundImg, 0, 0, panelWidth, panelHeight, null);

        // Draw bird
        bird.draw(g);

        // Draw pipes
        pipes.forEach(pipe -> pipe.draw(g));

        // Draw score or game over message
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 40));
        String text = isGameOver ? "Game Over: " + (int) score : String.valueOf((int) score);
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (panelWidth - textWidth) / 2, 40);
    }

    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (isGameOver) {
                    initializeGame();
                    gameLoopTimer.start();
                    pipeSpawnerTimer.start();
                } else {
                    bird.jump(BIRD_JUMP_VELOCITY);
                }
            }
        }
    }
}
