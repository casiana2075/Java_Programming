package org.server;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.graph4j.Digraph;
import org.graph4j.Edge;
import org.graph4j.GraphBuilder;
import org.graph4j.support.Tournament;
import org.graph4j.util.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ScheduleGenerator {
    public void generateSchedule(int n, int p, int d) {
        Model model = new Model("Tournament Schedule");

        //creating the variables
        IntVar[][][] x = new IntVar[n][n][d];
        for (int player1 = 0; player1 < n; player1++) {
            for (int player2 = 0; player2 < n; player2++) {
                for (int day = 0; day < d; day++) {
                    x[player1][player2][day] = model.intVar("x[" + player1 + "][" + player2 + "][" + day + "]", 0, 1);
                }
            }
        }

        //adding the constraints to play once against each other
        for (int player1 = 0; player1 < n; player1++) {
            for (int player2 = 0; player2 < n; player2++) {
                if (player1 != player2) {
                    IntVar[] sumVars1 = new IntVar[d];
                    for (int day = 0; day < d; day++) {
                        sumVars1[day] = x[player1][player2][day];
                    }
                    model.sum(sumVars1, "=", 1).post(); //players play once against each other
                    for (int k = 0; k < d; k++) {
                        model.arithm(x[player1][player2][k], "=", x[player2][player1][k]).post(); //symmetry
                    }
                }
            }
            for (int day = 0; day < d; day++) {
                IntVar[] sumVars2 = new IntVar[n];
                for (int player2 = 0; player2 < n; player2++) {
                    sumVars2[player2] = x[player1][player2][day];
                }
                model.sum(sumVars2, "<=", p).post(); //players play at most p games per day
            }
        }

        //computing the solution
        model.getSolver().solve();

        if(!model.getSolver().solve()){
            System.out.println("No solution found");
            return;
        }

        for (int day = 0; day < d; day++) {
            System.out.println("===Day " + (day + 1) + "====");
            for (int player1 = 0; player1 < n; player1++) {
                for (int player2 = player1+1; player2 < n; player2++) {
                    if (x[player1][player2][day].getValue() == 1) {
                        System.out.println("Player " + (player1 + 1) + " vs Player " + (player2 + 1));
                    }
                }
            }
        }
    }

    public Map<Pair<Integer, Integer>, Integer> simulateGames(int n) {
        Map<Pair<Integer, Integer>, Integer> results = new HashMap<>();//pair of players and the winner
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int winner = random.nextBoolean() ? i : j;//chose random the winner
                results.put(new Pair<>(i, j), winner);
            }
        }
        return results;
    }

    public void findSequence(Map<Pair<Integer, Integer>, Integer> results, int n) {

        Digraph<Integer, Edge<Integer>> winnersDigraph = GraphBuilder.empty().buildDigraph();

        for(int i=0;i<n;i++){
            winnersDigraph.addVertex(i);
        }

        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : results.entrySet()) {
            Pair<Integer, Integer> match = entry.getKey();
            int winner = entry.getValue();

            int player1 = match.player1;
            int player2 = match.player2;

            if (winner == player1) {
                Edge edge = new Edge<Integer>(player1, player2,true);
                winnersDigraph.addEdge(edge);
                System.out.println("Player"+(player1+1)+" won against Player"+(player2+1));
            } else {
                Edge edge = new Edge<Integer>(player2, player1,true);
                winnersDigraph.addEdge(edge);
                System.out.println("Player"+(player2+1)+" won against Player"+(player1+1));
            }
        }

        Tournament tournament = new Tournament(winnersDigraph);
        if(tournament.isTournament()){
            System.out.println("\nThe corresponding winning sequence:");

           Path path = tournament.getHamiltonianPath();
            for(int i=0;i<path.vertices().length-1;i++){
                System.out.print("Player"+(path.vertices()[i]+1)+" , ");
            }
            System.out.print("Player"+(path.vertices()[path.vertices().length-1]+1));
        }
    }

}