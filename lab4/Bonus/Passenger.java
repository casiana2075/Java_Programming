package org.example;

public class Passenger extends Person{
    private boolean assigned;
    public Passenger(String name, int age, String startingPoint, String destination) {
        super(name, age, startingPoint, destination);
    }

    public boolean isAssigned() {
        return this.assigned;
    }

    public void setAssigned( boolean assigned){
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "Name: '" + name + "\', age: " + age +
                ", final destination: " +'\''+ finalDestination + '\'' +
                ", list of destinations: \'" + listOfDestinations + '\'';
    }
}
