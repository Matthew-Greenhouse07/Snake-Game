package snake;

import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {

    private int snakeLength;

    public SnakePanel() {
        this.snakeLength = 3;
        JPanel panel = new JPanel();
        setupSnake(panel);
        panel.setBackground(Color.green);
    }

    protected void setupSnake(JPanel panel) {
        panel.add(new JLabel("Hey " + snakeLength));
    }
}
