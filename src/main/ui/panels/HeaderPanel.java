package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel(String mountainName) {
        setLayout(new GridLayout(1,2));
        add(leftText(mountainName));
        add(saveLoadButtons());
        setBorder(new LineBorder(Color.BLUE));
    }

    private JLabel leftText(String text) {
        JLabel l = new JLabel();
        l.setText(text);
        l.setFont(new Font("Courier", Font.BOLD,75));
        l.setPreferredSize(new Dimension(600,100));
        return l;
    }

    private JPanel saveLoadButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
        buttons.add(new JButton("save"));
        buttons.add(new JButton("load"));
        return buttons;
    }
}
