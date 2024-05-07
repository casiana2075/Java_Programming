package org.example;

import jakarta.persistence.EntityManager;
import org.entities.Publisher;

public class PublisherRepository extends AbstractRepository<Publisher> {

    public PublisherRepository() {
        super(Publisher.class);
    }

    // Implement Publisher-specific methods here
}