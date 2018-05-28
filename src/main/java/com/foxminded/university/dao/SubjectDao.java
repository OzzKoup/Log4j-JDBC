package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Subject;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {
    private static final Logger log = Logger.getLogger(SubjectDao.class);

    public Subject create(Subject subject) {
        log.trace("Enter create : " + subject);
        String sql = "insert into subject (name, department_id) values (?, ?);";
        Subject createdSubject = new Subject();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, subject.getName());
            log.trace("Set subject name : " + subject.getName());
            insertStatement.setInt(2, subject.getDepartmentId());
            log.trace("Set department id : " + subject.getDepartmentId());
            insertStatement.execute();
            log.info(subject + " created successful");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdSubject.setId(resultSet.getInt("id"));
                    log.trace("Get subject id : " + createdSubject.getId());
                    createdSubject.setName(resultSet.getString("name"));
                    log.trace("Get subject name : " + createdSubject.getName());
                    createdSubject.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Get department id : " + createdSubject.getDepartmentId());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create subject");
            throw new DaoException("Can`t create subject", e);
        }
        log.trace("Exit create");
        return createdSubject;
    }

    public Subject findId(int id) {
        log.trace("Enter findById with id : " + id);
        Subject foundSubject = new Subject();
        String sql = "select * from subject where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set subject id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundSubject.setId(resultSet.getInt("id"));
                    log.trace("Get subject id : " + foundSubject.getId());
                    foundSubject.setName(resultSet.getString("name"));
                    log.trace("Get subject name : " + foundSubject.getName());
                    foundSubject.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Get department id : " + foundSubject.getDepartmentId());
                }
            }
            log.debug(foundSubject + " was founded");
        } catch (SQLException e) {
            log.error("Can`t read subject");
            throw new DaoException("Can`t read subject", e);
        }
        log.trace("Exit findById");
        return foundSubject;
    }

    public Subject update(Subject subject) {
        log.trace("Enter update : " + subject);
        Subject updatedSubject = new Subject();
        String sql = "update subject set name = ?, department_id = ? where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, subject.getName());
            log.trace("Set subject name : " + subject.getName());
            statement.setInt(2, subject.getDepartmentId());
            log.trace("Set department id : " + subject.getDepartmentId());
            statement.setInt(3, subject.getId());
            log.trace("Set subject id : " + subject.getId());
            statement.executeUpdate();
            log.info(updatedSubject + " was updated successfully");
            updatedSubject = findId(subject.getId());
            log.debug(updatedSubject + " was found");
        } catch (SQLException e) {
            log.error("Can`t update student");
            throw new DaoException("Cant update subject", e);
        }
        log.trace("Exit update");
        return updatedSubject;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from subject where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set subject id : " + id);
            statement.execute();
            log.info("Subject with id : " + id + " was removed");
        } catch (SQLException e) {
            log.error("Can`t delete student");
            throw new DaoException("Can`t delete subject", e);
        }
        log.trace("Exit deleteById");
    }

    public List<Subject> findSubjectsByTeacher(int teacherId) {
        log.trace("Enter findSubjectsByTeacher with id : " + teacherId);
        Subject subject;
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = new ArrayList<>();
        String selectSubjectSql = "select subject.* from subject " +
                "inner join teacher_subject t ON subject.id = t.subject_id " +
                "where teacher_id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement st = connection.prepareStatement(selectSubjectSql)) {
            log.debug("Sql statement : " + selectSubjectSql);
            st.setInt(1, teacherId);
            log.trace("Set teacher id : " + teacherId);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                subject = subjectDao.findId(resultSet.getInt("id"));
                log.trace("Get subjects by teacher id : " + teacherId);
                subjects.add(subject);
                log.debug("Subjects founded : " + subjects.toString());
            }
        } catch (SQLException e) {
            log.error("Can`t read subjects or teacher student");
            throw new DaoException("Can`t read subjects or teacher ", e);
        }
        log.trace("Exit findSubjectsByTeacher");
        return subjects;
    }
}
