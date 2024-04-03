package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DrawingPanel extends JPanel {
    final MFrame frame;
    int gridRows;
    int gridColumns;
    int cellSize = 50; // size of each cell in pixels
    BufferedImage image; // the offscreen image
    Graphics2D offscreen; // the offscreen graphics
    boolean[][] sticks; // array to hold the presence of sticks at each intersection

    public DrawingPanel(MFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(600, 600));
        createOffscreenImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
                System.out.println("Clicked on cell: " + row + ", " + col);

            }
        });
    }

    public void init(int rows, int cols) {
        this.gridRows = rows;
        this.gridColumns = cols;
        sticks = new boolean[rows][cols];
        // clear the screen at a new game
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, getWidth(), getHeight());
    }

    public void createRandomSticks() {
        Random random = new Random();
        for (int row = 0; row < gridRows - 1; row++) {
            for (int col = 0; col < gridColumns - 1; col++) {
                sticks[row][col] = random.nextBoolean();
            }
        }
    }

    private void createOffscreenImage() {
        image = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frame.gameStarted) {
            drawGrid();
            drawSticks();
        }
        g.drawImage(image, 0, 0, this);
    }

    private void drawGrid() {
        offscreen.setColor(Color.BLACK);
        int offset = 20;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                int x = col * cellSize + offset;
                int y = row * cellSize + offset;
                offscreen.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void drawSticks() {
        offscreen.setColor(Color.BLACK);
        int offset = 20; // Offset in pixels
        for (int row = 0; row <= gridRows; row++) {
            for (int col = 0; col <= gridColumns; col++) {
                int centerX = col * cellSize + offset;
                int centerY = row * cellSize + offset;
                offscreen.drawOval(centerX - cellSize / 4, centerY - cellSize / 4, cellSize / 2, cellSize / 2);
            }
        }
    }
}
