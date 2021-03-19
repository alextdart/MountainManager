package ui.panels;

import javax.swing.*;
import java.awt.*;

public class HalfPanelFooter extends JPanel {

    public HalfPanelFooter() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,1));
        buttons.setSize(new Dimension(600,100));
        buttons.add(new JButton("add"));
        buttons.add(new JButton("remove"));
        add(buttons);
    }
}