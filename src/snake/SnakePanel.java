package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements KeyListener {

    private boolean activeGame;
    private Timer timer;
    private Snake snake;
    protected static final int SQUARE_LENGTH = 50;
    protected static int screenWidth;
    protected static int screenHeight;

    public SnakePanel() {
        // Toolkit retrieves system information
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDimensions = toolkit.getScreenSize();
        this.screenWidth = ((int) screenDimensions.width / SQUARE_LENGTH) * SQUARE_LENGTH;
        this.screenHeight = ((int) screenDimensions.height / SQUARE_LENGTH) * SQUARE_LENGTH;

        // set up everything
        setupBackground();
        setupRefreshRate(50);
        addKeyListener(this);
        setFocusable(true);

        this.snake = new Snake();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        snake.updateSnake();
        snake.drawSnake(g2);
    }
    

//#region "set up events"
    protected void setupBackground() {
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), SQUARE_LENGTH));
    }

    protected void setupRefreshRate(int rate) {
        timer = new Timer(rate, e -> {
            repaint();
        });

        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.snake.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
//#endregion

}
