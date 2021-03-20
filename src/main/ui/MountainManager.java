package ui;

import model.Lift;
import model.SkiResort;
import model.SkiRun;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.LineBorder;

// TODO comment
public class MountainManager extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel body;
    private JPanel header;
    private SkiResort resort;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/resort.json"; // borrowed from JsonDemo
    private JButton saveButton;
    private JButton loadButton;
    private JButton addLiftButton;
    private JButton addRunButton;
    private JButton delLiftButton;
    private JButton delRunButton;


    // TODO https://stackoverflow.com/questions/18202415/jpanels-in-jpanel
    // Constructs main window
    // effects: sets up window in which Mountain Manager will be displayed
    public MountainManager() {
        resort = new SkiResort("Unnamed Mountain");
        initializeFields();
        initializeGraphics();
    }

    private void initializeGraphics() {
        frame = new JFrame("Mountain Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        body = bodyPanel();
        header = headerPanel();
        frame.add(body, BorderLayout.CENTER);
        frame.add(header, BorderLayout.NORTH);
        frame.pack();
        frame.setSize(new Dimension(1600, 900));
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }

    private void initializeFields() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void loadResort() {
        try {
            resort = jsonReader.read();
            System.out.println("Loaded " + resort.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } finally {
            refresh();
        }
    }

    private void saveResort() {
        try {
            jsonWriter.open();
            jsonWriter.write(resort);
            jsonWriter.close();
            System.out.println("Saved " + resort.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        // blank
    }

    private void refresh() {
        Dimension curSize = frame.getSize();
        frame.remove(body);
        frame.remove(header);
        body = bodyPanel();
        header = headerPanel();
        frame.add(body, BorderLayout.CENTER);
        frame.add(header, BorderLayout.NORTH);
        frame.setPreferredSize(curSize);
        frame.pack();
        frame.invalidate();
        frame.validate();
    }

    private void addLift() {
        String name = JOptionPane.showInputDialog("Name of new lift: ");
        int numSeats = Integer.parseInt(JOptionPane.showInputDialog("Number of seats per chair: "));
        resort.addLift(name, numSeats);
        refresh();
    }

    private void addRun() {
        String name = JOptionPane.showInputDialog("Name of new run: ");
        resort.addRun(name);
        refresh();
    }

    private void delLift() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID of lift to remove: "));
        resort.removeLift(id);
        refresh();
    }

    private void delRun() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID of run to remove: "));
        resort.removeRun(id);
        refresh();
    }

    // ---------------------------------------------
    // PANELS ARE DOWN HERE SO THEY CAN CALL METHODS
    // ---------------------------------------------

    private JPanel bodyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(liftsPanel());
        panel.add(runsPanel());
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    private JPanel headerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(leftText(resort.getName()));
        panel.add(saveLoadButtons());
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    private JPanel liftsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(halfPanelHeader(0), BorderLayout.NORTH);
        panel.add(halfPanelBody(0), BorderLayout.CENTER);
        panel.add(halfPanelFooter(0), BorderLayout.SOUTH);
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    private JPanel runsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(halfPanelHeader(1), BorderLayout.NORTH);
        panel.add(halfPanelBody(1), BorderLayout.CENTER);
        panel.add(halfPanelFooter(1), BorderLayout.SOUTH);
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    private JPanel halfPanelHeader(int liftOrRun) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        if (liftOrRun == 0) {
            text.setText(generateLiftsText(resort.getNumOpenLifts(), resort.getNumOfLifts()));
        } else {
            text.setText(generateRunsText(resort.getNumOpenRuns(), resort.getNumOfRuns()));
        }
        panel.add(text);
        return panel;
    }

    private JPanel halfPanelBody(int liftOrRun) {
        JPanel panel = new JPanel();
        if (liftOrRun == 0) {
            panel.add(generateLiftsList());
        } else {
            panel.add(generateRunsList());
        }
        panel.setBorder(new LineBorder(Color.CYAN));
        return panel;
    }

    private JPanel halfPanelFooter(int liftOrRun) {
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,1));
        buttons.setSize(new Dimension(600,100));
        if (liftOrRun == 0) {
            buttons.add(addDelLiftButton(false));
            buttons.add(addDelLiftButton(true));
        } else {
            buttons.add(addDelRunButton(false));
            buttons.add(addDelRunButton(true));
        }
        panel.add(buttons);
        return panel;
    }

    // ---------------------------------------------
    // PANELS HELPERS
    // ---------------------------------------------

    private JButton addDelLiftButton(boolean func) {
        if (!func) {
            addLiftButton = new JButton("add lift");
            addLiftButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addLift();
                }
            });
            return addLiftButton;
        } else {
            delLiftButton = new JButton("remove lift");
            delLiftButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    delLift();
                }
            });
            return delLiftButton;
        }
    }

    private JButton addDelRunButton(boolean func) {
        if (!func) {
            addRunButton = new JButton("add run");
            addRunButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addRun();
                }
            });
            return addRunButton;
        } else {
            delRunButton = new JButton("remove run");
            delRunButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    delRun();
                }
            });
            return delRunButton;
        }
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
        saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveResort();
            }
        });
        loadButton = new JButton("load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadResort();
            }
        });
        buttons.add(saveButton);
        buttons.add(loadButton);
        return buttons;
    }

    private String generateLiftsText(int open, int total) {
        return "Lifts | " + open + "/" + total + " open";
    }

    private String generateRunsText(int open, int total) {
        return "Runs | " + open + "/" + total + " open";
    }

    private JPanel generateLiftsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(resort.getNumOfLifts(), 5, 20, 2));
        for (Lift lift : resort.viewAllLifts()) {
            panel.add(new JLabel("" + lift.getID()));
            panel.add(new JLabel(lift.getName()));
            panel.add(new JLabel(boolToOpenOrClose(lift.getOpen())));
            panel.add(new JLabel(resort.getLiftLineEstimate(lift.getID())));
            panel.add(new JButton("change"));
        }
        return panel;
    }

    private JPanel generateRunsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(resort.getNumOfRuns(), 5, 20, 2));
        for (SkiRun run : resort.viewAllRuns()) {
            panel.add(new JLabel("" + run.getID()));
            panel.add(new JLabel(run.getName()));
            panel.add(new JLabel(boolToOpenOrClose(run.getOpen())));
            panel.add(new JLabel(run.getStatus()));
            panel.add(new JButton("change"));
        }
        return panel;
    }

    private String boolToOpenOrClose(Boolean b) {
        if (b) {
            return "open";
        } else {
            return "closed";
        } 
    }
}