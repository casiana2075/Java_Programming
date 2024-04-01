package org.example;

import java.util.ArrayList;
import java.util.List;

public class Driver extends Person{
    private final List<Passenger> listOfAssignedPassengers = new ArrayList<>();
    public Driver(String name, int age, String startingPoint, String destination) {
        super(name, age, startingPoint, destination);
    }

    public List<Passenger> getListOfAssignedPassengers(){
        return listOfAssignedPassengers;
    }

    public void assignPassenger(Passenger passenger){
        this.listOfAssignedPassengers.add(passenger);
    }


}
