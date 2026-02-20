package snake;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        startGUI();
    }

    public static void startGUI() {
        // Start GUI
        SnakePanel snakePanel = new SnakePanel();
        SnakeWindow snakeWindow = new SnakeWindow(snakePanel);
        snakeWindow.setVisible(true);
    }
}