package org.server;

import java.util.Random;

public class Board {
    private char[][] board;
    private Random random = new Random();

    public Board() {
        this.board = new char[10][10];
    }

    public void generateBoard() {
        // fill with water
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = '~';
            }
        }
        // generate boats
        generateBoat(2, 3);
        generateBoat(3, 2);
        generateBoat(4, 1);
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, char value) {
        board[row][col] = value;
    }

    private void generateBoat(int length, int count) {
        for (int i = 0; i < count; i++) {
            int x, y;
            boolean vertical;
            do {
                x = random.nextInt(10);
                y = random.nextInt(10);
                vertical = random.nextBoolean();
            } while (!canPlaceBoat(x, y, length, vertical));

            for (int j = 0; j < length; j++) {
                board[vertical ? x + j : x][vertical ? y : y + j] = 'o';
            }
        }
    }

    private boolean canPlaceBoat(int x, int y, int length, boolean vertical) {
        for (int i = -1; i <= length; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = vertical ? x + i : x + j;
                int newY = vertical ? y + j : y + i;
                if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10 || board[newX][newY] != '~') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean areAllBoatsHit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 'o') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 0; i < 10; i++) {
            sb.append((char) ('A' + i)).append(" ");
        }
        sb.append("\n");

        for (int i = 0; i < 10; i++) {
            if (i < 9) {
                sb.append(" ");
            }
            sb.append((i + 1)).append(" ");
            for (int j = 0; j < 10; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}