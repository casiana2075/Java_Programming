package org.example;

import java.util.ArrayList;
import java.util.List;


public class Problem {
    private List<Driver> listOfDrivers = new ArrayList<>();
    private List<Passenger> listOfPassengers = new ArrayList<>();


    public Problem(List<Driver> listOfDrivers, List<Passenger> listOfPassengers) {
        this.listOfDrivers = listOfDrivers;
        this.listOfPassengers = listOfPassengers;
    }


    public List<Driver> getListOfDrivers() {return listOfDrivers;}
    public List<Passenger> getListOfPassengers() {return listOfPassengers;}


    public void solve() {
        // sort drivers based on the number of destinations in descending order
        listOfDrivers.sort((d1, d2) -> d2.getListOfDestinations().size() - d1.getListOfDestinations().size());

        for (Driver driver : listOfDrivers) {
            for (Passenger passenger : listOfPassengers) {
                // if the passenger is not assigned and the driver's destinations contain the passenger's destination
                if (!passenger.isAssigned() && driver.getListOfDestinations().contains(passenger.getFinalDestination())) {
                    // assing the passenger to the driver
                    driver.assignPassenger(passenger);
                    passenger.setAssigned(true);
                    break;
                }
            }
        }
    }

}
