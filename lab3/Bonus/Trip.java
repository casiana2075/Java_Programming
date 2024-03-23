package org.example;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Trip implements Payable , Comparator<Attraction>{
    double tripPrice;
    private String cityName;
    private LocalDate startDate, endDate;
    List<Attraction> listOfAttractions = new ArrayList<>();

    public Trip(String cityName, LocalDate date1, LocalDate date2) {
        this.cityName = cityName;
        this.startDate = date1;
        this.endDate = date2;
    }
    public List<Attraction> getListOfAttractions() {
        return listOfAttractions;
    }
    public void setListOfAttractions(List<Attraction> listOfAttractions) {
        this.listOfAttractions = listOfAttractions;
    }
    @Override
    public void setPrice(double price) {
        this.tripPrice = price;
    }
    @Override
    public double getPrice() {
        return tripPrice;
    }
    public String getCityName() {
        return cityName;
    }
    @Override
    public double getTicketPrice() {
        return tripPrice;
    }
    public LocalDate getStartDate() { return startDate;}
    public LocalDate getEndDate() { return endDate;}


    public List<Attraction> visitableOnlyLocations(){
        List<Attraction> freeLocations = new ArrayList<>();

        for (Attraction attraction : listOfAttractions) {
            if(attraction instanceof Visitable && !(attraction instanceof Payable)){
                freeLocations.add(attraction);
            }
        }
        Collections.sort(freeLocations,this::compare);

        return freeLocations;
    }


    @Override
    public int compare(Attraction first,Attraction second){

        Iterator<Map.Entry<LocalDate,TimeInterval>> iteratorFirst = first.getTimetable().entrySet().iterator();
        Iterator<Map.Entry<LocalDate,TimeInterval>> iteratorSecond = second.getTimetable().entrySet().iterator();

        while (iteratorFirst.hasNext() && iteratorSecond.hasNext()) {

            Map.Entry<LocalDate,TimeInterval> firstEntry = iteratorFirst.next();
            Map.Entry<LocalDate,TimeInterval> secondEntry = iteratorSecond.next();

            if(firstEntry.getValue().getFirst().getHour()>secondEntry.getValue().getFirst().getHour()){
                return 1;
            }
            else if (firstEntry.getValue().getFirst().getHour()<secondEntry.getValue().getFirst().getHour()){
                return -1;
            }

        }

        if(iteratorFirst.hasNext()&&!iteratorSecond.hasNext()){
            return 1;
        }
        else if(!iteratorFirst.hasNext()&&iteratorSecond.hasNext()){
            return -1;
        }

        return 0;
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
