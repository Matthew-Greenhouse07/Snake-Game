package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;

public class SnakePanel extends JPanel implements KeyListener {

    private boolean activeGame;
    private Timer timer;
    private Snake snake;
    private JButton btnStartAgain = new JButton("Start Again?");
    protected static final int SQUARE_LENGTH = 100;
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
        setupRefreshRate(100);
        addKeyListener(this);
        setFocusable(true);

        btnStartAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        this.add(btnStartAgain);
        btnStartAgain.setVisible(false);

        this.snake = new Snake();
        startNewGame();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        snake.drawSnake(g2);
    }
    
    private void startNewGame() {
        this.activeGame = true;
        btnStartAgain.setVisible(false);
        snake.resetSnake();
        timer.start();
    }

    protected void gameOver() {
        this.activeGame = false;
        btnStartAgain.setVisible(true);
        revalidate();
    }

//#region "set up events"
    protected void setupBackground() {
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), SQUARE_LENGTH));
    }

    protected void setupRefreshRate(int rate) {
        timer = new Timer(rate, e -> {
            if (activeGame) {
                snake.updateSnake();
                if (snake.checkCollision()) { gameOver(); }
                else { repaint(); }
            } else {
                timer.stop();
            }
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
