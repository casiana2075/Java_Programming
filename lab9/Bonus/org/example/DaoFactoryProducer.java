package org.example;

public class DaoFactoryProducer {
    public static DaoFactory getDaoFactory(String factoryType){
        if(factoryType.equalsIgnoreCase("JPA")){
            return new JpaDaoFactory();
        } else if(factoryType.equalsIgnoreCase("JDBC")){
            return new JdbcDaoFactory();
        }
        return null;
    }
}
