package org.example;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Trip implements Payable{
    double tripPrice;
    String cityName;
    LocalDate startDate;
    LocalDate endDate;
    List<Attraction> listOfAttractions;

    public List<Attraction> getListOfAttractions() {
        return listOfAttractions;
    }

    public Trip(double tripPrice, String cityName, LocalDate date1, LocalDate date2, List<Attraction> listOfAttractions) {
        this.tripPrice = tripPrice;
        this.cityName = cityName;
        this.startDate = date1;
        this.endDate = date2;
        this.listOfAttractions = listOfAttractions;
    }

    public void displayFreeVisitableLocations() {
        List<Attraction> freeVisitableLocations = listOfAttractions.stream()
                .filter(attraction -> attraction instanceof Visitable) //visitable attractions
                .filter(attraction -> !(attraction instanceof Payable)) //payable attractions
                .sorted(Comparator.comparing(attraction -> {
                    Optional<LocalDate> firstDate = attraction.getTimetableKeys().stream().findFirst();
                    return firstDate.orElse(null); // sort by date
                }))
                .collect(Collectors.toList());

        System.out.println("Free visitable locations:");
        int n = 0;
        for (Attraction attraction : freeVisitableLocations) {
            n++;
            System.out.printf("[%s].", Integer.toString(n));
            attraction.getTimetable();
        }
    }

    @Override
    public void setPrice(double price) {
        this.tripPrice = price;
    }

    @Override
    public double getPrice() {
        return tripPrice;
    }

    @Override
    public double getTicketPrice() {
        return tripPrice;
    }

    @Override
    public String toString() {
        return "Trip info :" +
                "\n1.tripPrice=" + tripPrice + ',' +
                "\n2.cityName='" + cityName + '\'' + ',' +
                "\n3.periodOfTime= from " + startDate +" to "+ endDate +  ',' +
                "\n4.listOfAttractions=\n" + listOfAttractions ;
    }
}
