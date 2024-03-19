package org.example;

import java.util.Random;

public class Person implements Comparable<Person> {
    private final String name;
    private final int age;
    private final String destination;

    public Person(String name, int age, String destination) {
        this.name = name;
        this.age = age;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDestination() {
        return destination;
    }

    static Person generateRandomPerson(){
        Random random = new Random();
        String[] names = {"Ioana","Valentina","Roxana","Casiana","Alex", "Mihai","Vlad","Eugen","Cristi","Alin"};
        String name = names[random.nextInt(names.length)];
        int age = 18 + random.nextInt(43); // ages from 18 to 20
        String destination = Character.toString((char) ('A'+ random.nextInt(5))); //destinatii de la  A la E
        if (random.nextBoolean()) {
            return new Driver(name, age, destination);
        } else {
            return new Passenger(name, age, destination);
        }
    }


    @Override
    public String toString() {
        return "Name: '" + name + "\', age: " + age + ", destination: '" + destination + '\'';
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
