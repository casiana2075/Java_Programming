import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem {
    private final List<Depot> depots;
    private final List<Client> clients;
    public final int[][] travelTimes = new int[][]{
                                //d1,c1,c2,c3
                     /*d1*/     { 0, 2, 6, 1},
                     /*c1*/     { 2, 0, 3, 5},
                     /*c2*/     { 6, 3, 0, 7},
                     /*c3*/     { 1, 5, 7, 0},
                             };

    public Problem(Client[] clients,Depot[] depots){
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

    public int getTravelTime(Depot depot, Client client) {
        return travelTimes[depots.indexOf(depot)][clients.indexOf(client)+1];
    }

    public int getTravelTime(Client client1, Client client2) {
        return travelTimes[clients.indexOf(client1)+1][clients.indexOf(client2)+1];
    }

    @Override
    public String toString() {
        return "Problem{\n" +
                "\tdepots=" + depots +
                ",\n\tclients=" + clients +
                "\n}";
    }
}
