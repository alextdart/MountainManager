package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BodyPanel extends JPanel {

    public BodyPanel() {
        setLayout(new GridLayout(1, 2));
        add(new LiftsPanel());
        add(new RunsPanel());
        setBorder(new LineBorder(Color.BLUE));
    }
}
