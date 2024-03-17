package org.example;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public abstract class Attraction implements Comparable<Attraction>, Visitable{
    protected final String attractionName;
    protected LocalDate date;
    protected TimeInterval interval;
    protected Map<LocalDate, TimeInterval> timetable;
    public Attraction(String attractionName,  Map<LocalDate, TimeInterval> newTimetable) {
        this.attractionName = attractionName;
        this.timetable = newTimetable;
    }

    @Override
    public Map<LocalDate, TimeInterval> getTimetableI() {
        return timetable;
    }

    public String getName(){
        return attractionName;
    }
    public void getTimetable(){
        System.out.println("Timetable for " + attractionName + ":");
        for (Map.Entry<LocalDate, TimeInterval> entry : timetable.entrySet()) {
            this.date = entry.getKey();
            this.interval = entry.getValue();
            System.out.println("Day: " + this.date.getDayOfWeek() + ", interval: " + this.interval.getFirst() + " - " + this.interval.getSecond());
        }
    }

    public Set<LocalDate> getTimetableKeys() {
        return timetable.keySet();
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
