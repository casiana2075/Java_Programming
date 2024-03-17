package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TravelPlan {
    private final Map<Attraction, LocalDate> plan;

    public TravelPlan() {
        plan = new HashMap<>();
    }

    public void addVisit(Attraction attraction, LocalDate visitDate) {
        plan.put(attraction, visitDate);
    }

    public void printTravelPlan() {
        System.out.println("Travel Plan:");
        for (Map.Entry<Attraction, LocalDate> entry : plan.entrySet()) {
            Attraction attraction = entry.getKey();
            LocalDate visitDate = entry.getValue();
            System.out.println("On " + visitDate + ", visit " + attraction.getName());
        }
    }
}
