package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //timetables
        Map<LocalDate, TimeInterval> churchTimetable = new HashMap<>();
        churchTimetable.put(LocalDate.of(2024, 3,20 ), new TimeInterval(LocalTime.of(9, 0), LocalTime.of(12, 0)));
        churchTimetable.put(LocalDate.of(2024, 3, 18), new TimeInterval(LocalTime.of(14, 0), LocalTime.of(18, 0)));

        Map<LocalDate, TimeInterval> statueTimetable = new HashMap<>();
        statueTimetable.put(LocalDate.of(2024, 3, 17), new TimeInterval(LocalTime.of(10, 0), LocalTime.of(16, 0)));

        Map<LocalDate, TimeInterval> concertTimetable = new HashMap<>();
        concertTimetable.put(LocalDate.of(2024, 3, 18), new TimeInterval(LocalTime.of(19, 0), LocalTime.of(23, 0)));

        // attractions
        Church church1 = new Church("East Church", churchTimetable);
        Church church2 = new Church("Three Crossroads Church", churchTimetable);
        Statue statue1 = new Statue("David Statue", statueTimetable);
        Concert concert1 = new Concert("Electric Castle", concertTimetable);

        // add attractions to list
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(church1);
        attractions.add(church2);
        attractions.add(statue1);
        attractions.add(concert1);

        // trip
        Trip trip1 = new Trip(299.9, "Timisoara", LocalDate.parse("2024-03-12"), LocalDate.parse("2024-03-16"), attractions);

        // sort attractions
        trip1.getListOfAttractions().sort(Attraction::compareTo);

        church1.isVisitable();
        church1.getTimetable();
        church1.getOpeningHour(LocalDate.of(2024, 3, 18));
        church1.getOpeningHour(LocalDate.of(2024, 3, 17));
        System.out.println(trip1);
        trip1.displayFreeVisitableLocations();

        TravelPlan travelPlan = new TravelPlan();
        travelPlan.addVisit(church1, LocalDate.of(2024, 3, 18));
        travelPlan.addVisit(church2, LocalDate.of(2024, 3, 20));
        travelPlan.addVisit(statue1, LocalDate.of(2024, 3, 17));
        travelPlan.addVisit(concert1, LocalDate.of(2024, 3, 18));

        // print travel plan
        travelPlan.printTravelPlan();
    }
}
