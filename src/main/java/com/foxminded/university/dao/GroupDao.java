package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Group;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class GroupDao {
    private static final Logger log = Logger.getLogger(GroupDao.class);

    public Group create(Group group) {
        log.trace("Enter create : " + group);
        String sql = "insert into groups (name, course, department_id) values (?, ?, ?);";
        Group createdGroup = new Group();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, group.getName());
            log.trace("Set group name : " + group.getName());
            insertStatement.setInt(2, group.getCourse());
            log.trace("Set group course : " + group.getCourse());
            insertStatement.setInt(3, group.getDepartmentId());
            log.trace("Set department id in group : " + group.getDepartmentId());
            insertStatement.execute();
            log.info(group + " created");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdGroup.setName(resultSet.getString("name"));
                    log.trace("Get group name : " + createdGroup.getName());
                    createdGroup.setCourse(resultSet.getInt("course"));
                    log.trace("Get group course : " + createdGroup.getCourse());
                    createdGroup.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Get department id in group : " + group.getDepartmentId());
                }
            }
        } catch (SQLException e) {
            log.error("Cant create group");
            throw new DaoException("Can`t create group", e);
        }
        log.trace("Exit create");
        return createdGroup;
    }

    public Group findById(int id) {
        log.trace("Enter findById with id : " + id);
        Group foundGroup = new Group();
        String sql = "select * from groups where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set group id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundGroup.setName(resultSet.getString("name"));
                    log.trace("Get group name : " + foundGroup.getName());
                    foundGroup.setCourse(resultSet.getInt("course"));
                    log.trace("Get group course : " + foundGroup.getCourse());
                    foundGroup.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Get department id in group : " + foundGroup.getDepartmentId());
                }
            }
            log.debug(foundGroup + " was founded");
        } catch (SQLException e) {
            log.error("Cant read group");
            throw new DaoException("Can`t read group", e);
        }
        log.trace("Exit findById");
        return foundGroup;
    }

    public Group update(Group newGroup) {
        log.trace("Enter update : " + newGroup);
        Group updatedGroup = new Group();
        String sql = "update department set name = ?, faculty_id = ? where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, newGroup.getName());
            log.trace("Set group name : " + newGroup.getName());
            statement.setInt(2, newGroup.getCourse());
            log.trace("Set group course : " + newGroup.getCourse());
            statement.setInt(3, newGroup.getId());
            log.trace("Set department id in group : " + newGroup.getDepartmentId());
            statement.executeUpdate();
            log.info(newGroup + " was updated");
            updatedGroup = findById(newGroup.getId());
            log.debug(updatedGroup + " was found");
        } catch (SQLException e) {
            log.error("Cant update group");
            throw new DaoException("Cant update group", e);
        }
        log.trace("Exit update");
        return updatedGroup;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from groups where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set group id : " + id);
            statement.execute();
            log.info("Success removed with id : " + id);
        } catch (SQLException e) {
            log.error("Cant delete group");
            throw new DaoException("Cant delete group", e);
        }
        log.trace("Exit deleteById");
    }
}
