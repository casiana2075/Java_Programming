package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class Problem {
    private List<Driver> listOfDrivers = new ArrayList<>();
    private List<Passenger> listOfPassengers = new ArrayList<>();


    public Problem(List<Driver> listOfDrivers, List<Passenger> listOfPassengers) {
        this.listOfDrivers = listOfDrivers;
        this.listOfPassengers = listOfPassengers;
    }


    public List<Driver> getListOfDrivers() {return listOfDrivers;}
    public List<Passenger> getListOfPassengers() {return listOfPassengers;}


    public void solveGreedy() {
        int totalConnections = 0;

        for (Driver driver : listOfDrivers) {
            for (Passenger passenger : listOfPassengers) {
                // if the passenger is not assigned and the driver's destinations contain the passenger's destination
                if (!passenger.isAssigned() && driver.getListOfDestinations().contains(passenger.getFinalDestination())) {
                    // assingn the passenger to the driver
                    driver.assignPassenger(passenger);
                    passenger.setAssigned(true);
                    totalConnections++;
                    break;
                }
            }
        }

        double percentage = (double) totalConnections / listOfDrivers.size() * 100;
        System.out.printf("Percentage of connections (Greedy): %.2f%%\n", percentage);
    }

    public void solveHopcroft(List<Driver> drivers, List<Passenger> passengers) {

        int numberOfMatchs = 0;
        // Create a bipartite graph
        Graph<Person, DefaultEdge> bipartiteGraph = new SimpleGraph<>(DefaultEdge.class);

        // Add drivers and passengers as vertices to the graph
        drivers.forEach(bipartiteGraph::addVertex);
        passengers.forEach(bipartiteGraph::addVertex);

        // Add edges between drivers and passengers based on their connections**start/end
        for (Driver driver : drivers) {
            for (Passenger passenger : passengers) {
                if (driver.getStartingPoint().equals(passenger.getStartingPoint()) &&
                        driver.getFinalDestination().equals(passenger.getFinalDestination())) {
                    bipartiteGraph.addEdge(driver, passenger);
                }
            }
        }

        // Create sets of vertices for each partition
        Set<Person> partition1 = new HashSet<>(drivers);
        Set<Person> partition2 = new HashSet<>(passengers);

        // Use Hopcroft-Karp algorithm to find maximum matching (connections)
        HopcroftKarpMaximumCardinalityBipartiteMatching<Person, DefaultEdge> matching =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(bipartiteGraph, partition1, partition2);
        System.out.println("\nHopcroft Karp Maximum Cardinality Bipartite Matching results: ");
        for (DefaultEdge edge : matching.getMatching()) {
            Person source = bipartiteGraph.getEdgeSource(edge);
            Person target = bipartiteGraph.getEdgeTarget(edge);

            // Check if source belongs to partition1 and target belongs to partition2
            if (partition1.contains(source) && partition2.contains(target)) {
                System.out.println("Matched: " + source + " -> " + target);
                numberOfMatchs++;
            } else {
                // If not, it indicates an invalid matching, so handle accordingly
                System.err.println("Invalid matching: " + source + " -> " + target);
            }
        }
        double percentage = (double) numberOfMatchs / listOfDrivers.size() * 100;
        System.out.printf("Percentage of connections (Hopcroft): %.2f%%\n", percentage);

    }
}
