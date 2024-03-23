package org.example;

import java.time.LocalDate;
import java.util.Map;

public class Statue extends Attraction implements Visitable{

    public Statue(String attractionName, Map<LocalDate, TimeInterval> newTimetable) {
        super(attractionName, newTimetable);
    }

    @Override
    public void isVisitable() {
        System.out.println("The statue '" + attractionName +"' is visitable.");
    }

    @Override
    public String toString() {
        return "Statue name: " + attractionName + "\n";
    }
}
