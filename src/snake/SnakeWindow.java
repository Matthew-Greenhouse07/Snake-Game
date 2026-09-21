package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class SnakeWindow extends JFrame {

    public SnakeWindow(SnakePanel snakePanel) {
        // create Snake Window
        setTitle("Snake Game");
        add(snakePanel, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
