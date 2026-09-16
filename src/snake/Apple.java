package snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Apple {

    private int currentX;
    private int currentY;
    private static ArrayList<Point> availableSquares = new ArrayList<>();


    protected void spawnApple() {
        if (availableSquares.size() > 0) {
            Point current = availableSquares.get((int) (Math.random() * availableSquares.size()));
            
            currentX = (int) current.getX();
            currentY = (int) current.getY();

            // remove spawned apple coords from avaialable squares array
            updateAvailableSquares(current);
        } else {
            //____________________________
            //
            // needs fixing
            // can win before finishing all apples (if several are present)
            //_____________________________

            System.out.println("You Win!");
        }
    }


    protected void drawApple(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(currentX + 2, currentY + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);
    }


    protected static void resetAvailableSquares() {
        availableSquares.clear();

        // Initialise availableSquares array
        int currX = SnakePanel.BORDER_SIZE;
        int currY;

        while (currX < SnakePanel.screenWidth - SnakePanel.BORDER_SIZE) {
            currY = SnakePanel.BORDER_SIZE;

            while (currY < SnakePanel.screenHeight - SnakePanel.BORDER_SIZE) {
                availableSquares.add(new Point(currX, currY));
                currY += SnakePanel.SQUARE_LENGTH;
            }

            currX += SnakePanel.SQUARE_LENGTH;
        }

        // initial coords of the snake
        availableSquares.remove(new Point(SnakePanel.BORDER_SIZE, SnakePanel.BORDER_SIZE));
        availableSquares.remove(new Point(SnakePanel.BORDER_SIZE + SnakePanel.SQUARE_LENGTH, SnakePanel.BORDER_SIZE));
        availableSquares.remove(new Point(SnakePanel.BORDER_SIZE + 2*SnakePanel.SQUARE_LENGTH, SnakePanel.BORDER_SIZE));
    }


    protected static void updateAvailableSquares(Point snakeEntered, Point snakeLeft) {
        availableSquares.remove(snakeEntered);
        availableSquares.add(snakeLeft);
    }

    protected static void updateAvailableSquares(Point takenSquare) {
        // could be snake entering point or apple spawned at point
        availableSquares.remove(takenSquare);
    }


    protected int getAppleX() { return currentX; }
    protected int getAppleY() { return currentY; }

}
