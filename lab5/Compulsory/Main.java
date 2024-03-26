package org.example;

import javax.print.Doc;
import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        Main app = new Main();
        app.testRepo();
        //app.testLoadView();

        //person
        var employee = new Person(1001, "Popescu"); //generated constructor
        System.out.println(employee); //toString implementation
        System.out.println(employee.name()); //accesor methods

        //document
        var document = new Document("Image");
        System.out.println(document);
    }


    private void testRepo(){
        var repo = new Repository("C:\\Users\\casia\\OneDrive\\Desktop\\RepositoryFile");
        var service = new RepositoryService();
        service.print(repo);
    }
}