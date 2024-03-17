package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Church extends Attraction implements Visitable {
    public Church(String attractionName, Map<LocalDate, TimeInterval> newTimetable) {
        super(attractionName, newTimetable);
    }

    public String getName(){
        return attractionName;
    }

    @Override
    public Map<LocalDate, TimeInterval> getTimetableI() {
        return timetable;
    }

    @Override
    public void isVisitable() {
        System.out.println("The church '" + attractionName +"' is visitable.");
    }

    @Override
    public String toString() {
        return "Church name: " + attractionName + "\n";
    }

}
