package org.example;

public class JdbcDaoFactory extends DaoFactory{
    @Override
    public AuthorDao getAuthorDao() {
        return new AuthorDaoJdbc();
    }

    @Override
    public BookDao getBookDao() {
        return new BookDaoJdbc();
    }

    @Override
    public PublisherDao getPublisherDao() {
        return new PublisherDaoJdbc();
    }
}
