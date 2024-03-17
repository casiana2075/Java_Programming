package org.example;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Concert extends Attraction implements Visitable, Payable{

    private double ticketPrice;

    public Concert(String attractionName, Map<LocalDate, TimeInterval> newTimetable) {
        super(attractionName, newTimetable);
    }

    public String getName(){
        return attractionName;
    }

    @Override
    public void setPrice(double price) {
        this.ticketPrice = price;
    }
    @Override
    public double getPrice() {
        return ticketPrice;
    }

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Concert name: " + attractionName + "\n";
    }

    @Override
    public Map<LocalDate, TimeInterval> getTimetableI() {
        return timetable;
    }

    @Override
    public void isVisitable() {
        System.out.println("The concert '" + attractionName + "' is visitable.");
    }
}
