package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Teacher;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TeacherDao {
    private static final Logger log = Logger.getLogger(TeacherDao.class);

    public Teacher create(Teacher teacher) {
        log.trace("Enter create : " + teacher);
        String sql = "insert into teacher (name, surname, address, position, department_id) values (?, ?, ?, ?, ?);";
        Teacher addedTeacher = new Teacher();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, teacher.getName());
            log.trace("Set teacher name : " + teacher.getName());
            insertStatement.setString(2, teacher.getSurname());
            log.trace("Set teacher surname : " + teacher.getName());
            insertStatement.setString(3, teacher.getAddress());
            log.trace("Set teacher address : " + teacher.getAddress());
            insertStatement.setString(4, teacher.getPosition());
            log.trace("Set teacher position : " + teacher.getPosition());
            insertStatement.setInt(5, teacher.getDepartmentId());
            log.trace("Set teacher department : " + teacher.getDepartmentId());
            insertStatement.execute();
            log.info(teacher + " was created");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    addedTeacher.setId(resultSet.getInt("id"));
                    log.trace("Set teacher id : " + teacher.getId());
                    addedTeacher.setName(resultSet.getString("name"));
                    log.trace("Set teacher name : " + teacher.getName());
                    addedTeacher.setSurname(resultSet.getString("surname"));
                    log.trace("Set teacher surname : " + teacher.getSurname());
                    addedTeacher.setAddress(resultSet.getString("address"));
                    log.trace("Set teacher address : " + teacher.getAddress());
                    addedTeacher.setPosition(resultSet.getString("position"));
                    log.trace("Set teacher position : " + teacher.getPosition());
                    addedTeacher.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Set department id : " + teacher.getDepartmentId());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create teacher");
            throw new DaoException("Can`t added teacher", e);
        }
        log.trace("Exit create");
        return addedTeacher;
    }

    public Teacher findTeacherById(int id) {
        log.trace("Enter findById with id : " + id);
        Teacher foundTeacher = new Teacher();
        SubjectDao subjectDao = new SubjectDao();
        String sql = "select * from teacher where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set teacher id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundTeacher.setId(resultSet.getInt("id"));
                    log.trace("Set teacher id : " + foundTeacher.getId());
                    foundTeacher.setName(resultSet.getString("name"));
                    log.trace("Set teacher name : " + foundTeacher.getName());
                    foundTeacher.setSurname(resultSet.getString("surname"));
                    log.trace("Set teacher surname : " + foundTeacher.getSurname());
                    foundTeacher.setAddress(resultSet.getString("address"));
                    log.trace("Set teacher address : " + foundTeacher.getAddress());
                    foundTeacher.setPosition(resultSet.getString("position"));
                    log.trace("Set teacher position : " + foundTeacher.getPosition());
                    foundTeacher.setDepartmentId(resultSet.getInt("department_id"));
                    log.trace("Set department id : " + foundTeacher.getDepartmentId());
                }
            }
            log.debug(foundTeacher + " was founded");
            foundTeacher.setSubjects(subjectDao.findSubjectsByTeacher(id));
            log.debug("Get subjects from SubjectDao : " + subjectDao.findSubjectsByTeacher(id));
        } catch (SQLException e) {
            log.error("Can`t read teacher");
            throw new DaoException("Can`t read teacher", e);
        }
        log.trace("Exit findTeacherById");
        return foundTeacher;
    }

    public Teacher update(Teacher teacher) {
        log.trace("Enter update : " + teacher);
        Teacher updatedTeacher;
        String sql = "update teacher set name = ?, surname = ?, address = ?, position = ?, department_id = ? " +
                "where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, teacher.getName());
            log.trace("Set teacher name : " + teacher.getName());
            statement.setString(2, teacher.getSurname());
            log.trace("Set teacher surname : " + teacher.getSurname());
            statement.setString(3, teacher.getAddress());
            log.trace("Set teacher address : " + teacher.getAddress());
            statement.setString(4, teacher.getPosition());
            log.trace("Set teacher position : " + teacher.getPosition());
            statement.setInt(5, teacher.getDepartmentId());
            log.trace("Set department id : " + teacher.getDepartmentId());
            statement.setInt(6, teacher.getId());
            log.trace("Set teacher id : " + teacher.getId());
            statement.executeUpdate();
            log.info(teacher + " was updated");
            updatedTeacher = findTeacherById(teacher.getId());
            log.debug(updatedTeacher + " was founded");
        } catch (SQLException e) {
            log.error("Can`t update teacher");
            throw new DaoException("Cant update teacher", e);
        }
        log.trace("Exit update");
        return updatedTeacher;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from teacher where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set teacher id : " + id);
            statement.execute();
            log.info("Teacher with id " + id + " was deleted successful");
        } catch (SQLException e) {
            log.error("Can`t delete teacher");
            throw new DaoException("Can`t delete teacher", e);
        }
        log.trace("Exit deleteById");
    }

    public void addSubject(int teacherId, int subjectId) {
        log.trace("Enter addSubject");
        String sql = "insert into teacher_subject (teacher_id, subject_id) values (?, ?);";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, teacherId);
            log.trace("Set teacher id : " + teacherId);
            statement.setInt(2, subjectId);
            log.trace("Set subject id : " + subjectId);
            statement.execute();
            log.info("Teacher`s subject added. Teacher id : " + teacherId + ". Subject id : " + subjectId);
        } catch (SQLException e) {
            log.error("Can`t add subject to teacher");
            throw new DaoException("Can`t add subject to teacher", e);
        }
        log.trace("Exit addSubject");
    }

    public void removeSubject(int teacherId, int subjectId) {
        log.trace("Enter removeSubject");
        String sql = "delete from teacher_subject where teacher_id = ? and subject_id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            st.setInt(1, teacherId);
            log.trace("Set teacher id : " + teacherId);
            st.setInt(2, subjectId);
            log.trace("Set subject id : " + subjectId);
            st.execute();
            log.info("Teacher`s subject was removed. Teacher id : " + teacherId + ". Subject id : " + subjectId);
        } catch (SQLException e) {
            log.error("Can`t remove teacher`s subject");
            throw new DaoException("Can`t remove teacher`s subject", e);
        }
        log.trace("Exit removeSubject");
    }
}
