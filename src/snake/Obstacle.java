package snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Obstacle {

    protected static ArrayList<Point> obstacleCoords = new ArrayList<>();
    private static double obstacleMultiplier = 0;


    protected static void initiateObstacles(String obstacleDifficulty) {
        obstacleCoords.clear();
        
        if (obstacleDifficulty.equals("low")) { obstacleMultiplier = 0.0625; }    // every 1 in 16 squares
        else if (obstacleDifficulty.equals("medium")) { obstacleMultiplier = 0.084; }     // ~ every 1 in 12 squares
        else { obstacleMultiplier = 0.125; }  // every 1 in 8 squares

        ArrayList<Point> availableSquares = new ArrayList<>();

        final int BORDER_SIZE = SnakePanel.BORDER_SIZE;
        final int SQUARE_LENGTH = SnakePanel.SQUARE_LENGTH;
        final int SCREEN_WIDTH = SnakePanel.SCREEN_WIDTH;
        final int SCREEN_HEIGHT = SnakePanel.SCREEN_HEIGHT;

        // initialise availableSquares array
        int currX = BORDER_SIZE;
        int currY;

        while (currX < SCREEN_WIDTH - (BORDER_SIZE)) {
            currY = BORDER_SIZE;

            while (currY < SCREEN_HEIGHT - (BORDER_SIZE)) {
                availableSquares.add(new Point(currX, currY));
                currY += SQUARE_LENGTH;
            }

            currX += SQUARE_LENGTH;
        }

        // obstacles will not be able to spawn on squares around corners or first x squares in the path of the snake
        // top left corner
        availableSquares.remove(new Point(BORDER_SIZE, BORDER_SIZE));
        availableSquares.remove(new Point(BORDER_SIZE + SQUARE_LENGTH, BORDER_SIZE));
        availableSquares.remove(new Point(BORDER_SIZE, BORDER_SIZE + SQUARE_LENGTH));
        // top right corner
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - SQUARE_LENGTH, BORDER_SIZE));
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - (2 * SQUARE_LENGTH), BORDER_SIZE));
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - SQUARE_LENGTH, BORDER_SIZE + SQUARE_LENGTH));
        // bottom left corner
        availableSquares.remove(new Point(BORDER_SIZE, SCREEN_HEIGHT + BORDER_SIZE - SQUARE_LENGTH));
        availableSquares.remove(new Point(BORDER_SIZE, SCREEN_HEIGHT + BORDER_SIZE - (2 * SQUARE_LENGTH)));
        availableSquares.remove(new Point(BORDER_SIZE + SQUARE_LENGTH, SCREEN_HEIGHT + BORDER_SIZE - SQUARE_LENGTH));
        // bottom right corner
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - SQUARE_LENGTH, SCREEN_HEIGHT + BORDER_SIZE - SQUARE_LENGTH));
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - (2 * SQUARE_LENGTH), SCREEN_HEIGHT + BORDER_SIZE - SQUARE_LENGTH));
        availableSquares.remove(new Point(SCREEN_WIDTH + BORDER_SIZE - SQUARE_LENGTH, SCREEN_HEIGHT + BORDER_SIZE - (2 * SQUARE_LENGTH)));
        // snake trajectory
        availableSquares.remove(new Point(BORDER_SIZE + (2 * SQUARE_LENGTH), BORDER_SIZE));
        availableSquares.remove(new Point(BORDER_SIZE + (3 * SQUARE_LENGTH), BORDER_SIZE));
        availableSquares.remove(new Point(BORDER_SIZE + (4 * SQUARE_LENGTH), BORDER_SIZE));
        availableSquares.remove(new Point(BORDER_SIZE + (5 * SQUARE_LENGTH), BORDER_SIZE));

        int numObstacles = (int) Math.round(availableSquares.size() * obstacleMultiplier);

        for (int i=0; i < numObstacles; i++) {
            int randIndex = (int) (Math.random() * availableSquares.size());
            // add random available square to obstacle coords
            obstacleCoords.add(availableSquares.get(randIndex));
            availableSquares.remove(randIndex);
        }
    }

}