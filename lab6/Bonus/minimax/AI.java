package org.example;

public class AI {
    private static final int MAX_DEPTH = 3; // max depth for the minimax algorithm
    private static final int INFINITY = 1000000; // a large value representing positive infinity
    private static final int[] ROW_OFFSETS = {-1, 0, 1, 0}; // row offsets for adjacent cells
    private static final int[] COL_OFFSETS = {0, 1, 0, -1}; // column offsets for adjacent cells

    public static int[] findBestMove(int[][] gameState, boolean[][] sticks, int currentPlayer, int lastRow, int lastCol) {
        int[] bestMove = new int[]{-1, -1}; //invalid move
        int bestScore = Integer.MIN_VALUE; //negative infinity

        // go over all empty circles and check each as a potential move
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == 0 && isValidMove(row, col, gameState, sticks, currentPlayer, lastRow, lastCol)) {
                    gameState[row][col] = currentPlayer; // make the move
                    int score = minimax(gameState, sticks, 1, false, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, row, col);
                    gameState[row][col] = 0; // undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(int[][] gameState, boolean[][] sticks, int depth, boolean isMaximizingPlayer, int currentPlayer, int alpha, int beta, int lastRow, int lastCol) {
        // base case: if reached maximum depth or game over, evaluate the game state
        if (depth == MAX_DEPTH || isGameOver(gameState)) {
            return evaluateGameState(gameState, currentPlayer);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            // iterate over all possible moves
            for (int row = 0; row < gameState.length; row++) {
                for (int col = 0; col < gameState[row].length; col++) {
                    // check if the cell is empty and a valid move
                    if (gameState[row][col] == 0 && isValidMove(row, col, gameState, sticks, currentPlayer, lastRow, lastCol)) {
                        // make the move
                        gameState[row][col] = currentPlayer;
                        // recursevly evaluate the resulting game state for the opponent
                        int eval = minimax(gameState, sticks, depth + 1, false, currentPlayer, alpha, beta, row, col);
                        // undo the move
                        gameState[row][col] = 0;
                        // update the maximum evaluation
                        maxEval = Math.max(maxEval, eval);
                        // update alpha value
                        alpha = Math.max(alpha, eval);
                        // perform alpha-beta pruning
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            // iterate over all possible moves
            for (int row = 0; row < gameState.length; row++) {
                for (int col = 0; col < gameState[row].length; col++) {
                    // check if the cell is empty and a valid move
                    if (gameState[row][col] == 0 && isValidMove(row, col, gameState, sticks, currentPlayer, lastRow, lastCol)) {
                        // make the move for the opponent
                        gameState[row][col] = 3 - currentPlayer;
                        // recursively evaluate the resulting game state for the maximizing player
                        int eval = minimax(gameState, sticks, depth + 1, true, currentPlayer, alpha, beta, row, col);
                        // undo the move
                        gameState[row][col] = 0;
                        // update the minimum evaluation
                        minEval = Math.min(minEval, eval);
                        // update beta value
                        beta = Math.min(beta, eval);
                        // perform alpha-beta pruning
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    private static boolean isValidMove(int row, int col, int[][] gameState, boolean[][] sticks, int currentPlayer, int lastRow, int lastCol) {
        // empty circle
        if (gameState[row][col] != 0) {
            return false;
        }
        // always valid if it's the first move
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

        // check if exists a stick between last and current move
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

    private static boolean hasPath(int row, int col, int player, int[][] gameState, boolean[][] sticks, boolean[][] visited) {
        if (row == 0 && player == 1) {
            return true; // red player has reached the top row
        }
        if (row == gameState.length - 1 && player == 2) {
            return true; // blue player has reached the bottom row
        }
        visited[row][col] = true;
        for (int i = 0; i < 4; i++) {
            int newRow = row + ROW_OFFSETS[i];
            int newCol = col + COL_OFFSETS[i];
            if (newRow >= 0 && newRow < gameState.length && newCol >= 0 && newCol < gameState[newRow].length && !visited[newRow][newCol] && gameState[newRow][newCol] == player && sticks[Math.min(row, newRow)][Math.min(col, newCol)]) {
                if (hasPath(newRow, newCol, player, gameState, sticks, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int evaluateGameState(int[][] gameState, int currentPlayer) {
        // simple heuristic: count the number of stones for the current player
        int score = 0;
        for (int[] row : gameState) {
            for (int cell : row) {
                if (cell == currentPlayer) {
                    score++;
                }
            }
        }
        return score;
    }

    private static boolean isGameOver(int[][] gameState) {
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == 0) {
                    return false; // game is not over, an empty circle is found
                }
            }
        }
        return true; // game is over, no empty circles left
    }

}