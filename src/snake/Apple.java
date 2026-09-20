package snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Apple {

    private static ArrayList<Point> availableSquares = new ArrayList<>();
    private static int numApples = 1;
    private static int finalApplesEaten = 0;      // to check when a game with multiple apples ends
    private static boolean gameWon = false;

    private int currentX;
    private int currentY;  
    private boolean spawnable;      // determines if the apple has is spawnable (or if space has ran out)


    protected void spawnApple() {
        if (availableSquares.size() > 0) {
            Point current = availableSquares.get((int) (Math.random() * availableSquares.size()));
            
            currentX = (int) current.getX();
            currentY = (int) current.getY();

            // remove spawned apple coords from avaialable squares array
            updateAvailableSquares(current);

            if (!spawnable) {
                spawnable = true;
            }
        } else {
            finalApplesEaten++;
            spawnable = false;

            if (finalApplesEaten == numApples) {
                // win
                gameWon = true;
            }
        }
    }


    protected void drawApple(Graphics2D g) {
        if (spawnable) {
            g.setColor(Color.RED);
            g.fillRect(currentX + 2, currentY + 2, SnakePanel.SQUARE_LENGTH - 4, SnakePanel.SQUARE_LENGTH - 4);
        }
    }


    protected static void resetAvailableSquares(int numberOfApples) {
        // reset these static values as well at beginning of game
        numApples = numberOfApples;
        finalApplesEaten = 0;
        gameWon = false;

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


    protected static boolean isWin() {
        return gameWon;
    }


    protected int getAppleX() { return currentX; }
    protected int getAppleY() { return currentY; }


}
