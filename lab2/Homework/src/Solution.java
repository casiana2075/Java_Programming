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

        for (Vehicle vehicle : vehicles) {
            Tour tour = new Tour(vehicle);
            tour.setDepot(vehicle.getDepot());

            Client c1 = findClosestDepotClient(tour, unassignedClients); // nearest client from depot

            while (!unassignedClients.isEmpty()) {
                Client nearestClient = findClosestClientClient(c1, unassignedClients);
                if (nearestClient != null) {
                    tour.addClient(nearestClient);
                    unassignedClients.remove(nearestClient);
                    c1 = nearestClient;
                } else {
                    break;
                }
            }
            System.out.println(tour);
        }
    }

    private Client findClosestDepotClient(Tour tour, List<Client> unassignedClients) {
        double minDistance = Double.MAX_VALUE;
        Client nearestClient = null;
        Depot currentLocation = tour.getDepot();

        for (Client client : unassignedClients) {
            double distance = problem.getTravelTime(currentLocation, client);
            if (distance < minDistance) {
                minDistance = distance;
                nearestClient = client;
            }
        }

        return nearestClient;
    }
    private Client findClosestClientClient(Client client, List<Client> unassignedClients) {
        double minDistance = Double.MAX_VALUE;
        Client nearestClient = null;

        for (Client clientX : unassignedClients) {
            double distance = problem.getTravelTime(client, clientX);
            if (distance < minDistance) {
                minDistance = distance;
                nearestClient = clientX;
            }
        }

        return nearestClient;
    }
}