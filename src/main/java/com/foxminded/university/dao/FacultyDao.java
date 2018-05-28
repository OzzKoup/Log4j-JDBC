package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Faculty;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class FacultyDao {
    private static final Logger log = Logger.getLogger(FacultyDao.class);

    public Faculty create(Faculty faculty) {
        log.trace("Enter create : " + faculty);
        String sql = "insert into faculty (name) values (?);";
        Faculty createdFaculty = new Faculty();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, faculty.getName());
            log.trace("Set faculty name : " + faculty.getName());
            insertStatement.execute();
            log.info(faculty + " created");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdFaculty.setId(resultSet.getInt("id"));
                    log.trace("Get faculty id : " + createdFaculty.getId());
                    createdFaculty.setName(resultSet.getString("name"));
                    log.trace("Get faculty name : " + createdFaculty.getName());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create faculty");
            throw new DaoException("Can`t create faculty", e);
        }
        log.trace("Exit create");
        return createdFaculty;
    }

    public Faculty findById(int id) {
        log.trace("Enter findById : " + id);
        Faculty foundFaculty = new Faculty();
        String sql = "select * from faculty where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set id  : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundFaculty.setId(resultSet.getInt("id"));
                    log.trace("Get faculty id " + foundFaculty.getId());
                    foundFaculty.setName(resultSet.getString("name"));
                    log.trace("Get faculty name : " + foundFaculty.getName());
                }
            }
            log.debug(foundFaculty + " was founded");
        } catch (SQLException e) {
            log.error("Can`t read faculty");
            throw new DaoException("Can`t read faculty", e);
        }
        log.trace("Exit findById");
        return foundFaculty;
    }

    public Faculty update(Faculty faculty) {
        log.trace("Enter update : " + faculty);
        Faculty updateFaculty = new Faculty();
        String sql = "update faculty set name = ? where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, faculty.getName());
            log.trace("Set faculty name : " + faculty.getName()) ;
            statement.setInt(2, faculty.getId());
            log.trace("Set faculty id : " + faculty.getId());
            statement.executeUpdate();
            log.info(faculty + " updated");
            updateFaculty = findById(faculty.getId());
            log.debug(faculty + " was founded");
        } catch (SQLException e) {
            log.error("Can`t update faculty");
            throw new DaoException("Cant update faculty", e);
        }
        log.trace("Exit update");
        return updateFaculty;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from faculty where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set faculty id : " + id);
            statement.execute();
            log.info("Success removed with id : " + id);
        } catch (SQLException e) {
            throw new DaoException("Cant delete faculty", e);
        }
        log.trace("Exit deleteById");
    }
}
