package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class SnakePanel extends JPanel implements KeyListener {

    protected static int SQUARE_LENGTH;
    protected static final int BORDER_SIZE = 20;
    protected static final int SCREEN_WIDTH = 1200;
    protected static final int SCREEN_HEIGHT = 900;

    private boolean activeGame;
    private Timer timer;
    private Snake snake;
    private ArrayList<Apple> apples = new ArrayList<>();
    private HashMap<String, Integer> highScores = new HashMap<>();  // String part will be concatenation of 
                                                                    // speed -> map size -> num apples -> true/false (boolean obstacles)
    private boolean obstacles;

    private JPanel menu;
    private ControlsPanel controlsPanel;

    private JPanel pnlGameOverButtons;
    private JButton btnStartAgain;
    private JButton btnMenu;

    private JLabel lblTitle;
    private JLabel lblWin;

    private JPanel pnlScoreLabels;
    private JLabel lblHighScore;
    private JLabel lblCurrScore;


    public SnakePanel() {
        // preferred panel size (screen width is the playing area and doesnt include borders)
        setPreferredSize(new Dimension(SCREEN_WIDTH + 2*BORDER_SIZE, SCREEN_HEIGHT + 2*BORDER_SIZE));

        // display GUI elements vertically (elements which are horizontal are contained in their own panel of a different layout)
        setLayout(new BorderLayout());

        // set up everything
        activeGame = false;
        setupBackground();

        addKeyListener(this);
        addButtonsAndLabels();
        addButtonListeners();

        setupMenu();

        setFocusable(true);

        snake = new Snake();
        mainMenu();
    }


    protected void mainMenu() {
        activeGame = false;
        pnlGameOverButtons.setVisible(false);
        pnlScoreLabels.setVisible(false);
        lblWin.setVisible(false);
        menu.setVisible(true);

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

        // draw obstacles if any
        if (obstacles) {
            g2.setColor(Color.GRAY);
            for (Point obstacle : Obstacle.obstacleCoords) {
                g.fillRect((int) obstacle.getX() + 2, (int) obstacle.getY() + 2, SQUARE_LENGTH - 4, SQUARE_LENGTH - 4);
            }
        }
    }
    

    protected void startNewGame() {
        activeGame = true;

        pnlGameOverButtons.setVisible(false);
        pnlScoreLabels.setVisible(true);
        lblWin.setVisible(false);
        menu.setVisible(false);

        String speed = controlsPanel.getSpeed();
        String mapSize = controlsPanel.getMapSize();
        int numApples = controlsPanel.getNumApples();
        obstacles = controlsPanel.getObstaclesEnabled();

        lblCurrScore.setText("Current Score: 0");

        // high score
        String highScoreString = speed + mapSize + String.valueOf(numApples) + String.valueOf(obstacles);
        Integer currHighScore = highScores.get(highScoreString);                        
        if (currHighScore != null) {
            lblHighScore.setText("High Score: " + currHighScore);
        } else {
            lblHighScore.setText("High Score: 0");
        }

        switch (mapSize) {
            case ("normal") -> { SQUARE_LENGTH = 100; }
            case ("small") -> { SQUARE_LENGTH = 150; }
            case ("large") -> { SQUARE_LENGTH = 75; }
        }

        for (int i=0; i < numApples; i++) {
            apples.add(new Apple());
        }

        snake.resetSnake();

        if (obstacles) {
            Obstacle.initiateObstacles(controlsPanel.getObstacleDifficulty());
        }
        
        Apple.resetAvailableSquares(numApples, obstacles);
        for (Apple apple : apples) {
            apple.spawnApple();
        }

        switch (speed) {
            case ("normal") -> { setupRefreshRate(100); }
            case ("fast") -> { setupRefreshRate(50); }
            case ("slow") -> { setupRefreshRate(150); }
        }
    }


    protected void gameOver() {
        activeGame = false;

        // check for highscore
        String highScoreString = controlsPanel.getSpeed() + controlsPanel.getMapSize() +
                                String.valueOf(controlsPanel.getNumApples() + String.valueOf(controlsPanel.getObstaclesEnabled()));
        Integer currHighScore = highScores.get(highScoreString);
        int score = snake.getSnakeScore();
        if (currHighScore == null || (score > currHighScore)) {
            // add or replace high score
            highScores.put(highScoreString, snake.getSnakeScore());
        }

        if (Apple.isWin()) {
            lblWin.setVisible(true);
        }

        pnlGameOverButtons.setVisible(true);
        apples.clear();
        revalidate();
    }


    protected void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);

        int currX = BORDER_SIZE - 1;
        while (currX < SCREEN_WIDTH - BORDER_SIZE) {
            g.fillRect(currX, BORDER_SIZE, 4, SCREEN_HEIGHT - BORDER_SIZE);
            currX += SQUARE_LENGTH;
        }

        int currY = BORDER_SIZE - 1;
        while (currY < SCREEN_HEIGHT - BORDER_SIZE) {
            g.fillRect(BORDER_SIZE, currY, SCREEN_WIDTH - BORDER_SIZE, 4);
            currY += SQUARE_LENGTH;
        }
    }


//#region "set up events"
    protected void setupBackground() {
        setBackground(new Color(10, 10, 10));
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), BORDER_SIZE));
    }

    protected void setupRefreshRate(int rate) {
        // prevent several timers running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(rate, e -> {
            if (activeGame) {
                snake.updateSnake(apples);

                if (snake.appleEaten(apples)) {
                    Apple apple = snake.getEatenApple(apples);

                    if (apple != null) {
                        apple.spawnApple();
                    }

                    int currScore = snake.getSnakeScore();
                    lblCurrScore.setText("Current Score: " + currScore);

                    // update high score label if high score
                    String lblHighScoreText = lblHighScore.getText();
                    int currHighScore = Integer.parseInt(lblHighScoreText.substring(lblHighScoreText.length() - 1));  // always int
                    if (currScore > currHighScore) {
                        lblHighScore.setText("High Score: " + currScore);
                    }
                }

                if (snake.checkCollision(obstacles)) {
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
        // pressing Enter starts a new game
        if ((!activeGame) && (e.getKeyCode() == KeyEvent.VK_ENTER)) { startNewGame(); }
        else { snake.keyPressed(e); }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}


    private void setupMenu() {
        menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setOpaque(false);

        lblTitle = new JLabel("Snake");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 250));
        lblTitle.setForeground(Color.GREEN);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(lblTitle);

        menu.add(Box.createRigidArea(new Dimension(0, 30)));

        controlsPanel = new ControlsPanel(this);
        controlsPanel.setMaximumSize(controlsPanel.getPreferredSize());
        controlsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(controlsPanel);

        add(menu, BorderLayout.CENTER);
    }


    private void addButtonsAndLabels() {
        pnlScoreLabels = new JPanel();
        pnlScoreLabels.setLayout(new FlowLayout());
        pnlScoreLabels.setOpaque(false);
        
        lblHighScore = new JLabel("High Score: 0");
        lblHighScore.setForeground(Color.WHITE);
        pnlScoreLabels.add(lblHighScore);
        pnlScoreLabels.add(Box.createRigidArea(new Dimension(20, 0)));
        lblCurrScore = new JLabel("Current Score: 0");
        lblCurrScore.setForeground(Color.WHITE);
        pnlScoreLabels.add(lblCurrScore);
        pnlScoreLabels.setMaximumSize(pnlScoreLabels.getPreferredSize());
        add(pnlScoreLabels, BorderLayout.PAGE_START);

        pnlGameOverButtons = new JPanel();
        pnlGameOverButtons.setLayout(new FlowLayout());
        pnlGameOverButtons.setOpaque(false);

        btnStartAgain = new JButton("Start Again?");
        pnlGameOverButtons.add(btnStartAgain);
        pnlGameOverButtons.add(Box.createRigidArea(new Dimension(20, 0)));
        btnMenu = new JButton("Menu");
        pnlGameOverButtons.add(btnMenu);
        pnlGameOverButtons.setMaximumSize(pnlGameOverButtons.getPreferredSize());
        add(pnlGameOverButtons, BorderLayout.PAGE_END);

        lblWin = new JLabel("You Win!");
        lblWin.setFont(new Font("Arial", Font.BOLD, 100));
        lblWin.setForeground(Color.YELLOW);
        add(lblWin);
    }

    protected void addButtonListeners() {
        btnStartAgain.addActionListener(e -> { startNewGame(); });
        btnMenu.addActionListener(e -> { mainMenu(); });
    }
    
//#endregion

}
