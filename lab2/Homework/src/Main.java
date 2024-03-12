/**
 * Create at least one object of each class.
 * Allows you to create multiple problems with specific parameters and compute theirs solutions
 * You can also allocate vehicles to depots through some methods
 *
 * @return      Through classes the solution of problem(a greedy algorithm that returns the order of clients)
 */

import java.time.LocalTime;

public class Main {
    public static void main(String []args){
        //client
        Client c1 = new Client("Client 1", LocalTime.of(8, 0),  LocalTime.of(12, 30), ClientType.REGULAR);
        Client c2 = new Client("Client 2", LocalTime.of(13, 10),  LocalTime.of(16, 30), ClientType.PREMIUM);
        Client c3 = new Client("Client 3", LocalTime.of(13, 10),  LocalTime.of(16, 30), ClientType.PREMIUM);

        /*vehicle and depots*/
        Depot d1 = new Depot("Romania");
        Depot d2 = new Depot("Germany");
        Depot d3 = new Depot("Austria");

        Vehicle v1 = new Truck("Ford",  1000);
        Vehicle v2 = new Truck("Volvo",  1000);
        Vehicle v3 = new Drone("Delair", LocalTime.of(1,30));

        d1.setVehicles(v2,v3);
        d3.setVehicles(v1);

        Client [] clients = new Client[] {c1, c2, c3};
        Depot [] depots = new Depot[] {d1};

        Problem p1 = new Problem(clients, depots);

        Solution solution = new Solution(p1);

        solution.solveProblem();

        //System.out.println(p1.getVehicles());

        //System.out.println(p1);
    }
}