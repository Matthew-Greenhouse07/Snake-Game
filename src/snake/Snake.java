package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Snake implements KeyListener {

    private int snakeLength;
    private ArrayList<Point> coords = new ArrayList<>();

    private int currentX;
    private int currentY;

    enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;

    private boolean hasMoved = true;


    public Snake() {
        resetSnake();
    }


    protected void updateSnake(Apple apple) {
        switch ( direction ) {
            case UP -> { currentY -= SnakePanel.SQUARE_LENGTH; }
            case DOWN -> { currentY += SnakePanel.SQUARE_LENGTH; }
            case LEFT -> { currentX -= SnakePanel.SQUARE_LENGTH; }
            case RIGHT -> { currentX += SnakePanel.SQUARE_LENGTH; }
        }

        hasMoved = true;

        Point current = new Point(currentX, currentY);
        coords.add(current);
        
        if (appleEaten(apple)) {
            apple.updateAvailableSquares(current);
            snakeLength++;
        } else {
            Point snakeLeft = new Point(coords.get(0));
            apple.updateAvailableSquares(current, snakeLeft);
            coords.remove(0);
        }
    }


    protected void resetSnake() {
        snakeLength = 3;
        coords.clear();
        currentX = SnakePanel.BORDER_SIZE + 2*SnakePanel.SQUARE_LENGTH;
        currentY = SnakePanel.BORDER_SIZE;

        coords.add(new Point(SnakePanel.BORDER_SIZE, SnakePanel.BORDER_SIZE));
        coords.add(new Point(SnakePanel.BORDER_SIZE + SnakePanel.SQUARE_LENGTH, SnakePanel.BORDER_SIZE));
        coords.add(new Point(currentX, currentY));
        direction = Direction.RIGHT;
    }


    protected void drawSnake(Graphics2D g) {
        g.setColor(Color.GREEN);
        for (Point point : coords) {
            g.fillRect((int)point.getX() + 2, (int)point.getY() + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);
        }
    }


    protected boolean checkCollision() {
        return currentY < SnakePanel.BORDER_SIZE ||
               currentY >= SnakePanel.screenHeight - SnakePanel.BORDER_SIZE ||
               currentX < SnakePanel.BORDER_SIZE ||
               currentX >= SnakePanel.screenWidth - SnakePanel.BORDER_SIZE ||
               coords.subList(0, coords.size() - 1).contains(new Point(currentX, currentY));
    }


    protected boolean appleEaten(Apple apple) {
        return ((apple.getAppleX() == currentX) && (apple.getAppleY() == currentY));
    }


//#region "key events"
    @Override
    public void keyPressed(KeyEvent e) {
        if (hasMoved) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    if (direction != direction.DOWN) { direction = Direction.UP; }}
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    if (direction != direction.UP) { direction = Direction.DOWN; }}
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                    if (direction != direction.RIGHT) { direction = Direction.LEFT; }}
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                    if (direction != direction.LEFT) { direction = Direction.RIGHT; }}
            }

            hasMoved = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
//#endregion
    
}
