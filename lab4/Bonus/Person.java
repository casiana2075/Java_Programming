package org.example;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;


public class Person implements Comparable<Person> {
    protected final String name;
    protected final int age;
    protected final String finalDestination;
    protected final String startingPoint;
    protected ArrayList<String> listOfDestinations;

    public Person(String name, int age, String startingPoint, String destination) {
        this.name = name;
        this.age = age;
        this.finalDestination = destination;
        this.startingPoint = startingPoint;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getFinalDestination() {
        return finalDestination;
    }
    public String getStartingPoint() {
        return startingPoint;
    }
    public ArrayList<String> getListOfDestinations() {
        return listOfDestinations;
    }
    protected void setListOfDestinations(ArrayList<String> listOfDestinations) {
        this.listOfDestinations = listOfDestinations;
    }


    public static Person generateRandomPerson(){
        Faker faker = new Faker();
        Random random = new Random();

        String name = faker.name().fullName(); // generate a random name using Faker
        int age = 18 + random.nextInt(43); // ages from 18 to 60

        String destination, startingPoint;
        do {
            destination = Character.toString((char) ('A' + random.nextInt(8))); // destinations from A to H
            startingPoint = Character.toString((char) ('A' + random.nextInt(8))); // starting points from A to H
        } while (destination.equals(startingPoint));

        if (random.nextBoolean()) {
            Driver driver = new Driver(name, age, startingPoint, destination);
            int numberOfPoints = random.nextInt(7);
            ArrayList<String> route = new ArrayList<>();
            route.add(startingPoint);

            for (int i = 0; i < numberOfPoints; i++) {
                String point;
                do {
                    point = Character.toString((char) ('A' + random.nextInt(8))); // points from A to H
                } while (point.equals(startingPoint) || point.equals(destination) || route.contains(point));
                route.add(point);
            }

            route.add(destination);
            driver.setListOfDestinations(route);
            return driver;
        } else {
            return new Passenger(name, age, startingPoint, destination);
        }
    }

    public static Driver generateRandomDriver() {
        Faker faker = new Faker();
        Random random = new Random();

        String name = faker.name().fullName(); // generate a random name using Faker
        int age = 18 + random.nextInt(43); // ages from 18 to 60

        String destination, startingPoint;
        do {
            destination = Character.toString((char) ('A' + random.nextInt(8))); // destinations from A to H
            startingPoint = Character.toString((char) ('A' + random.nextInt(8))); // starting points from A to H
        } while (destination.equals(startingPoint));

        Driver driver = new Driver(name, age, startingPoint, destination);
        int numberOfPoints = random.nextInt(7);
        ArrayList<String> route = new ArrayList<>();
        route.add(startingPoint);

        for (int i = 0; i < numberOfPoints; i++) {
            String point;
            do {
                point = Character.toString((char) ('A' + random.nextInt(8))); // points from A to H
            } while (point.equals(startingPoint) || point.equals(destination) || route.contains(point));
            route.add(point);
        }

        route.add(destination);
        driver.setListOfDestinations(route);
        return driver;
    }

    public static Passenger generateRandomPassenger(double connectionProbability) {
        Faker faker = new Faker();
        Random random = new Random();

        String name = faker.name().fullName();
        int age = 18 + random.nextInt(43);
        String destination, startingPoint, otherDestination;
        do {
            destination = Character.toString((char) ('A' + random.nextInt(8)));
            startingPoint = Character.toString((char) ('A' + random.nextInt(8)));
            otherDestination = Character.toString((char) ('I' + random.nextInt(8)));
        } while (destination.equals(startingPoint));

        Passenger passenger;
        if  (random.nextDouble() <= connectionProbability){
            //I may find a connection
            passenger = new Passenger(name, age, startingPoint, destination);
        } else { // I do not have the connection for sure
            passenger = new Passenger(name, age, startingPoint, otherDestination);
        }

        return passenger;
    }


    @Override
    public String toString() {
        return "Name: '" + name + "\', age: " + age +
                ", start point: '" + startingPoint + '\'' +
                ", final destination: " +'\''+ finalDestination + '\'' +
                ", list of destinations: \'" + listOfDestinations + '\'';
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }

}