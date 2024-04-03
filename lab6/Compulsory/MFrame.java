package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    boolean gameStarted;

    public MFrame() {
        super("Positional Game");
        init();

        // re-center the frame when it is resized
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setLocationRelativeTo(null);
            }
        });

    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Create the components
        configPanel = new ConfigPanel(this);
        canvas = new DrawingPanel(this);
        controlPanel = new ControlPanel(this);

        // Arrange the components in the container (frame)
        add(configPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);


        pack();
        setLocationRelativeTo(null);
    }

    public void createNewGame(int gridRows, int gridColumns) {
        gameStarted = true;
        canvas.init(gridRows, gridColumns);
        canvas.createRandomSticks();
        canvas.repaint();
    }
}