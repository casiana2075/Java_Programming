package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel {
    final MFrame frame;
    JLabel labelRows, labelColumns;
    JSpinner spinnerRows, spinnerColumns;
    JButton newGameBtn;

    public ConfigPanel(MFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        // create the labels, spinners, and new game button
        labelRows = new JLabel("Grid rows:");
        spinnerRows = new JSpinner(new SpinnerNumberModel(11, 2, 11, 1));
        labelColumns = new JLabel("Grid columns:");
        spinnerColumns = new JSpinner(new SpinnerNumberModel(11, 2, 11, 1));
        newGameBtn = new JButton("New Game");

        // add components to the panel
        add(labelRows);
        add(spinnerRows);
        add(labelColumns);
        add(spinnerColumns);
        add(newGameBtn);

        // configure listener for new game button
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gridRows = (int) spinnerRows.getValue();
                int gridColumns = (int) spinnerColumns.getValue();
                frame.createNewGame(gridRows, gridColumns);
            }
        });
    }
}
