package org.example;
import java.time.LocalTime;

public class Drone extends Vehicle {

    private final LocalTime maxFlightDuration;
    public Drone(String name, LocalTime maxTime) {
        super(name);
        this.maxFlightDuration = maxTime;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "name=" + getName() +
                ", maxFlightDuration=" + maxFlightDuration +
                '}';
    }
}
