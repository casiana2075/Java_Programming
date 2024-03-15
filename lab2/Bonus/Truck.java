package org.example;

public class Truck extends Vehicle{

    private int capacity;
    public Truck(String name,int capacity) {
        super(name);
        this.capacity=capacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "name=" + getName() +
                ", capacity=" + capacity +
                '}';
    }
}
