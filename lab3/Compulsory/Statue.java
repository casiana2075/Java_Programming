package org.example;

public class Statue extends Attraction implements Visitable{

    public Statue(String attractionName) {
        super(attractionName);
    }
    public String getName(){
        return attractionName;
    }

    @Override
    public void visit() {
        System.out.println("The statue '" + attractionName +"' is visitable.");
    }

    @Override
    public String toString() {
        return "Statue name: " + attractionName + "\n";
    }
}
