package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Student;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class StudentDao {
    private static final Logger log = Logger.getLogger(StudentDao.class);

    public Student create(Student student) {
        log.trace("Enter create : " + student);
        String sql = "insert into student (card_number, name, surname, address, groups_id) values (?, ?, ?, ?, ?);";
        Student addedStudent = new Student();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, student.getCardNumber());
            log.trace("Set card number : " + student.getCardNumber());
            insertStatement.setString(2, student.getName());
            log.trace("Set student name : " + student.getName());
            insertStatement.setString(3, student.getSurname());
            log.trace("Set student surname : " + student.getSurname());
            insertStatement.setString(4, student.getAddress());
            log.trace("Set student address : " + student.getAddress());
            insertStatement.setInt(5, student.getGroupId());
            log.trace("Set student group id : " + student.getGroupId());
            insertStatement.execute();
            log.info(student + " was created successful");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    addedStudent.setId(resultSet.getInt("id"));
                    log.trace("Get student id : " + addedStudent.getId());
                    addedStudent.setCardNumber(resultSet.getString("card_number"));
                    log.trace("Get student card number : " + addedStudent.getCardNumber());
                    addedStudent.setSurname(resultSet.getString("name"));
                    log.trace("Get student name : " + addedStudent.getName());
                    addedStudent.setSurname(resultSet.getString("surname"));
                    log.trace("Get student surname : " + addedStudent.getSurname());
                    addedStudent.setSurname(resultSet.getString("address"));
                    log.trace("Get student address : " + addedStudent.getAddress());
                    addedStudent.setGroupId(resultSet.getInt("group_id"));
                    log.trace("Get student group id : " + addedStudent.getGroupId());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create student");
            throw new DaoException("Can`t added student", e);
        }
        log.trace("Exit create");
        return addedStudent;
    }

    public Student findById(int id) {
        log.trace("Enter findById with id : " + id);
        Student foundStudent = new Student();
        String sql = "select * from student where cardid = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set student id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundStudent.setId(resultSet.getInt("id"));
                    log.trace("Get student id : " + foundStudent.getId());
                    foundStudent.setCardNumber(resultSet.getString("card_number"));
                    log.trace("Set student card number : " + foundStudent.getCardNumber());
                    foundStudent.setSurname(resultSet.getString("name"));
                    log.trace("Get student name : " + foundStudent.getName());
                    foundStudent.setSurname(resultSet.getString("surname"));
                    log.trace("Set student surname : " + foundStudent.getSurname());
                    foundStudent.setSurname(resultSet.getString("address"));
                    log.trace("Set student address : " + foundStudent.getAddress());
                    foundStudent.setGroupId(resultSet.getInt("group_id"));
                    log.trace("Set student group id : " + foundStudent.getGroupId());
                }
            }
            log.debug(foundStudent + " was founded");
        } catch (SQLException e) {
            log.error("Can`t read student");
            throw new DaoException("Can`t read student", e);
        }
        log.trace("Exit findById");
        return foundStudent;
    }

    public Student update(Student student) {
        log.trace("Enter update : " + student);
        Student updatedStudent = new Student();
        String sql = "update student set card_number = ?, name = ?, surname = ?, address = ?, groups_id = ? " +
                "where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, student.getCardNumber());
            log.trace("Set card number : " + student.getCardNumber());
            statement.setString(2, student.getName());
            log.trace("Set student name : " + student.getName());
            statement.setString(3, student.getSurname());
            log.trace("Set student surname : " + student.getSurname());
            statement.setString(4, student.getAddress());
            log.trace("Set student address : " + student.getAddress());
            statement.setInt(5, student.getGroupId());
            log.trace("Set student group id : " + student.getGroupId());
            statement.setInt(6, student.getId());
            log.trace("Set student id : " + student.getId());
            statement.executeUpdate();
            log.info(student + " updated successful");
            updatedStudent = findById(student.getId());
            log.debug(updatedStudent + " was found");
        } catch (SQLException e) {
            log.error("Can`t update student");
            throw new DaoException("Cant update student", e);
        }
        log.trace("Exit update");
        return updatedStudent;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from student where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set student id : " + id);
            statement.execute();
            log.info("Student with id : " + id + " was removed");
        } catch (SQLException e) {
            log.error("Can`t delete student");
            throw new DaoException("Cant delete student", e);
        }
        log.trace("Exit deleteById");
    }
}
