package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LiftsPanel extends JPanel {

    public LiftsPanel() {
        setLayout(new BorderLayout());
        add(new HalfPanelHeader(0), BorderLayout.NORTH);
        add(new HalfPanelBody(), BorderLayout.CENTER);
        add(new HalfPanelFooter(), BorderLayout.SOUTH);
        setBorder(new LineBorder(Color.BLUE));
    }
}
