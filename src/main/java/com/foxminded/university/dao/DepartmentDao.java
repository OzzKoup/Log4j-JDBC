package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Department;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DepartmentDao {
    private static final Logger log = Logger.getLogger(DepartmentDao.class);

    public Department create(Department department) {
        log.trace("Enter create " + department);
        String sql = "insert into department (name, faculty_id) values (?, ?);";
        Department createdDepartment = new Department();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, department.getName());
            log.trace("Set department name : " + department.getName());
            insertStatement.setInt(2, department.getFacultyId());
            log.trace("Set department id : " + department.getId());
            insertStatement.execute();
            log.info("Created : " + department);
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdDepartment.setId(resultSet.getInt("id"));
                    log.trace("Get department id " + createdDepartment.getId());
                    createdDepartment.setName(resultSet.getString("name"));
                    log.trace("Get department name : " + createdDepartment.getName());
                    createdDepartment.setFacultyId(resultSet.getInt("faculty_id"));
                    log.trace("Get department faculty id : " + createdDepartment.getFacultyId());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create department");
            throw new DaoException("Can`t create department", e);
        }
        log.trace("Exit create");
        return createdDepartment;
    }

    public Department findById(int id) {
        log.trace("Enter findById with id : " + id);
        Department foundDepartment = new Department();
        String sql = "select * from department where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundDepartment.setName(resultSet.getString("name"));
                    log.trace("Get department name : " + foundDepartment.getName());
                    foundDepartment.setId(resultSet.getInt("id"));
                    log.trace("Get department id " + foundDepartment.getId());
                    foundDepartment.setFacultyId(resultSet.getInt("faculty_id"));
                    log.trace("Get department faculty id " + foundDepartment.getId());
                }
            }
            log.debug(foundDepartment + " was founded");
        } catch (SQLException e) {
            log.error("Can`t read department");
            throw new DaoException("Can`t read department", e);
        }
        log.trace("Exit findById");
        return foundDepartment;
    }

    public Department update(Department newDepartment) {
        log.trace("Enter update : " + newDepartment);
        Department updatedDepartment = new Department();
        String sql = "update department set name = ?, faculty_id = ? where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, newDepartment.getName());
            log.trace("Set department name : " + newDepartment.getName());
            statement.setInt(2, newDepartment.getFacultyId());
            log.trace("Set department faculty id : " + newDepartment.getFacultyId());
            statement.setInt(3, newDepartment.getId());
            log.trace("Set department id : " + newDepartment.getId());
            statement.executeUpdate();
            log.info(newDepartment + " updated");
            updatedDepartment = findById(newDepartment.getId());
            log.debug(updatedDepartment + " was found");
        } catch (SQLException e) {
            log.error("Can`t update department");
            throw new DaoException("Cant update department", e);
        }
        log.trace("Exit update");
        return updatedDepartment;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById id with id : " + id);
        String sql = "delete from department where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set department id : " + id);
            statement.execute();
            log.info("Success removed with id : " + id);
        } catch (SQLException e) {
            log.error("Can`t delete department");
            throw new DaoException("Cant delete department", e);
        }
        log.trace("Exit deleteById");
    }
}
