package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class);
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "123";

    static Connection getConnection() {
        try {
            log.trace("Looking for driver");
            Class.forName("org.postgresql.Driver");
            log.debug("Getting connection");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            log.error("Can`t connect to database");
            throw new DaoException("Cant connect to database", e);
        } catch (ClassNotFoundException e) {
            log.error("Driver not found");
            throw new DaoException("Driver not found", e);
        }
    }
}
