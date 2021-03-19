package ui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HalfPanelHeader extends JPanel {

    private int total;
    private int open;

    public HalfPanelHeader(int liftsOrRuns) {
        JLabel text = new JLabel();
        setLayout(new FlowLayout(FlowLayout.LEADING));
        if (liftsOrRuns == 0) {
            text.setText(generateLiftsText(0, 0));
        } else {
            text.setText(generateRunsText(0, 0));
        }
        add(text);

        //setBorder(new LineBorder(Color.RED));
    }

    private String generateLiftsText(int open, int total) {
        return "Lifts | " + open + "/" + total + " open";
    }

    private String generateRunsText(int open, int total) {
        return "Runs | " + open + "/" + total + " open";
    }
}
