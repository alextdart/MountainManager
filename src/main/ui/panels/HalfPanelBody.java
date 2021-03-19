package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HalfPanelBody extends JPanel {

    public HalfPanelBody() {
        add(new JScrollPane());
        setBorder(new LineBorder(Color.RED));
    }
}