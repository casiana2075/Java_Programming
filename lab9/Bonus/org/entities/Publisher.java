package org.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "publishers")
@NamedQueries({
        @NamedQuery(name = "Publisher.create",
                query = "insert into Publisher (name) values (:name)"),
        @NamedQuery(name = "Publisher.findById",
                query = "select e from Publisher e where e.id=(:id)"),
        @NamedQuery(name = "Publisher.findByName",
                query = "select e from Publisher e where e.name=(:name)"),
})
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Publisher() {
        // default constructor required by JPA
    }

    public void setName(String name){
        this.name = name;
    }
    public void setId(int id) { this.id = id; }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Publisher :" +
                "name='" + name + '\'' + "\n";
    }
}