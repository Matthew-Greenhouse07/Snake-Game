package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class SnakeWindow extends JFrame {

    public SnakeWindow(SnakePanel snakePanel) {
        // Create Snake Window
        setTitle("Snake Game");
        add(snakePanel, BorderLayout.CENTER);

        // Maximise JFrame to fit entire screen
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
