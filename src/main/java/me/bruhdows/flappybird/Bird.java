package me.bruhdows.flappybird;

import java.awt.*;

public class Bird {
    private static final int BIRD_WIDTH = 34;
    private static final int BIRD_HEIGHT = 24;

    private int x, y;
    private int velocity;
    private final Image image;

    public Bird(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.velocity = 0;
    }

    public void applyGravity(int gravity) {
        velocity += gravity;
        y += velocity;
    }

    public void jump(int jumpVelocity) {
        velocity = jumpVelocity;
    }

    public boolean isOutOfBounds(int panelHeight) {
        return y < 0 || y > panelHeight;
    }

    public boolean collidesWith(Pipe pipe) {
        return x < pipe.getX() + pipe.getWidth() &&
                x + BIRD_WIDTH > pipe.getX() &&
                y < pipe.getY() + pipe.getHeight() &&
                y + BIRD_HEIGHT > pipe.getY();
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, BIRD_WIDTH, BIRD_HEIGHT, null);
    }

    public int getX() {
        return x;
    }
}
