package org.example;

public abstract class Attraction implements Comparable<Attraction>{
    protected final String attractionName;

    public Attraction(String attractionName) {
        this.attractionName = attractionName;
    }

    @Override
    public int compareTo(Attraction o) {
            return this.attractionName.compareTo(o.attractionName);
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "attractionName='" + attractionName + '\'' +
                '}';
    }

}
