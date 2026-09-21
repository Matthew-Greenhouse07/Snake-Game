package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Snake implements KeyListener {

    private int snakeScore;
    private ArrayList<Point> coords = new ArrayList<>();

    private int currentX;
    private int currentY;

    enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;

    private boolean hasMoved = true;


    public Snake() {
        resetSnake();
    }


    protected void updateSnake(ArrayList<Apple> apples) {
        switch ( direction ) {
            case UP -> { currentY -= SnakePanel.SQUARE_LENGTH; }
            case DOWN -> { currentY += SnakePanel.SQUARE_LENGTH; }
            case LEFT -> { currentX -= SnakePanel.SQUARE_LENGTH; }
            case RIGHT -> { currentX += SnakePanel.SQUARE_LENGTH; }
        }

        hasMoved = true;

        Point current = new Point(currentX, currentY);
        coords.add(current);
        
        if (appleEaten(apples)) {
            Apple.updateAvailableSquares(current);
            snakeScore++;
        } else {
            Point snakeLeft = new Point(coords.get(0));
            Apple.updateAvailableSquares(current, snakeLeft);
            coords.remove(0);
        }
    }


    protected void resetSnake() {
        snakeScore = 0;
        coords.clear();
        currentX = SnakePanel.BORDER_SIZE + 2*SnakePanel.SQUARE_LENGTH;
        currentY = SnakePanel.BORDER_SIZE;

        coords.add(new Point(SnakePanel.BORDER_SIZE, SnakePanel.BORDER_SIZE));
        coords.add(new Point(SnakePanel.BORDER_SIZE + SnakePanel.SQUARE_LENGTH, SnakePanel.BORDER_SIZE));
        coords.add(new Point(currentX, currentY));
        direction = Direction.RIGHT;
    }


    protected void drawSnake(Graphics2D g) {
        int colorNum = 255;
        for (int i = coords.size() - 1; i >= 0; i--) {
            Point point = coords.get(i);
            g.setColor(new Color(0, colorNum, 0));
            g.fillRect((int)point.getX() + 2, (int)point.getY() + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);

            if (colorNum > 0) {
                colorNum -= 2;
            }
        }
    }


    protected boolean checkCollision(boolean obstacles) {
        if (!obstacles) {
            return currentY < SnakePanel.BORDER_SIZE ||
               currentY >= SnakePanel.SCREEN_HEIGHT - SnakePanel.BORDER_SIZE ||
               currentX < SnakePanel.BORDER_SIZE ||
               currentX >= SnakePanel.SCREEN_WIDTH - SnakePanel.BORDER_SIZE ||
               // self collision
               coords.subList(0, coords.size() - 1).contains(new Point(currentX, currentY));
        } else {
            Point curr = new Point(currentX, currentY);
            return currentY < SnakePanel.BORDER_SIZE ||
               currentY >= SnakePanel.SCREEN_HEIGHT - SnakePanel.BORDER_SIZE ||
               currentX < SnakePanel.BORDER_SIZE ||
               currentX >= SnakePanel.SCREEN_WIDTH - SnakePanel.BORDER_SIZE ||
               // self collision
               coords.subList(0, coords.size() - 1).contains(curr) ||
               // obstacles
               Obstacle.obstacleCoords.contains(curr);
        }
    }


    protected boolean appleEaten(ArrayList<Apple> apples) {
        for (Apple apple : apples) {
            if ((apple.getAppleX() == currentX) && (apple.getAppleY() == currentY)) {
                return true;
            }
        }
            
        return false;
    }


    protected Apple getEatenApple(ArrayList<Apple> apples) {
        for (Apple apple : apples) {
            if ((apple.getAppleX() == currentX) && (apple.getAppleY() == currentY)) {
                return apple;
            }
        }

        return null;
    }


    protected int getSnakeScore() {
        return snakeScore;
    }


//#region "key events"
    @Override
    public void keyPressed(KeyEvent e) {
        if (hasMoved) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    if (direction != direction.DOWN) { direction = Direction.UP; hasMoved = false;}}
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    if (direction != direction.UP) { direction = Direction.DOWN; hasMoved = false;}}
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                    if (direction != direction.RIGHT) { direction = Direction.LEFT; hasMoved = false;}}
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                    if (direction != direction.LEFT) { direction = Direction.RIGHT; hasMoved = false;}}
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
//#endregion
    
}
