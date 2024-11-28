package me.bruhdows.flappybird;

import javax.swing.*;

public class GameMain extends JFrame {

    public static final int WINDOW_WIDTH = 360;
    public static final int WINDOW_HEIGHT = 640;

    public GameMain() {
        super("Flappy Bird");

        // Set up the frame
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Add the game panel
        var gamePanel = new GamePanel(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(gamePanel);
        pack();

        gamePanel.requestFocus();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMain::new);
    }
}
