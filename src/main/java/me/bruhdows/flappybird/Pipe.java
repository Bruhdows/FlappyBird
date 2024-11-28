package me.bruhdows.flappybird;

import java.awt.*;

public class Pipe {
    public static final int PIPE_WIDTH = 64;
    public static final int PIPE_HEIGHT = 512;

    private int x, y;
    private final Image image;
    private boolean passed;

    public Pipe(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.passed = false;
    }

    public void move(int velocity) {
        x += velocity;
    }

    public boolean isOffScreen() {
        return x + PIPE_WIDTH < 0;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, PIPE_WIDTH, PIPE_HEIGHT, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return PIPE_WIDTH;
    }

    public int getHeight() {
        return PIPE_HEIGHT;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
