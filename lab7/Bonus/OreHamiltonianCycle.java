package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HamiltonianCycleAlgorithmBase;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.GraphWalk;

import java.util.ArrayList;
import java.util.List;

public class OreHamiltonianCycle extends HamiltonianCycleAlgorithmBase<Integer, DefaultEdge> {

    @Override //polyn time alg that finds a Hamiltonian cycle in a graph that satisfies Ore's condition
    public GraphPath<Integer, DefaultEdge> getTour(Graph<Integer, DefaultEdge> graph) {
        if (!isOre(graph)) {
            throw new IllegalArgumentException("Graph does not satisfy Ore's condition");
        }

        List<Integer> vertices = new ArrayList<>(graph.vertexSet());
        List<DefaultEdge> edges = new ArrayList<>();

        for (int i = 0; i < vertices.size() - 1; i++) {
            edges.add(graph.getEdge(vertices.get(i), vertices.get(i + 1)));
        }
        edges.add(graph.getEdge(vertices.get(vertices.size() - 1), vertices.get(0)));

        return toTour(graph, edges);
    }

    //converts a list of edges to a graph path
    private GraphPath<Integer, DefaultEdge> toTour(Graph<Integer, DefaultEdge> graph, List<DefaultEdge> edges) {
        List<Integer> vertices = new ArrayList<>();
        for (DefaultEdge edge : edges) {
            vertices.add(graph.getEdgeSource(edge));
        }
        vertices.add(vertices.get(0)); // to form a cycle, the last vertex should be the same as the first one
        double weight = edges.stream().mapToDouble(graph::getEdgeWeight).sum();
        return new GraphWalk<>(graph, vertices.get(0), vertices.get(0), vertices, edges, weight);
    }

    boolean isOre(Graph<Integer, DefaultEdge> graph) {
        int n = graph.vertexSet().size();

        for (Integer v : graph.vertexSet()) {
            for (Integer w : graph.vertexSet()) {
                if (!v.equals(w) && !graph.containsEdge(v, w)) {
                    if (graph.degreeOf(v) + graph.degreeOf(w) < n) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}