package snake;

import javax.swing.*;
import java.awt.*;

public class Apple {

    private int currentX;
    private int currentY;

    protected void spawnApple() {
        int randX = (int)(Math.random() * (SnakePanel.screenWidth - (2*SnakePanel.BORDER_SIZE))) + SnakePanel.BORDER_SIZE;
        int randY = (int)(Math.random() * (SnakePanel.screenHeight - (2*SnakePanel.BORDER_SIZE))) + SnakePanel.BORDER_SIZE;

        this.currentX = randX - (randX % SnakePanel.SQUARE_LENGTH) + SnakePanel.BORDER_SIZE;
        this.currentY = randY - (randY % SnakePanel.SQUARE_LENGTH) + SnakePanel.BORDER_SIZE;
    }

    protected void drawApple(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(currentX + 2, currentY + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);
    }

    protected int getAppleX() { return this.currentX; }
    protected int getAppleY() { return this.currentY; }

}