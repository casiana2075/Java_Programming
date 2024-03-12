package org.example;

public class Concert extends Attraction implements Payable{

    float ticketPrice;
    public Concert(String attractionName) {
        super(attractionName);
    }

    public String getName(){
        return attractionName;
    }

    @Override
    public void setPrice(float price) {
        this.ticketPrice = price;
    }

    @Override
    public float getprice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Concert name: " + attractionName + "\n";
    }
}
