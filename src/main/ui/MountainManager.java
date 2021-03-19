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
        JPanel mainFrame = new JPanel(new GridLayout(2, 1));
        mainFrame.add(new HeaderPanel());
        mainFrame.add(new BodyPanel());

        JFrame frame = new JFrame("Mountain Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainFrame);
        frame.pack();
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
//    private void centreOnScreen() {
//        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
//    }

    // Run the application
    public static void main(String[] args) {
        new MountainManager();
    }
}