package org.example;

import javax.swing.*;
import java.awt.*;


public class MFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    boolean gameStarted;

    public MFrame(){
        super("Positional Game");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // create the components
        configPanel = new ConfigPanel(this);
        canvas = new DrawingPanel(this);
        controlPanel = new ControlPanel(this);

        // arrange the components in the container (frame)
        add(configPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void createNewGame(int gridRows, int gridColumns) {
        gameStarted = true;
        canvas.init(gridRows, gridColumns);
        canvas.repaint();
    }
}