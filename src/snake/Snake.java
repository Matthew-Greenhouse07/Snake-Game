package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Snake implements KeyListener {

    private int snakeLength;
    private int currentX;
    private int currentY;

    enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;

    private boolean activeGame;

    private Timer timer;

    public Snake() {
        this.snakeLength = 3;
        this.currentX = SnakePanel.SQUARE_LENGTH;
        this.currentY = SnakePanel.SQUARE_LENGTH;
        this.direction = Direction.RIGHT;
    }

    protected void updateSnake() {
        switch ( direction ) {
            case UP -> { currentY -= SnakePanel.SQUARE_LENGTH; }
            case DOWN -> { currentY += SnakePanel.SQUARE_LENGTH; }
            case LEFT -> { currentX -= SnakePanel.SQUARE_LENGTH; }
            case RIGHT -> { currentX += SnakePanel.SQUARE_LENGTH; }
        }
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

    protected void drawSnake(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(currentX, currentY, SnakePanel.SQUARE_LENGTH, SnakePanel.SQUARE_LENGTH);
    }

    protected boolean checkCollision() {
        return this.currentY < SnakePanel.SQUARE_LENGTH ||
               this.currentY > SnakePanel.screenHeight - SnakePanel.SQUARE_LENGTH ||
               this.currentX < SnakePanel.SQUARE_LENGTH ||
               this.currentX > SnakePanel.screenWidth - SnakePanel.SQUARE_LENGTH;
    }

    protected void resetSnake() {
        this.snakeLength = 3;
        this.currentX = SnakePanel.SQUARE_LENGTH;
        this.currentY = SnakePanel.SQUARE_LENGTH;
        this.direction = Direction.RIGHT;
    }
}