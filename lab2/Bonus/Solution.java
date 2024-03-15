package org.example;
import java.util.ArrayList;
import java.util.List;
class Solution {
    private final Problem problem;

    public Solution(Problem problem) {
        this.problem = problem;
    }

    public void solveProblem() {
        List<Vehicle> vehicles = problem.getVehicles();
        List<Client> unassignedClients = new ArrayList<>(problem.getClients());

        // Get the cost matrix
        int[][] costMatrix = problem.getShortestPaths();

        for (Vehicle vehicle : vehicles) {
            Tour tour = new Tour(vehicle);
            tour.setDepot(vehicle.getDepot());

            // Start from the depot
            Depot currentLocation = vehicle.getDepot();

            while (!unassignedClients.isEmpty()) {
                Client nearestClient = null;
                double minDistance = Double.MAX_VALUE;

                // Find the nearest unassigned client to the current location using the cost matrix
                for (Client client : unassignedClients) {
                    int clientIndex = problem.getClients().indexOf(client);
                    double distance = costMatrix[0][clientIndex + 1]; // Skip depot in the matrix
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestClient = client;
                    }
                }

                if (nearestClient != null) {
                    tour.addClient(nearestClient);
                    unassignedClients.remove(nearestClient);
                    // No need to update currentLocation as there is only one depot
                } else {
                    break; // no more unassigned clients
                }
            }
            System.out.println(tour);
        }
    }

}