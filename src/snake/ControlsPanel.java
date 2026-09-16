package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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


    public ControlsPanel() {
        setupGUIElements();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createGroupedButtons(normalSpeedButton, fastSpeedButton, slowSpeedButton));
        add(createGroupedButtons(normalSizeButton, smallSizeButton, largeSizeButton));
        add(createLabeledSlider("Number of apples: ", numApplesSlider));
    }


    private void setupGUIElements() {
        speedButtons.add(normalSpeedButton);
        speedButtons.add(fastSpeedButton);
        speedButtons.add(slowSpeedButton);
        normalSpeedButton.setSelected(true);
        // speedButtons.setPreferredSize(new Dimension(100, 25));

        mapSizeButtons.add(normalSizeButton);
        mapSizeButtons.add(smallSizeButton);
        mapSizeButtons.add(largeSizeButton);
        normalSizeButton.setSelected(true);
        // mapSizeButtons.setPreferredSize(new Dimension(100, 25));

        numApplesSlider = new JSlider(1, 5, 1);
        // numApplesSlider.setPreferredSize(new Dimension(150, 50));
    }


    private JPanel createGroupedButtons(JRadioButton button1, JRadioButton button2, JRadioButton button3) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        return panel;
    }
    

    private JPanel createLabeledSlider(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
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
