package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    final MFrame frame;
    JButton loadBtn, saveBtn, exitBtn;

    public ControlPanel(MFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        // Add buttons
        loadBtn = new JButton("Load");
        saveBtn = new JButton("Save");
        exitBtn = new JButton("Exit");

        // add buttons
        add(loadBtn);
        add(saveBtn);
        add(exitBtn);


        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
