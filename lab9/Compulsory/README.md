#output

    Hibernate: insert into authors (name) values (?)
    Author 'Mark Twain' was created.
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.id=?
    Author{id=1, name='Robert Greene'}
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.name=?
    Author{id=1, name='Robert Greene'}

    Process finished with exit code 0

#all dots resolved
