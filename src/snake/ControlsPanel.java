package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;


public class ControlsPanel extends JPanel {

    private ButtonGroup speedButtons;
    private JRadioButton normalSpeedButton;
    private JRadioButton fastSpeedButton;
    private JRadioButton slowSpeedButton;

    private ButtonGroup mapSizeButtons;
    private JRadioButton normalSizeButton;
    private JRadioButton smallSizeButton;
    private JRadioButton largeSizeButton;

    private JSlider numApplesSlider;

    private JToggleButton enableObstaclesButton;
    private ButtonGroup obstacleModeButtons;
    private JRadioButton lowObstaclesButton;
    private JRadioButton mediumObstaclesButton;
    private JRadioButton highObstaclesButton;

    private JButton btnStartGame;

    private Font font = new Font("Arial", Font.BOLD, 20);


    public ControlsPanel(SnakePanel snakePanel) {
        initialiseGUIElements();
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

        add(createLabeledToggleableButtons("Obstacles enabled: ", enableObstaclesButton,
                    lowObstaclesButton, mediumObstaclesButton, highObstaclesButton));
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(btnStartGame);
    }


    private void initialiseGUIElements() {
        speedButtons = new ButtonGroup();
        normalSpeedButton = new JRadioButton("Normal");
        fastSpeedButton = new JRadioButton("Fast");
        slowSpeedButton = new JRadioButton("Slow");

        mapSizeButtons = new ButtonGroup();
        normalSizeButton = new JRadioButton("Normal");
        smallSizeButton = new JRadioButton("Small");
        largeSizeButton = new JRadioButton("Large");

        numApplesSlider = new JSlider(1, 5, 1);

        enableObstaclesButton = new JToggleButton("Off", false);
        obstacleModeButtons = new ButtonGroup();
        lowObstaclesButton = new JRadioButton("Low");
        mediumObstaclesButton = new JRadioButton("Medium");
        highObstaclesButton = new JRadioButton("High");
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

        numApplesSlider.setMajorTickSpacing(1);
        numApplesSlider.setPaintTicks(true);
        numApplesSlider.setPaintLabels(true);

        // start game button and listener
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
        lowObstaclesButton.setOpaque(false);
        mediumObstaclesButton.setOpaque(false);
        highObstaclesButton.setOpaque(false);

        // obstacle toggleable button and options
        obstacleModeButtons.add(lowObstaclesButton);
        obstacleModeButtons.add(mediumObstaclesButton);
        obstacleModeButtons.add(highObstaclesButton);
        lowObstaclesButton.setSelected(true);
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

    private JPanel createLabeledToggleableButtons(String label, JToggleButton toggle,
                    JRadioButton button1, JRadioButton button2, JRadioButton button3) {

        JLabel buttonLabel = new JLabel(label);
        buttonLabel.setFont(font);
        JPanel buttons = createGroupedButtons(button1, button2, button3);

        JPanel toggleableButtonsPanel = new JPanel();
        toggleableButtonsPanel.setLayout(new BoxLayout(toggleableButtonsPanel, BoxLayout.Y_AXIS));
        toggleableButtonsPanel.add(toggle);
        toggleableButtonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        toggleableButtonsPanel.add(buttons);
        toggleableButtonsPanel.setBackground(new Color(200, 200, 200));
        toggleableButtonsPanel.setMaximumSize(toggleableButtonsPanel.getPreferredSize());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(buttonLabel);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(toggleableButtonsPanel);
        panel.setOpaque(false);
        panel.setMaximumSize(panel.getPreferredSize());

        // disable radio buttons by default (toggle button enables them)
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);

        toggle.addActionListener(e -> {
            boolean enabled = toggle.isSelected();
            button1.setEnabled(enabled);
            button2.setEnabled(enabled);
            button3.setEnabled(enabled);

            if (enabled) { toggle.setText("On"); }
            else { toggle.setText("Off"); }
        });

        return panel;
    }
    

    private JPanel createLabeledSlider(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel sliderLabel = new JLabel(label);
        sliderLabel.setFont(font);
        panel.add(sliderLabel, BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
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


    protected boolean getObstaclesEnabled() {
        return enableObstaclesButton.isSelected();
    }

    protected String getObstacleDifficulty() {
        if (lowObstaclesButton.isSelected()) { return "low"; }
        else if (mediumObstaclesButton.isSelected()) { return "medium"; }
        else { return "high"; }
    }

}
