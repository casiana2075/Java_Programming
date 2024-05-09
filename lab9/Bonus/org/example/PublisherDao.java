package org.example;

import org.entities.Publisher;

public interface PublisherDao {
    void create(Publisher publisher);
    Publisher findById(Integer id);
    Publisher findByName(String name);
}
