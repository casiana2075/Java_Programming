package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface Visitable {
    Map<LocalDate, TimeInterval> getTimetableI();

    void isVisitable();

    default void getOpeningHour(LocalDate day) {
        Map<LocalDate, TimeInterval> timetable = getTimetableI();
        if (timetable.containsKey(day)) {
            LocalTime openingHour = timetable.get(day).getFirst();
            System.out.println("The opening hour on " + day.getDayOfWeek() + " is: " + openingHour + '.');
        } else {
            System.out.println("Closed on " + day.getDayOfWeek() + '.');
        }
    }
}

