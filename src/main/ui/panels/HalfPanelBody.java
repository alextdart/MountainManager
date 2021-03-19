package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HalfPanelBody extends JPanel {

    public HalfPanelBody() {
        setLayout(new FlowLayout(3));

        setBorder(new LineBorder(Color.RED));
    }
}