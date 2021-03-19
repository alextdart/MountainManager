package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new FlowLayout(4));
        for (int index = 0; index < 4; index++) {
            add(new JLabel(Integer.toString(index)));
        }
        setBorder(new LineBorder(Color.RED));
    }
}
