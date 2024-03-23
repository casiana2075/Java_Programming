package org.example;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public abstract class Attraction implements Comparable<Attraction>, Visitable{
    protected final String attractionName;
    protected LocalDate date;
    protected TimeInterval interval;
    protected Map<LocalDate, TimeInterval> timetable;
    private Colors color;

    public Attraction(String attractionName,  Map<LocalDate, TimeInterval> newTimetable) {
        this.attractionName = attractionName;
        this.timetable = newTimetable;
    }

    public Colors getColor(){
        return this.color;
    }

    public void setColor(Colors color){
        this.color = color;
    }
    @Override
    public Map<LocalDate, TimeInterval> getTimetableI() {
        return timetable;
    }

    public String getName(){
        return this.attractionName;
    }
    public Map<LocalDate,TimeInterval> getTimetable(){
        return this.timetable;
    }

    @Override
    public int compareTo(Attraction o) {
            return this.attractionName.compareTo(o.attractionName);
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "attractionName='" + attractionName + '\'' +
                '}';
    }
}
