package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;


public class ControlsPanel extends JPanel {

    private ButtonGroup speedButtons = new ButtonGroup();
    private JRadioButton normalSpeedButton = new JRadioButton("Normal");
    private JRadioButton fastSpeedButton = new JRadioButton("Fast");
    private JRadioButton slowSpeedButton = new JRadioButton("Slow");

    private ButtonGroup mapSizeButtons = new ButtonGroup();
    private JRadioButton normalSizeButton = new JRadioButton("Normal");
    private JRadioButton smallSizeButton = new JRadioButton("Small");
    private JRadioButton largeSizeButton = new JRadioButton("Large");

    private JSlider numApplesSlider;

    private JButton btnStartGame;

    private Font font = new Font("Arial", Font.BOLD, 20);


    public ControlsPanel(SnakePanel snakePanel) {
        setupGUIElements(snakePanel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        

        add(createGroupedButtons(normalSpeedButton, fastSpeedButton, slowSpeedButton));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createGroupedButtons(normalSizeButton, smallSizeButton, largeSizeButton));
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(createLabeledSlider("Number of apples: ", numApplesSlider));
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(btnStartGame);
    }


    private void setupGUIElements(SnakePanel snakePanel) {
        speedButtons.add(normalSpeedButton);
        speedButtons.add(fastSpeedButton);
        speedButtons.add(slowSpeedButton);
        normalSpeedButton.setSelected(true);

        mapSizeButtons.add(normalSizeButton);
        mapSizeButtons.add(smallSizeButton);
        mapSizeButtons.add(largeSizeButton);
        normalSizeButton.setSelected(true);

        numApplesSlider = new JSlider(1, 5, 1);
        numApplesSlider.setMajorTickSpacing(1);
        numApplesSlider.setPaintTicks(true);
        numApplesSlider.setPaintLabels(true);

        // button and listener
        btnStartGame = new JButton("Start Game");
        btnStartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStartGame.addActionListener(e -> { snakePanel.startNewGame(); });

        // set font of elements
        normalSpeedButton.setFont(font);
        fastSpeedButton.setFont(font);
        slowSpeedButton.setFont(font);
        normalSizeButton.setFont(font);
        smallSizeButton.setFont(font);
        largeSizeButton.setFont(font);
        numApplesSlider.setFont(font);

        // make elements transparent
        normalSpeedButton.setOpaque(false);
        fastSpeedButton.setOpaque(false);
        slowSpeedButton.setOpaque(false);
        normalSizeButton.setOpaque(false);
        smallSizeButton.setOpaque(false);
        largeSizeButton.setOpaque(false);
        numApplesSlider.setOpaque(false);
    }


    private JPanel createGroupedButtons(JRadioButton button1, JRadioButton button2, JRadioButton button3) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(button1);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(button2);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(button3);
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setOpaque(false);
        return panel;
    }
    

    private JPanel createLabeledSlider(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel sliderLabel = new JLabel(label);
        sliderLabel.setFont(font);
        panel.add(sliderLabel, BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        // panel.setMaximumSize(panel.getPreferredSize());
        panel.setOpaque(false);
        return panel;
    }


    protected String getSpeed() {
        if (normalSpeedButton.isSelected()) { return "normal"; }
        else if (fastSpeedButton.isSelected()) { return "fast"; }
        else { return "slow"; }
    }


    protected String getMapSize() {
        if (normalSizeButton.isSelected()) { return "normal"; }
        else if (smallSizeButton.isSelected()) { return "small"; }
        else { return "large"; }
    }


    protected int getNumApples() {
        return numApplesSlider.getValue();
    }

}
