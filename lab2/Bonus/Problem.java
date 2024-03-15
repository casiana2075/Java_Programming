package org.example;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class Problem {
    private List<Depot> depots;
    private List<Client> clients;
    private int[][] shortestPaths;
    private final int[][] randomCostMatrix = new int[][]{
                 //{d1,c1,c2,c3,c4}
            /*d1*/ {0, 1, 0, 2, 0},
            /*c1*/ {1, 0, 3, 0, 4},
            /*c2*/ {0, 3, 0, 0, 0},
            /*c3*/ {2, 0, 0, 0, 5},
            /*c4*/ {0, 4, 0, 5, 0},

    };

    public Problem(Client[] clients, Depot[] depots){
        this.clients=initListIfUnique(clients);
        this.depots=initListIfUnique(depots);
    }

    private <T> ArrayList<T> initListIfUnique(T[] elements) {
        boolean ok = true;
        for (int i = 0; i < elements.length - 1; i++) {
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[i].equals(elements[j])) {
                    System.out.println("Cannot have two same elements.");
                    ok = false;
                    break;
                }
            }
            if (!ok) {
                break;
            }
        }
        if (ok) {
            return new ArrayList<>(Arrays.asList(elements));
        } else {
            return new ArrayList<>();
        }
    }

    private void calculateShortestPaths() {
        int numNodes = 5;
        shortestPaths = new int[numNodes][numNodes];

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                shortestPaths[i][j] = randomCostMatrix[i][j];
            }
        }

        // Floyd-Warshall algorithm to find shrt paths
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (shortestPaths[i][k] != 0 && shortestPaths[k][j] != 0) {
                        // If there is an edge from i to k and from k to j
                        if (shortestPaths[i][j] == 0 || shortestPaths[i][k] + shortestPaths[k][j] < shortestPaths[i][j]) {
                            // Update the shortest distance from i to j
                            shortestPaths[i][j] = shortestPaths[i][k] + shortestPaths[k][j];
                        }
                    }
                }
            }
        }
    }

    public int[][] getShortestPaths() {
        if (shortestPaths == null) {
            calculateShortestPaths();
        }
        return shortestPaths;
    }

    public void setRandomCostMatrix() {

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                if(this.randomCostMatrix[i][j] != 0){
                    this.randomCostMatrix[i][j] = 1 + (int)(Math.random() * 10.0);
                    this.randomCostMatrix[j][i] = this.randomCostMatrix[i][j];
                }
            }
        }
    }

    public void getRandomCostMatrix() {

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                System.out.print(this.randomCostMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Vehicle> getVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        boolean ok= true;
        for(Depot d : depots){
            for(Vehicle v : d.getVehicles()){
                if(!vehicles.contains(v)){
                    vehicles.add(v);
                }
                else {
                    System.out.println("Cannot have two identical vehicles in problem.");
                    ok=false;
                    break;
                }
            }
        }
        if(ok){
            return vehicles;
        }
        else return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Problem{\n" +
                "\tdepots=" + depots +
                ",\n\tclients=" + clients +
                "\n}";
    }
}
