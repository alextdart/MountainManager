package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RunsPanel extends JPanel {

    public RunsPanel() {
        setLayout(new GridLayout(3, 1));
        add(new HalfPanelHeader());
        add(new HalfPanelBody());
        add(new HalfPanelFooter());
        setBorder(new LineBorder(Color.BLUE));
    }
}

