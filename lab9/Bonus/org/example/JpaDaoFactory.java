package org.example;

public class JpaDaoFactory extends DaoFactory{
    @Override
    public AuthorDao getAuthorDao() {return new AuthorRepository();}

    @Override
    public BookDao getBookDao() {
        return new BookRepository();
    }

    @Override
    public PublisherDao getPublisherDao() { return new PublisherRepository(); }
}
