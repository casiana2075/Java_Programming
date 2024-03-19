package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.Person.generateRandomPerson;

public class Main {
    public static void main(String[] args) {

        List<Person> persons = Stream.generate(Person::generateRandomPerson)
                .limit(20) // total number of persons
                .collect(Collectors.toList());

        // filter drivers and passengers
        List<Driver> drivers = persons.stream()
                .filter(person -> person instanceof Driver)
                .map(person -> (Driver) person)
                .collect(Collectors.toCollection(LinkedList::new));

        Set<Passenger> passengers = persons.stream()
                .filter(person -> (person instanceof Passenger))
                .map(person -> (Passenger) person)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Filtered Drivers:");
        drivers.stream().sorted((d1, d2) -> d1.getAge()-d2.getAge()).forEach(System.out::println);

        System.out.println("\nFiltered Passengers:");
        passengers.forEach(System.out::println);
    }
}