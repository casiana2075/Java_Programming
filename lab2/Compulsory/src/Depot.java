import java.util.ArrayList;

public class Depot {
    private String name;
    private ArrayList<Vehicle> vehicles;

    public Depot() {
    }

    public Depot(String name) {
        this.name = name;
    }

    //setters & getters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    public void setVehicles(Vehicle ... vehicles) {
        this.vehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            this.vehicles.add(vehicle);
        }
    }

    //toString
    @Override
    public String toString(){ 
        return name + vehicles ; 
    }

}
