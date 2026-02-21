package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.KeyListener;

public class SnakePanel extends JPanel {

    private int snakeLength;
    private int currentX;
    private int currentY;
    private boolean activeGame;

    private Timer timer;

    final int SQUARE_LENGTH = 30;

    private int screenWidth;
    private int screenHeight;

    public SnakePanel() {
        // Toolkit retrieves system information
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDimensions = toolkit.getScreenSize();
        this.screenWidth = screenDimensions.width;
        this.screenHeight = screenDimensions.height;

        // Create the stage
        setupBackground();

        // Setup snake
        this.snakeLength = 3;
        this.currentX = screenWidth/4;
        this.currentY = screenHeight/2;

        // Setting up the listeners
        setupListeners();

        timer = new Timer(50, e -> {
            updateSnake();
            repaint();
        });

        timer.start();
    }

    protected void setupBackground() {
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 30));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GREEN);
        g2.fillRect(currentX, currentY, SQUARE_LENGTH, SQUARE_LENGTH);
    }

    protected void updateSnake() {
        currentX += 30;
    }

    protected void setupListeners() {
        updateSnake();
    }
}
