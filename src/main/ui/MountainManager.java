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

public class MountainManager extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel body;
    private JPanel header;
    private SkiResort resort;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/resort.json"; // borrowed from JsonDemo

    // Constructs main window
    // EFFECTS: initializes with blank resort, starts fields and graphics
    public MountainManager() {
        resort = new SkiResort("Unnamed Mountain");
        initializeJson();
        initializeGraphics();
    }

    // EFFECTS: constructs JFrame graphics and opens the app window
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

    // EFFECTS: initializes JSON objects
    private void initializeJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // REQUIRES: a json file at JSON_STORE exists
    // MODIFIES: this.resort
    // EFFECTS: loads resort file, refreshes graphics
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

    // REQUIRES: a json file at JSON_STORE exists
    // MODIFIES: JSON_STORE file
    // EFFECTS: saves current resort to file
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

    // doesn't do anything, only here to have a general implementation for ActionListener
    public void actionPerformed(ActionEvent ae) {
        // blank
    }

    // REQUIRES: a frame is already displayed
    // MODIFIES: this
    // EFFECTS: redraws the graphics to the same size and place as they were before
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

    // MODIFIES: resort
    // EFFECTS: adds a new lift to the resort
    private void addLift() {
        String name = JOptionPane.showInputDialog("Name of new lift: ");
        int numSeats = Integer.parseInt(JOptionPane.showInputDialog("Number of seats per chair: "));
        resort.addLift(name, numSeats);
        refresh();
    }

    // MODIFIES: resort
    // EFFECTS: adds a new run to the resort
    private void addRun() {
        String name = JOptionPane.showInputDialog("Name of new run: ");
        resort.addRun(name);
        refresh();
    }

    // MODIFIES: resort
    // EFFECTS: deletes a lift from the resort
    private void delLift() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID of lift to remove: "));
        resort.removeLift(id);
        refresh();
    }

    // MODIFIES: resort
    // EFFECTS: deletes a run from the resort
    private void delRun() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID of run to remove: "));
        resort.removeRun(id);
        refresh();
    }

    // MODIFIES: resort
    // EFFECTS: depending on user choice, opens, closes or updates a lift from resort, and refreshes graphics
    private void modifyLifts() {
        int target = Integer.parseInt(JOptionPane.showInputDialog("ID of lift to modify: "));
        String func = JOptionPane.showInputDialog("Modifying: " + target + ". Enter 'update', 'open' or 'close': ");
        if (func.equals("update")) {
            int pplInLine = Integer.parseInt(JOptionPane.showInputDialog("How many people in line?: "));
            resort.updateLiftLine(target, pplInLine);
        } else if (func.equals("open")) {
            resort.openLift(target);
        } else {
            resort.closeLift(target);
        }
        refresh();
    }

    // MODIFIES: resort
    // EFFECTS: depending on user choice, opens or closes a run from resort, and refreshes graphics
    private void modifyRuns() {
        int target = Integer.parseInt(JOptionPane.showInputDialog("ID of run to modify: "));
        String func = JOptionPane.showInputDialog("Modifying: " + target + ". Enter 'open' or 'close': ");
        if (func.equals("open")) {
            String newStatus = JOptionPane.showInputDialog("New condition: ");
            resort.openRun(target, newStatus);
        } else {
            resort.closeRun(target);
        }
        refresh();
    }

    // ---------------------------------------------
    // PANELS ARE DOWN HERE SO THEY CAN CALL METHODS
    // ---------------------------------------------

    // EFFECTS: creates the body panel with all the content
    private JPanel bodyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(liftsPanel());
        panel.add(runsPanel());
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    // EFFECTS: creates the header panel with the save and load buttons, and title
    private JPanel headerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(leftText(resort.getName()));
        panel.add(saveLoadButtons());
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    // EFFECTS: creates the panel with the lift information
    private JPanel liftsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(halfPanelHeader(0), BorderLayout.NORTH);
        panel.add(halfPanelBody(0), BorderLayout.CENTER);
        panel.add(halfPanelFooter(0), BorderLayout.SOUTH);
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    // EFFECTS: creates the panel with the run information
    private JPanel runsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(halfPanelHeader(1), BorderLayout.NORTH);
        panel.add(halfPanelBody(1), BorderLayout.CENTER);
        panel.add(halfPanelFooter(1), BorderLayout.SOUTH);
        panel.setBorder(new LineBorder(Color.BLUE));
        return panel;
    }

    // EFFECTS: creates the header for either the lift or runs panel
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

    // EFFECTS: creates the body for either the lifts or runs panel (the content list)
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

    // EFFECTS: creates the footer (add, delete, modify buttons) for either the lifts or runs panel
    private JPanel halfPanelFooter(int liftOrRun) {
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,1));
        buttons.setSize(new Dimension(600,100));
        if (liftOrRun == 0) {
            buttons.add(modifyLiftButton());
            buttons.add(addDelLiftButton(false));
            buttons.add(addDelLiftButton(true));
        } else {
            buttons.add(modifyRunButton());
            buttons.add(addDelRunButton(false));
            buttons.add(addDelRunButton(true));
        }
        panel.add(buttons);
        return panel;
    }

    // ---------------------------------------------
    // PANELS HELPERS
    // ---------------------------------------------

    // EFFECTS: creates the modify lifts button with correct functionality
    private JButton modifyLiftButton() {
        JButton modifyLiftButton = new JButton("modify lifts");
        modifyLiftButton.addActionListener(e -> modifyLifts());
        return modifyLiftButton;
    }

    // EFFECTS: creates the modify runs button with correct functionality
    private JButton modifyRunButton() {
        JButton modifyRunButton = new JButton("modify runs");
        modifyRunButton.addActionListener(e -> modifyRuns());
        return modifyRunButton;
    }

    // EFFECTS: creates the add/delete lifts buttons with functionality
    private JButton addDelLiftButton(boolean func) {
        if (!func) {
            JButton addLiftButton = new JButton("add lift");
            addLiftButton.addActionListener(e -> addLift());
            return addLiftButton;
        } else {
            JButton delLiftButton = new JButton("remove lift");
            delLiftButton.addActionListener(e -> delLift());
            return delLiftButton;
        }
    }

    // EFFECTS: creates the add/delete runs buttons with functionality
    private JButton addDelRunButton(boolean func) {
        if (!func) {
            JButton addRunButton = new JButton("add run");
            addRunButton.addActionListener(e -> addRun());
            return addRunButton;
        } else {
            JButton delRunButton = new JButton("remove run");
            delRunButton.addActionListener(e -> delRun());
            return delRunButton;
        }
    }

    // EFFECTS: creates the label for the mountain title at the top of the header
    private JLabel leftText(String text) {
        JLabel l = new JLabel();
        l.setText(text);
        l.setFont(new Font("Courier", Font.BOLD,75));
        l.setPreferredSize(new Dimension(600,100));
        return l;
    }

    // EFFECTS: creates the save/load resort buttons with functionality
    private JPanel saveLoadButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
        JButton saveButton = new JButton("save");
        saveButton.addActionListener(e -> saveResort());
        JButton loadButton = new JButton("load");
        loadButton.addActionListener(e -> loadResort());
        buttons.add(saveButton);
        buttons.add(loadButton);
        return buttons;
    }

    // EFFECTS: creates the text for the lifts panel header
    private String generateLiftsText(int open, int total) {
        return "Lifts | " + open + "/" + total + " open";
    }

    // EFFECTS: creates the text for the runs panel header
    private String generateRunsText(int open, int total) {
        return "Runs | " + open + "/" + total + " open";
    }

    // EFFECTS: generates the list of lifts
    private JPanel generateLiftsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(resort.getNumOfLifts() + 1, 4, 30, 8));
        panel.add(new JLabel("ID"));
        panel.add(new JLabel("NAME"));
        panel.add(new JLabel("STATUS"));
        panel.add(new JLabel("WAIT TIME"));
        for (Lift lift : resort.viewAllLifts()) {
            panel.add(new JLabel("" + lift.getID()));
            panel.add(new JLabel(lift.getName()));
            panel.add(new JLabel(boolToOpenOrClose(lift.getOpen())));
            panel.add(new JLabel(resort.getLiftLineEstimate(lift.getID())));
        }
        return panel;
    }

    // EFFECTS: generates the list of runs
    private JPanel generateRunsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(resort.getNumOfRuns() + 1, 4, 30, 8));
        panel.add(new JLabel("ID"));
        panel.add(new JLabel("NAME"));
        panel.add(new JLabel("STATUS"));
        panel.add(new JLabel("CONDITION"));
        for (SkiRun run : resort.viewAllRuns()) {
            panel.add(new JLabel("" + run.getID()));
            panel.add(new JLabel(run.getName()));
            panel.add(new JLabel(boolToOpenOrClose(run.getOpen())));
            panel.add(new JLabel(run.getStatus()));
        }
        return panel;
    }

    // REQUIRES: a boolean
    // EFFECTS: converts a boolean to the text either "open" or "closed"
    private String boolToOpenOrClose(Boolean b) {
        if (b) {
            return "open";
        } else {
            return "closed";
        } 
    }
}