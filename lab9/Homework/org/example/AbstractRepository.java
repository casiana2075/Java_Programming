package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class AbstractRepository<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractRepository.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
        } catch (IOException e) {
            LOGGER.severe("Error setting up logger: " + e.getMessage());
        }
    }

    protected EntityManager em = Manager.getInstance().getFactory().createEntityManager();
    private Class<T> entityClass;

    public AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
    //added update delete
    public void merge(T entity) {
        try {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            LOGGER.severe("Error updating entity: " + e.getMessage());
        }
    }
    public void delete(T entity) {
        try {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            LOGGER.severe("Error deleting entity: " + e.getMessage());
        }
    }
    public void save(T entity) {
        try {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            LOGGER.severe("Error saving entity: " + e.getMessage());
        }
    }

    public T find(Object id) {
        T result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = em.find(entityClass, id);
            long endTime = System.currentTimeMillis();
            LOGGER.info("Execution time of find query: " + (endTime - startTime) + "ms");
        } catch (PersistenceException e) {
            LOGGER.severe("Error finding entity: " + e.getMessage());
        }
        return result;
    }
}