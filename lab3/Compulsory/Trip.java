package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Trip implements Payable{

    float tripPrice;
    String cityName;
    LocalDate date;
    List<Attraction> listOfAttractions;
    public List<Attraction> getListOfAttractions() {
        return listOfAttractions;
    }

    public Trip(float tripPrice, String cityName, LocalDate date, List<Attraction> listOfAttractions) {
        this.tripPrice = tripPrice;
        this.cityName = cityName;
        this.date = date;
        this.listOfAttractions = listOfAttractions;
    }

    @Override
    public void setPrice(float price) {
        this.tripPrice = price;
    }

    @Override
    public float getprice() {
        return tripPrice;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripPrice=" + tripPrice +
                ", cityName='" + cityName + '\'' +
                ", periodOfTime=" + date +
                ", listOfAttractions=" + listOfAttractions +
                '}';
    }
}
