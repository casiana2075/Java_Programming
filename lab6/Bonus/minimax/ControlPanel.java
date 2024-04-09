package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    final MFrame frame;
    JButton loadBtn, saveBtn, exitBtn, enableAIBtn, disableAIBtn;

    public ControlPanel(MFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        // add buttons
        loadBtn = new JButton("Load");
        saveBtn = new JButton("Save");
        exitBtn = new JButton("Exit");
        enableAIBtn = new JButton("Enable AI");
        disableAIBtn = new JButton("Disable AI");

        // add buttons to the panel
        add(loadBtn);
        add(saveBtn);
        add(exitBtn);
        add(enableAIBtn);
        add(disableAIBtn);

        // set up action listeners for the buttons
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.canvas.clearGameState();
                frame.canvas.loadGame();
                frame.canvas.repaint();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.canvas.saveGame();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        enableAIBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.canvas.enableAI(true);
            }
        });

        disableAIBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.canvas.enableAI(false);
            }
        });
    }
}
