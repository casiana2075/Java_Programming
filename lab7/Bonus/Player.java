package org.example;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.tour.PalmerHamiltonianCycle;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running = true;
    List<Tile> playerTiles = new LinkedList<>();  // tiles of each player
    int maxSequenceLength; // length of the longest sequence
    private final int playerNumber;
    private Graph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    OreHamiltonianCycle oreHamiltonianCycle = new OreHamiltonianCycle();

    public Player(String name, Game game, int playerNumber) {
        this.name = name;
        this.game = game;
        this.maxSequenceLength = 0;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public void addTile(Tile tile) {
        playerTiles.add(tile);
        if (!graph.containsVertex(tile.first())) {
            graph.addVertex(tile.first());
        }
        if (!graph.containsVertex(tile.second())) {
            graph.addVertex(tile.second());
        }
        graph.addEdge(tile.first(), tile.second());
    }

    public int getScore() {
        List<Integer> longestPath = getLongestTrail();
        return longestPath.size();
    }

    public List<Integer> getLongestTrail() {
        List<Integer> longestTrail = new ArrayList<>();
        Set<Integer> vertices = graph.vertexSet();
        int maxVertexIndex = vertices.stream().mapToInt(Integer::intValue).max().orElse(0); // Find the maximum vertex index
        boolean[][] visitedEdges = new boolean[maxVertexIndex + 1][maxVertexIndex + 1]; // Initialize the visitedEdges matrix

        for (Integer vertex : vertices) {
            List<Integer> trail = new ArrayList<>();
            dfs(vertex, trail, visitedEdges, longestTrail);
        }
        return longestTrail;
    }

    private void dfs(Integer v, List<Integer> trail, boolean[][] visitedEdges, List<Integer> longestTrail) {
        trail.add(v);

        if (trail.size() > longestTrail.size()) {
            longestTrail.clear();
            longestTrail.addAll(trail);
        }

        for (DefaultEdge edge : graph.outgoingEdgesOf(v)) {
            Integer neighbor = graph.getEdgeTarget(edge);
            if (!visitedEdges[v][neighbor]) {
                visitedEdges[v][neighbor] = true;
                dfs(neighbor, trail, visitedEdges, longestTrail);
                visitedEdges[v][neighbor] = false; // Mark as unvisited for other paths
                trail.remove(trail.size() - 1); // Backtrack to explore other paths
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    public void printLongestSequence() {
        List<Integer> longestTrail = getLongestTrail();
        int longestPathLength = longestTrail.size() - 1;
        if (longestPathLength > 0) {
            System.out.println(name + "'s longest sequence (length " + longestPathLength + "):");
            for (int vertex : longestTrail) {
                System.out.print(vertex + " ");
            }
            System.out.println();
            checkOreConditionAndFindHamiltonianCycle();
        } else {
            System.out.println(name + " has no sequences.");
        }
    }

    public void checkOreConditionAndFindHamiltonianCycle() {
        if (oreHamiltonianCycle.isOre(graph)) {
            System.out.println(name + "'s graph satisfies Ore's condition.");
            HamiltonianCycleAlgorithm<Integer,DefaultEdge> palmerAlgorithm = new PalmerHamiltonianCycle<Integer,DefaultEdge>();
            GraphPath<Integer, DefaultEdge> hamiltonianCycle = null;
            try {
                hamiltonianCycle = palmerAlgorithm.getTour(graph);
               //hamiltonianCycle = oreHamiltonianCycle.getTour(graph);
            } catch (IllegalArgumentException e) {
                System.out.println(name + "'s graph does not have a Hamiltonian cycle.");
            }
            if (hamiltonianCycle != null) {
                System.out.println(name + "'s graph has a Hamiltonian cycle: " + hamiltonianCycle.getVertexList());
            }
        } else {
            System.out.println(name + "'s graph does not satisfy Ore's condition.");
        }
    }

    public void run() {
        while (running) {
            synchronized (game) {
                while (game.getCurrentPlayer() != playerNumber) {
                    try {
                        game.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Tile tile = game.extractTile();
                if (tile == null) {
                    running = false;
                    game.playerFinished();
                } else {
                    addTile(tile);
                    System.out.println(name + " picked tile (" + tile.first() + ", " + tile.second() + ").");
                }
                game.nextPlayer();
                game.notifyAll();
            }
        }
    }
}
