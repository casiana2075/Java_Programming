package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "STUDENT";
    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }

    private Database() {}

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            System.err.println("Error at getting connection from the database:" + ex);
        }
        return null;
    }

    public void addColumns() throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement()) {
            stmt.execute("ALTER TABLE books ADD COLUMN author VARCHAR(255)");
            stmt.execute("ALTER TABLE books ADD COLUMN genres VARCHAR(255)");
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to add columns.");
            System.err.println(e.getMessage());
        }
    }
}
