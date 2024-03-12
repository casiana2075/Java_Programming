package org.example;

import java.time.LocalDate;
import java.util.Date;

public class Church extends Attraction implements Visitable {

    public Church(String attractionName) {
        super(attractionName);
    }

    public String getName(){
        return attractionName;
    }

    @Override
    public void visit() {
        System.out.println("The church '" + attractionName +"' is visitable.");

    }

    @Override
    public String toString() {
        return "Church name: " + attractionName + "\n";
    }
//    @Override
//    public boolean isAvailableDay(LocalDate day) {
//        if(day.isEqual("Monday"))
//    }
}
