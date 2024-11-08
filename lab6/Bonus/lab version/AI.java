package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Arrays;

public class AI {
    private static final int[] ROW_OFFSETS = {-1, 0, 1, 0};
    private static final int[] COL_OFFSETS = {0, 1, 0, -1};

    public static int[] findBestMove(int[][] gameState, boolean[][] sticks, int currentPlayer, int lastRow, int lastCol) {
        int[] bestMove = new int[]{-1, -1};
        System.out.println(Arrays.deepToString(gameState.clone()));

        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == 0 && isValidMove(row, col, gameState, sticks, lastRow, lastCol)) {
                    gameState[row][col] = currentPlayer;

                    // If there is no perfect matching after making the move, then it's a good move
                    if (!hasPerfectMatching(gameState, sticks, currentPlayer)) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        gameState[row][col] = 0;
                        return bestMove;
                    }

                    gameState[row][col] = 0;
                }
            }
        }
        return bestMove;
    }


    static boolean isValidMove(int row, int col, int[][] gameState, boolean[][] sticks, int lastRow, int lastCol) {
        if (gameState[row][col] != 0) {
            return false;
        }

        if (Math.abs(row - lastRow) <= 1 && Math.abs(col - lastCol) <= 1) {
            return row == lastRow || col == lastCol ? sticks[Math.min(row, lastRow)][Math.min(col, lastCol)] : false;
        }

        return false;
    }

    static boolean hasPerfectMatching(int[][] gameState, boolean[][] sticks, int currentPlayer) {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices for unoccupied cells
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == 0) {
                    graph.addVertex(row * gameState[row].length + col);
                }
            }
        }

        // Add edges for sticks between unoccupied cells
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == 0) {
                    for (int i = 0; i < 4; i++) {
                        int newRow = row + ROW_OFFSETS[i];
                        int newCol = col + COL_OFFSETS[i];
                        if (newRow >= 0 && newRow < gameState.length && newCol >= 0 && newCol < gameState[row].length && gameState[newRow][newCol] == 0) {
                            if (sticks[row][col]) {
                                graph.addEdge(row * gameState[row].length + col, newRow * gameState[newRow].length + newCol);
                            }
                        }
                    }
                }
            }
        }

        DenseEdmondsMaximumCardinalityMatching<Integer, DefaultEdge> matcher = new DenseEdmondsMaximumCardinalityMatching<>(graph);
        int matchingSize = matcher.getMatching().getEdges().size();

        return matchingSize * 2 != graph.vertexSet().size();
    }

}
