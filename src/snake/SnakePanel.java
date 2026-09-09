package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.event.KeyListener;

public class SnakePanel extends JPanel implements KeyListener {

    private int snakeLength;
    private int currentX;
    private int currentY;
    private boolean activeGame;
    
    enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;

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
        this.direction = Direction.RIGHT;

        // Setting up the listeners
        setupListeners();

        // timer
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
        switch ( direction ) {
            case UP -> currentY -= 30;
            case DOWN -> currentY += 30;
            case LEFT -> currentX -= 30;
            case RIGHT -> currentX += 30;
        }
    }

    protected void setupListeners() {
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_W -> direction = Direction.UP;
            case KeyEvent.VK_S -> direction = Direction.DOWN;
            case KeyEvent.VK_A -> direction = Direction.LEFT;
            case KeyEvent.VK_D -> direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
