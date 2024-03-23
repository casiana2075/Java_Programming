package org.example;

import java.time.LocalDate;
import java.util.Map;

public class Museum extends Attraction implements Visitable,Payable {
    private Map<LocalDate,TimeInterval> timetable;
    double ticketprice;

    public Museum(String name,Map<LocalDate, TimeInterval> timetable){
        super(name, timetable);
    }

    @Override
    public void setPrice(double price) {

    }
    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public double getTicketPrice(){
        return this.ticketprice;
    }

    public void setTicketPrice(double price){
        this.ticketprice = price;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Museum Timetable\n\nDay          Hours");
        timetable.forEach((k, v) -> sb.append(("\n" + k.getDayOfWeek() + "     " + v)));
        return sb.toString();
    }


    @Override
    public void isVisitable() {

    }
}
