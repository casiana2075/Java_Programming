package org.example;

import org.entities.Publisher;

public class PublisherRepository extends AbstractRepository<Publisher> implements PublisherDao {
    public PublisherRepository() {
        super(Publisher.class);
    }

    @Override
    public void create(Publisher publisher) {
        em.getTransaction().begin();
        em.persist(publisher);
        em.getTransaction().commit();
    }
    @Override
    public Publisher findById(Integer id) {
        return em.find(Publisher.class, id);
    }
    @Override
    public Publisher findByName(String name) {
        return em.createNamedQuery("Publisher.findByName", Publisher.class)
                .setParameter("name", name).getResultList().get(0);
    }
}