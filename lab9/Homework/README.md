# output

    Hibernate: insert into publishers (name) values (?)
    Hibernate: insert into books (publisher_id, title) values (?, ?)
    Hibernate: insert into books (publisher_id, title) values (?, ?)
    Hibernate: select b1_0.id,p1_0.id,p1_0.name,b1_0.title from books b1_0 left join publishers p1_0 on p1_0.id=b1_0.publisher_id where b1_0.id=?
    Hibernate: delete from author_book where book_id=?
    Hibernate: select b1_0.id,p1_0.id,p1_0.name,b1_0.title from books b1_0 left join publishers p1_0 on p1_0.id=b1_0.publisher_id where b1_0.id=?
    Hibernate: delete from author_book where book_id=?
    Hibernate: insert into authors (name) values (?)
    Author 'Mark Twain' was created.
    Hibernate: insert into author_book (author_id, book_id) values (?, ?)
    Hibernate: insert into authors (name) values (?)
    Author 'Charles Dickens' was created.
    Hibernate: insert into author_book (author_id, book_id) values (?, ?)
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.id=?
    Author: id=98, name='Mark Twain'}
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.id=?
    Author: id=99, name='Charles Dickens'}
    Book: The Adventures of Tom Sawyer'
    Book: A Tale of Two Cities'
    Publisher name: 'Penguin Books'
    
    Hibernate: update books set publisher_id=?, title=? where id=?
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 1ms
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 1ms
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 0ms
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 0ms
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 0ms
    May 15, 2024 8:55:53 AM org.example.AbstractRepository find
    INFO: Execution time of find query: 0ms
    Hibernate: delete from author_book where author_id=?
    Hibernate: delete from authors where id=?
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.id=?
    Author: id=98, name='Mark Twain'}
    Book: The Adventures of Finn & Jake'
    Book: A Tale of Two Cities'
    Publisher name: 'Penguin Books'

# all dots resolved
