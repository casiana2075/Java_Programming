package org.example;

import jakarta.persistence.Query;
import org.entities.Author;

public class AuthorRepository extends AbstractRepository<Author> implements AuthorDao {
    public AuthorRepository(){
        super(Author.class);
    }

    @Override
    public void create(Author author){
        em.getTransaction().begin();
        em.persist(author);

        em.createNamedQuery("Author.create").setParameter("name",author.getName());
        System.out.println("Author '"+author.getName()+"' was created.");

        em.getTransaction().commit();
    }

    @Override
    public Author findById(Integer id){
        Query query = em.createNamedQuery("Author.findById").setParameter("id",id);
        return (Author)query.getSingleResult();
    }
    @Override
    public Author findByName(String name){
        Query query = em.createNamedQuery("Author.findByName").setParameter("name",name);
        return (Author)query.getSingleResult();
    }
}