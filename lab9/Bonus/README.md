# output for JDBC

    Author{id=5, name='Mark Twain'}
    Book :Notes from a Small Island'
    Publisher :name='Bloomsbury'

    Process finished with exit code 0

# ouput for JPA

    May 08, 2024 11:49:51 PM org.hibernate.jpa.internal.util.LogHelper logPersistenceUnitInformation
    INFO: HHH000204: Processing PersistenceUnitInfo [name: ExamplePU]
    May 08, 2024 11:49:51 PM org.hibernate.Version logVersion
    INFO: HHH000412: Hibernate ORM core version 6.1.2.Final
    May 08, 2024 11:49:51 PM org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl lambda$normalizeConnectionAccessUserAndPass$5
    WARN: HHH90000021: Encountered deprecated setting [javax.persistence.jdbc.user], use [jakarta.persistence.jdbc.user] instead
    May 08, 2024 11:49:51 PM org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl lambda$normalizeConnectionAccessUserAndPass$11
    WARN: HHH90000021: Encountered deprecated setting [javax.persistence.jdbc.password], use [jakarta.persistence.jdbc.password] instead
    May 08, 2024 11:49:51 PM org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl normalizeDataAccess
    WARN: HHH90000021: Encountered deprecated setting [javax.persistence.jdbc.url], use [jakarta.persistence.jdbc.url] instead
    May 08, 2024 11:49:51 PM org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl normalizeDataAccess
    WARN: HHH90000021: Encountered deprecated setting [javax.persistence.jdbc.driver], use [jakarta.persistence.jdbc.driver] instead
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
    WARN: HHH10001002: Using built-in connection pool (not intended for production use)
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
    INFO: HHH10001005: Loaded JDBC driver class: org.postgresql.Driver
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
    INFO: HHH10001012: Connecting with JDBC URL [jdbc:postgresql://localhost:5432/postgres]
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
    INFO: HHH10001001: Connection properties: {password=****, user=postgres}
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl buildCreator
    INFO: HHH10001003: Autocommit mode: false
    May 08, 2024 11:49:51 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PooledConnections <init>
    INFO: HHH10001115: Connection pool size: 20 (min=1)
    May 08, 2024 11:49:52 PM org.hibernate.engine.jdbc.dialect.internal.DialectFactoryImpl logSelectedDialect
    INFO: HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
    May 08, 2024 11:49:53 PM org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
    INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@76396509] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
    May 08, 2024 11:49:53 PM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
    INFO: HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
    Hibernate: select a1_0.id,a1_0.name from authors a1_0 where a1_0.id=?
    Author{id=5, name='Mark Twain'}
    Hibernate: select b1_0.id,b1_0.genres,b1_0.lang,b1_0.number_of_pages,b1_0.publication_date,b1_0.publisher_id,b1_0.title from books b1_0 where b1_0.title=?
    Hibernate: select p1_0.id,p1_0.name from publishers p1_0 where p1_0.id=?
    Book :Notes from a Small Island'
    Hibernate: insert into publishers (name) values (?)
    Publisher :name='Bloomsbury'

    Process finished with exit code 0

# output for solver (second dot)
    Selected book: Book{id=100135, title='The Lord of the Rings: Complete Visual Companion', lang='eng', genres='[Mystery, Thriller]', publication_date=2004-11-15, number_of_pages=224, publisher=9, year = 2004}
    Selected book: Book{id=100246, title='The Broken Wings', lang='eng', genres='[Sci-Fi, Thriller]', publication_date=2003-03-03, number_of_pages=132, publisher=5, year = 2003}
    Selected book: Book{id=100312, title='The Coming Economic Collapse: How You Can Thrive When Oil Costs $200 a Barrel', lang='en-US', genres='[Non-fiction, Romance, Horror]', publication_date=2006-02-01, number_of_pages=211, publisher=1, year = 2006}
    Selected book: Book{id=100461, title='Tyler Florence's Real Kitchen: An Indespensible Guide for Anybody Who Likes to Cook', lang='eng', genres='[Romance, Sci-Fi]', publication_date=2003-03-25, number_of_pages=304, publisher=1, year = 2003}
    Selected book: Book{id=100514, title='The Iliad', lang='en-US', genres='[Horror]', publication_date=2004-04-03, number_of_pages=588, publisher=1, year = 2004}
    Selected book: Book{id=100590, title='The Suppliant Maidens/The Persians/Seven against Thebes/Prometheus Bound', lang='eng', genres='[Thriller, Romance, Fantasy]', publication_date=2004-06-17, number_of_pages=208, publisher=1, year = 2004}
    Selected book: Book{id=100745, title='The Long Goodbye (Philip Marlowe  #6)', lang='eng', genres='[Non-fiction, Sci-Fi, Mystery]', publication_date=2004-09-20, number_of_pages=2, publisher=7, year = 2004}
    Selected book: Book{id=100794, title='The Letters of John and Abigail Adams', lang='eng', genres='[Thriller]', publication_date=2003-12-30, number_of_pages=512, publisher=7, year = 2003}
    Selected book: Book{id=100959, title='The Birth of Tragedy', lang='en-US', genres='[Horror, Thriller]', publication_date=2003-11-27, number_of_pages=160, publisher=6, year = 2003}
    Selected book: Book{id=101048, title='The Longest Journey', lang='eng', genres='[Non-fiction, Sci-Fi]', publication_date=2006-07-27, number_of_pages=396, publisher=2, year = 2006}


# all dots resolved

