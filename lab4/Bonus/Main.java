package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        int numDrivers = 5000;
        int numPassengers = 5000;

        // Generate drivers
        List<Driver> drivers = new ArrayList<>();
        for (int i = 0; i < numDrivers; i++) {
            drivers.add(Person.generateRandomDriver());
        }

        // Generate passengers with a connection probability of 0.1
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            passengers.add(Person.generateRandomPassenger(0.1));
        }

        // Combine drivers and passengers into a single list of persons
        Set<Person> persons = new HashSet<>(drivers);
        persons.addAll(passengers);


        Map<Driver, List<String>> driverDestinations = drivers.stream()
                .collect(Collectors.toMap(driver -> driver, Driver::getListOfDestinations));

        // Group persons by their final destination
        Map<String, List<Person>> finalDestinationMap = persons.stream()
                .collect(Collectors.groupingBy(Person::getFinalDestination));

        System.out.println("Drivers:");
        drivers.stream().sorted(Comparator.comparingInt(Person::getAge)).forEach(System.out::println);

        System.out.println("\nPassengers:");
        passengers.forEach(System.out::println);

        System.out.println("\nDrivers and all their destinations:");
        driverDestinations.forEach((driver, destinations) -> {
            System.out.println("Driver: " + driver.getName());
            destinations.forEach(destination -> System.out.println("\tDestination: " + destination));
        });

        System.out.println("\nDestinations and people who want to go there:");
        finalDestinationMap.forEach((destination, people) -> {
            System.out.println("Destination: " + destination);
            people.forEach(person -> System.out.println("\t" + person.getName()));
        });

        Problem problem = new Problem(drivers, passengers);
        problem.solveGreedy();
        System.out.println("\nDrivers and assigned passengers:");
        for (Driver driver : drivers) {
            System.out.println("Driver name: " + driver.getName() + ", passengers: " + driver.getListOfAssignedPassengers());
        }

        problem.solveHopcroft(drivers, passengers);

    }
}
