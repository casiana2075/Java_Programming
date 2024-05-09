package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Manager {

    private static Manager instance;
    private final EntityManagerFactory emf;

    Manager() {
        emf = Persistence.createEntityManagerFactory("ExamplePU");
    }

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public EntityManagerFactory getFactory() {
        return emf;
    }

}