package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Church church1 = new Church("Sf. Maria");
        Church church2 = new Church("Sf. Nicolae");

        Statue statue1 = new Statue("Statuia Libertatii");

        Concert concert1 = new Concert("Concert ACDC");

        List<Attraction> attractions = new ArrayList<>();

        attractions.add(church1);
        attractions.add(church2);
        attractions.add(statue1);
        attractions.add(concert1);

        Trip trip1 = new Trip(299.9F, "Timisoara", LocalDate.parse("2024-03-12"), attractions);

        trip1.getListOfAttractions().sort(Attraction::compareTo);

        church1.visit();
        church2.visit();
        statue1.visit();
        System.out.println(trip1);
    }
}
