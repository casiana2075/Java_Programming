package org.solver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.entities.Book;


import java.util.List;

public class BookSelectionSolver {
    public void solve(List<Book> books, int k, int p) {
        Model model = new Model("Book Selection");

        //create binary variables for each book
        IntVar[] x = new IntVar[1000];
        for (int i = 0; i < 1000; i++) {
            x[i] = model.intVar("x[" + i + "]", 0, 1);  //domain is [0,1]
        }

        //add  the constraints to the model
        for (int i = 0; i < 1000; i++) {
            for (int j = i + 1; j < 1000; j++) {
                Book book1 = books.get(i);
                Book book2 = books.get(j);
                if (!book1.getTitle().substring(0, 1).equals(book2.getTitle().substring(0, 1)) ||       //if two books cannot be part of the solution
                        Math.abs(book1.getYear() - book2.getYear()) > p) {
                    model.arithm(x[i], "+", x[j], "<=", 1).post();      //we add the constraint that their variables sum is <= 1
                }
            }
        }
        model.sum(x, ">=", k).post();

        // solve the model
        if (model.getSolver().solve()) {
            for (int i = 0; i < 1000; i++) {
                if (x[i].getValue() == 1) {
                    System.out.println("Selected book: " + books.get(i));
                }
            }
        } else {
            System.out.println("No solution found");
        }
    }
}