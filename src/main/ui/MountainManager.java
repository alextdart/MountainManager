package ui;

import ui.panels.BodyPanel;
import ui.panels.HeaderPanel;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO comment
public class MountainManager extends JFrame {

    // TODO https://stackoverflow.com/questions/18202415/jpanels-in-jpanel
    // Constructs main window
    // effects: sets up window in which Mountain Manager will be displayed
    public MountainManager() {
        JFrame frame = new JFrame("Mountain Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BodyPanel(), BorderLayout.CENTER);
        frame.add(new HeaderPanel("Dart Mountain"), BorderLayout.NORTH);
        frame.pack();
        sizeScreen(frame);
        frame.setVisible(true);
    }

    // Sizes the screen
    // modifies: this
    // effects:  location and size of frame is set
    private void sizeScreen(JFrame frame) {
        frame.setPreferredSize(new Dimension(1600, 900));
        frame.setLocation(0,0);
    }

    // Run the application
    public static void main(String[] args) {
        new MountainManager();
    }
}