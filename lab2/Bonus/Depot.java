package org.example;
import java.util.ArrayList;
import java.util.Arrays;

public class Depot{
    private String name;
    private ArrayList<Vehicle> vehicles;
    private int index;

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
        return this.vehicles;
    }
    public void setVehicles(Vehicle... newVehicle) {
        this.vehicles = new ArrayList<>();
        for(Vehicle v : newVehicle){
            if(!this.vehicles.contains(v)){
                this.vehicles.add(v);
            }
        }
        for(Vehicle vehicle: vehicles){
            vehicle.setDepot(this);
        }
    }


    //toString
    @Override
    public String toString() {
        return "\nDepot{" +
                "name='" + name + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Depot other)) {
            return false;
        }
        return name.equals(other.name) && vehicles.equals(other.vehicles);
    }
}