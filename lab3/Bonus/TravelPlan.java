package org.example;

import java.time.LocalDate;
import java.util.*;

public class TravelPlan implements Comparator<Attraction> {

    private int[][] attractionGraph;
    private Trip trip;
    private EnumMap<Colors, LocalDate> daysColouring;

    public TravelPlan(Trip trip) {
        this.trip= trip;
        List<Attraction> attractionsList = trip.getListOfAttractions();
        this.attractionGraph = new int[attractionsList.size()][attractionsList.size()];

        for (int i = 0; i < attractionsList.size(); i++) {
            for (int j = i + 1; j < attractionsList.size(); j++) {
                if (attractionsList.get(i).getClass() == attractionsList.get(j).getClass()) {
                    // if two attractions have the same obj type(two museums)
                    attractionGraph[i][j] = attractionGraph[j][i] = 1; // connect them with an edge
                }
            }
        }
        daysColouring = new EnumMap<>(Colors.class);
        int no_of_days = trip.getEndDate().getDayOfMonth() - trip.getStartDate().getDayOfMonth();

        for (int i = 0; i <= no_of_days; i++) {
            daysColouring.put(Colors.values()[i], trip.getStartDate().plusDays(i));
        }
    }

    public void printAttractionGraph() {
        for (int i = 0; i < attractionGraph.length; i++) {
            for (int j = 0; j < attractionGraph.length; j++) {
                System.out.print(attractionGraph[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void greedyStaticColoring() {
        List<Attraction> sortedAttractions = new ArrayList<>(largestDegreeFirst()); // sorting the elements descending
        // by their degree

        for (Attraction attraction : sortedAttractions) {
            setAvailableColor(attraction);
            System.out.println(attraction.getName() + "->" + attraction.getColor());
        }

    }

    public List<Attraction> largestDegreeFirst() { // we sort the attractions descending by their degree in the graph
        List<Attraction> sortedAttractions = new ArrayList<>(trip.getListOfAttractions());
        Collections.sort(sortedAttractions, this::compare);

        return sortedAttractions;
    }

    public void setAvailableColor(Attraction attr) {
        List<Colors> usedColors = new ArrayList<>();

        for (int i = 0; i < attractionGraph.length; i++) {
            if (attractionGraph[trip.getListOfAttractions().indexOf(attr)][i] == 1) {
                if (trip.getListOfAttractions().get(i).getColor() != null) {
                    usedColors.add(trip.getListOfAttractions().get(i).getColor());
                }
            }
        }

        for (int i = 0; i < Colors.values().length; i++) {
            Colors currentColor = Colors.values()[i];
            LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
            if (!usedColors.contains(currentColor) && attr.getTimetable().containsKey(colourCorrespondingDate)) { // if
                // the colour wasn't used
                attr.setColor(currentColor); // and the attraction is open on the colour's day
                break;
            }
        }

    }

    public void recursiveLargestFirstColouring() {

        for (int j = 0; j < Colors.values().length; j++) {
            colors:
            for (int i = 0; i < attractionGraph.length; i++) {
                if(trip.getListOfAttractions().get(i).getColor()==null){//if the attraction isn't coloured
                    Colors currentColor = Colors.values()[j];
                    LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
                    if(trip.getListOfAttractions().get(i).getTimetable().containsKey(colourCorrespondingDate)){ // and is open on the current colour's day
                        trip.getListOfAttractions().get(i).setColor(Colors.values()[j]);
                        System.out.println(
                                trip.getListOfAttractions().get(i).getName() + "->" + trip.getListOfAttractions().get(i).getColor());
                        recursiveColouring(i, j);
                        break colors;
                    }
                }
            }
        }
    }

    public void recursiveColouring(int vertexIndex, int colourIndex) {

        for (int i = vertexIndex; i < attractionGraph.length; i++) {
            Colors currentColor = Colors.values()[colourIndex];
            LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
            if (vertexIndex != i && attractionGraph[vertexIndex][i] == 0
                    && trip.getListOfAttractions().get(i).getColor() == null &&
                    trip.getListOfAttractions().get(i).getTimetable().containsKey(colourCorrespondingDate)) {

                trip.getListOfAttractions().get(i).setColor(Colors.values()[colourIndex]);
                System.out.println(
                        trip.getListOfAttractions().get(i).getName() + "->" + trip.getListOfAttractions().get(i).getColor());
                recursiveColouring(i, colourIndex);
                break;
            }
        }

    }

    @Override
    public int compare(Attraction atr1, Attraction atr2) {

        int atr1Degree = 0, atr2Degree = 0;
        for (int i = 0; i < attractionGraph.length; i++) {
            if (attractionGraph[trip.getListOfAttractions().indexOf(atr1)][i] == 1) {
                atr1Degree++;
            }
            if (attractionGraph[trip.getListOfAttractions().indexOf(atr2)][i] == 1) {
                atr2Degree++;
            }
        } // Integer.compare returns the ascending order
        return Integer.compare(atr1Degree, atr2Degree) * (-1); // by multiplying with (-1) we get the descending order
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=_=_=Travel Plan=_=_=\n");
        sb.append("\nCity:" + trip.getCityName() + "   Duration:" + trip.getStartDate() + " " + trip.getEndDate() + "\n");

        for (Map.Entry<Colors, LocalDate> entry : daysColouring.entrySet()) {
            sb.append("\nDay:" + entry.getValue() + " Attractions:");
            for (Attraction attraction : trip.getListOfAttractions()) {
                if (attraction.getColor() == entry.getKey())
                    sb.append(attraction.getName() + " | ");
            }
        }

        return sb.toString();
    }
}
