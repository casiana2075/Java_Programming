package org.example;

public abstract class DaoFactory {
    public abstract AuthorDao getAuthorDao();
    public abstract BookDao getBookDao();
    public abstract PublisherDao getPublisherDao();
}
