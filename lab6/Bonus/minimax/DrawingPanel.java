package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class DrawingPanel extends JPanel {
    final MFrame frame;
    int gridRows;
    int gridColumns;
    int cellSize = 50; //size of each cell in pixels
    BufferedImage image; //the offscreen image
    Graphics2D offscreen; //the offscreen graphics
    boolean[][] sticks; //array to hold the presence of sticks at each intersection
    private int[][] gameState; //0 = empty, 1 = red, 2 = blue
    private int currentPlayer = 1; //1 = red's turn, 2 = blue's turn
    private int lastRow = -1, lastCol = -1; //last cell that was clicked
    private boolean gameOver = false; // ending the game
    private boolean isAIEnabled = false; // flag for AI
    private int aiPlayer = 2; // assume AI is blue


    public DrawingPanel(MFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(600, 600));
        createOffscreenImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gameOver){
                    return;
                }
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                if (isValidMove(row, col)) {
                    gameState[row][col] = currentPlayer;
                    //switch player
                    currentPlayer = 3 - currentPlayer;
                    //update the last clicked stone
                    lastRow = row;
                    lastCol = col;
                    // redraw
                    repaint();
                }
                if (!hasValidMoves()) {
                    // Export current image as PNG
                    saveImage("game_board");
                    // end game, show the winner
                    String winner = currentPlayer == 1 ? "Blue" : "Red";
                    JOptionPane.showMessageDialog(frame, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    // reset the current player to red to replay
                    currentPlayer = 3 - currentPlayer;
                    gameOver = true;
                }
                if (isAIEnabled && currentPlayer == aiPlayer) {
                    enableAI(true); // Let the AI make a move
                }
            }
        });
    }

    public void init(int rows, int cols) {
        this.gridRows = rows;
        this.gridColumns = cols;
        sticks = new boolean[rows][cols];
        gameState = new int[rows][cols]; // reset the game state
        currentPlayer = 1; // reset the current player
        lastRow = -1; // reset the last clicked cell
        lastCol = -1; // reset the last clicked cell
        gameOver = false; //reset the game

        // clear the screen at a new game
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, getWidth(), getHeight());
        createRandomSticks();
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
            drawCircles();
            drawSticks();
            drawStones();
        }
        g.drawImage(image, 0, 0, this);
    }

    private void drawGrid() {
        offscreen.setColor(Color.GRAY);
        int offset = 20;
        for (int row = 0; row < gridRows-1; row++) {
            for (int col = 0; col < gridColumns-1; col++) {
                int x = col * cellSize + offset;
                int y = row * cellSize + offset;
                offscreen.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void drawCircles() {
        offscreen.setColor(Color.GRAY);
        int offset = 20; // offset in pixels
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                int centerX = col * cellSize + offset;
                int centerY = row * cellSize + offset;
                offscreen.drawOval(centerX - cellSize / 4, centerY - cellSize / 4, cellSize / 2, cellSize / 2);
            }
        }
    }

    public void createRandomSticks() {
        Random random = new Random();
        int stickProbability = 86;

        for (int row = 0; row < gridRows-1; row++) {
            for (int col = 0; col < gridColumns-1; col++) {
                // connect circles horizontally with a probability
                if (random.nextInt(100) < stickProbability) {
                    sticks[row][col] = true;
                } else {
                    sticks[row][col] = false;
                }
                // connect circles vertically with a probability
                if (random.nextInt(100) < stickProbability) {
                    sticks[row][col] = true;
                } else {
                    sticks[row][col] = false;
                }
            }
        }
    }

    public void drawSticks() {
        int stickWidth = 5;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                if (sticks[row][col]) {
                    int x1 = col * cellSize + 20;
                    int y1 = row * cellSize + 20;
                    int x2 = (col + 1) * cellSize + 20;
                    int y2 = y1;

                    offscreen.setColor(Color.BLACK);
                    offscreen.setStroke(new BasicStroke(stickWidth));
                    offscreen.drawLine(x1, y1, x2, y2);

                    x2 = x1;
                    y2 = (row + 1) * cellSize + 20;
                    offscreen.drawLine(x1, y1, x2, y2);
                }
            }
        }
        offscreen.setStroke(new BasicStroke());
    }


    private void drawStones() {
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                if (gameState[row][col] != 0) {
                    int x = col * cellSize + 20;
                    int y = row * cellSize + 20;
                    offscreen.setColor(gameState[row][col] == 1 ? Color.RED : Color.BLUE);
                    offscreen.fillOval(x - cellSize / 4, y - cellSize / 4, cellSize / 2, cellSize / 2);
                }
            }
        }
    }


    // check if a move is valid
    private boolean isValidMove(int row, int col) {
        // empty circle
        if (gameState[row][col] != 0) {
            return false;
        }
        // always valid of it's first move
        if (lastRow == -1 && lastCol == -1) {
            return true;
        }
        // the circle must be adjacent to the last clicked circle
        if (Math.abs(row - lastRow) > 1 || Math.abs(col - lastCol) > 1) {
            return false;
        }

        // don't go on diagonals
        if (row != lastRow && col != lastCol) {
            return false;
        }

        //check if exists a stick between last and current move
        if (row == lastRow) {
            // the circles are in the same row, check the column
            int minCol = Math.min(col, lastCol);
            return sticks[row][minCol];
        } else {
            // the circles are in the same column, check the row
            int minRow = Math.min(row, lastRow);
            return sticks[minRow][col];
        }
    }

    private boolean hasValidMoves() {
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                if (gameState[row][col] == 0 && isValidMove(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gameState.dat"))) {
            out.writeObject(gameState);
            out.writeObject(sticks);
            out.writeInt(currentPlayer);
            out.writeInt(lastRow);
            out.writeInt(lastCol);
            out.writeBoolean(gameOver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        // check if gameState and sticks are initialized
        if (gameState == null || sticks == null) {
            JOptionPane.showMessageDialog(this, "Please create a new game before loading.", "Load Game Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gameState.dat"))) {
            gameState = (int[][]) in.readObject();
            sticks = (boolean[][]) in.readObject();
            currentPlayer = in.readInt();
            lastRow = in.readInt();
            lastCol = in.readInt();
            gameOver = in.readBoolean();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        repaint();
    }


    public void clearGameState() {
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                gameState[i][j] = 0;
                sticks[i][j] = false;
            }
        }
        // reset other game variables
        currentPlayer = 1;
        lastRow = -1;
        lastCol = -1;
        gameOver = false;
        repaint();
    }

    public void saveImage(String fileName) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(img, "PNG", new File(fileName + ".png"));
            System.out.println("Image saved successfully.");
        } catch (IOException ex) {
            System.err.println("Error saving image: " + ex.getMessage());
        }
    }

    public void enableAI(boolean enable) {
        isAIEnabled = enable;
        if (isAIEnabled && currentPlayer == aiPlayer) {
            // AI's turn, make a move
            int[] aiMove = AI.findBestMove(gameState, sticks, aiPlayer, lastRow, lastCol);
            if (aiMove[0] != -1 && aiMove[1] != -1) {
                gameState[aiMove[0]][aiMove[1]] = aiPlayer;
                currentPlayer = 3 - aiPlayer;
                lastRow = aiMove[0];
                lastCol = aiMove[1];
                repaint();
            }
        }
    }


}
