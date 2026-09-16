package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class SnakePanel extends JPanel implements KeyListener {

    private boolean activeGame;
    private Timer timer;
    private Snake snake;
    private ArrayList<Apple> apples = new ArrayList<>();

    private ControlsPanel controlsPanel;

    private JButton btnStartAgain;
    private JButton btnMenu;

    protected static int SQUARE_LENGTH;
    protected static final int BORDER_SIZE = 20;
    protected static int screenWidth = 1200;
    protected static int screenHeight = 900;


    public SnakePanel() {
        // preferred panel size (screen width is the playing area and doesnt include borders)
        setPreferredSize(new Dimension(screenWidth + 2*BORDER_SIZE, screenHeight + 2*BORDER_SIZE));

        // set up everything
        activeGame = false;
        setupBackground();

        controlsPanel = new ControlsPanel();
        add(controlsPanel);

        addKeyListener(this);
        addButtons();
        addButtonListeners();

        setFocusable(true);

        snake = new Snake();
        mainMenu();
    }


    protected void mainMenu() {
        activeGame = false;
        btnStartAgain.setVisible(true);
        btnMenu.setVisible(false);
        controlsPanel.setVisible(true);

        revalidate();
        repaint();
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (activeGame) {
            drawBackground(g2);
            snake.drawSnake(g2);
            for (Apple apple : apples) {
                apple.drawApple(g2);
            }
        }
    }
    

    private void startNewGame() {
        activeGame = true;

        btnStartAgain.setVisible(false);
        btnMenu.setVisible(false);
        controlsPanel.setVisible(false);

        String mapSize = controlsPanel.getMapSize();
        switch (mapSize) {
            case ("normal") -> { SQUARE_LENGTH = 100; }
            case ("small") -> { SQUARE_LENGTH = 150; }
            case ("large") -> { SQUARE_LENGTH = 75; }
        }

        int numApples = controlsPanel.getNumApples();
        for (int i=0; i < numApples; i++) {
            apples.add(new Apple());
        }

        snake.resetSnake();
        
        Apple.resetAvailableSquares();
        for (Apple apple : apples) {
            apple.spawnApple();
        }

        String speed = controlsPanel.getSpeed();
        switch (speed) {
            case ("normal") -> { setupRefreshRate(100); }
            case ("fast") -> { setupRefreshRate(50); }
            case ("slow") -> { setupRefreshRate(150); }
        }
    }


    protected void gameOver() {
        activeGame = false;
        btnStartAgain.setVisible(true);
        btnMenu.setVisible(true);
        apples.clear();
        revalidate();
    }


    protected void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);

        int currX = BORDER_SIZE - 1;
        while (currX < screenWidth - BORDER_SIZE) {
            g.fillRect(currX, BORDER_SIZE, 4, screenHeight - BORDER_SIZE);
            currX += SQUARE_LENGTH;
        }

        int currY = BORDER_SIZE - 1;
        while (currY < screenHeight - BORDER_SIZE) {
            g.fillRect(BORDER_SIZE, currY, screenWidth - BORDER_SIZE, 4);
            currY += SQUARE_LENGTH;
        }
    }


//#region "set up events"
    protected void setupBackground() {
        setBackground(new Color(10, 10, 10));
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), BORDER_SIZE));
    }

    protected void setupRefreshRate(int rate) {
        timer = new Timer(rate, e -> {
            if (activeGame) {
                snake.updateSnake(apples);
                if (snake.appleEaten(apples)) {
                    Apple apple = snake.getEatenApple(apples);
                    if (apple != null) {
                        apple.spawnApple();
                    }
                }
                if (snake.checkCollision()) {
                    gameOver();
                } else {
                    repaint();
                }
            } else {
                timer.stop();
            }
        });

        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        snake.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}


    protected void addButtons() {
        btnStartAgain = new JButton("Start Again?");
        add(btnStartAgain);
        btnMenu = new JButton("Menu");
        add(btnMenu);
    }

    protected void addButtonListeners() {
        btnStartAgain.addActionListener(e -> { startNewGame(); });
        btnMenu.addActionListener(e -> { mainMenu(); });
    }
    
//#endregion

}
