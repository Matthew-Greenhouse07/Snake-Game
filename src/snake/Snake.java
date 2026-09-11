package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Snake implements KeyListener {

    private int snakeLength;
    protected ArrayList<Point> coords = new ArrayList<>();

    private int currentX;
    private int currentY;

    enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;


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

        coords.add(new Point(currentX, currentY));
        
        if (!appleEaten(apple)) { coords.remove(0); }
    }


    protected void resetSnake() {
        this.snakeLength = 3;
        this.coords.clear();
        this.currentX = SnakePanel.BORDER_SIZE + 2*SnakePanel.SQUARE_LENGTH;
        this.currentY = SnakePanel.BORDER_SIZE;

        coords.add(new Point(SnakePanel.BORDER_SIZE, SnakePanel.BORDER_SIZE));
        coords.add(new Point(SnakePanel.BORDER_SIZE + SnakePanel.SQUARE_LENGTH, SnakePanel.BORDER_SIZE));
        coords.add(new Point(this.currentX, this.currentY));
        this.direction = Direction.RIGHT;
    }


    protected void drawSnake(Graphics2D g) {
        g.setColor(Color.GREEN);
        for (Point point : this.coords) {
            g.fillRect((int)point.getX() + 2, (int)point.getY() + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);
        }
    }


    protected boolean checkCollision() {
        return this.currentY < SnakePanel.BORDER_SIZE ||
               this.currentY > SnakePanel.screenHeight - SnakePanel.BORDER_SIZE ||
               this.currentX < SnakePanel.BORDER_SIZE ||
               this.currentX > SnakePanel.screenWidth - SnakePanel.BORDER_SIZE ||
               coords.subList(0, coords.size() - 1).contains(new Point(this.currentX, this.currentY));
    }


    protected boolean appleEaten(Apple apple) {
        return ((apple.getAppleX() == this.currentX) && (apple.getAppleY() == this.currentY));
    }


//#region "key events"
    @Override
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_W -> {
                if (direction != direction.DOWN) { direction = Direction.UP; }}
            case KeyEvent.VK_S -> {
                if (direction != direction.UP) { direction = Direction.DOWN; }}
            case KeyEvent.VK_A -> {
                if (direction != direction.RIGHT) { direction = Direction.LEFT; }}
            case KeyEvent.VK_D -> {
                if (direction != direction.LEFT) { direction = Direction.RIGHT; }}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
//#endregion
    
}