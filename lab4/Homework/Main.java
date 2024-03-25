package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<Person> persons = Stream.generate(Person::generateRandomPerson)
                .limit(15) // total number of persons
                .collect(Collectors.toList());

        // filter drivers and passengers
        List<Driver> drivers = persons.stream()
                .filter(person -> person instanceof Driver)
                .map(person -> (Driver) person)
                .collect(Collectors.toCollection(LinkedList::new));

        List<Passenger> passengers = persons.stream()
                .filter(person -> (person instanceof Passenger))
                .map(person -> (Passenger) person)
                .collect(Collectors.toCollection(LinkedList::new));

        Map<Driver, List<String>> driverDestinations = drivers.stream()
                .collect(Collectors.toMap(driver -> driver, Driver::getListOfDestinations));

        Map<String, List<Person>> finalDestinationMap = persons.stream()
                .collect(Collectors.groupingBy(Person::getFinalDestination));

        System.out.println("Filtered Drivers:");
        drivers.stream().sorted((d1, d2) -> d1.getAge()-d2.getAge()).forEach(System.out::println);

        System.out.println("\nFiltered Passengers:");
        passengers.forEach(System.out::println);

        System.out.println("\nDrivers and their destinations:");
        driverDestinations.forEach((driver, destinations) -> {
            System.out.println("Driver: " + driver.getName());
            destinations.forEach(destination -> System.out.println("\tDestination: " + destination));
        });

        System.out.println("\nDestinations and people who want to go there:");
        finalDestinationMap.forEach((destination, people) -> {
            System.out.println("Destination: " + destination);
            people.forEach(person -> System.out.println("\t" + person.getName()));
        });

        Problem problem = new Problem(drivers,passengers);
        problem.solve();
        System.out.println("\nDrivers and assigned people:");
        for(Driver driver: drivers){
            System.out.println( "Driver name: " + driver.getName() + ", passengers: " + driver.getListOfAssignedPassengers());
        }

    }
}
