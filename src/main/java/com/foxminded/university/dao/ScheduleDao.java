package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.Schedule;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ScheduleDao {
    private static final Logger log = Logger.getLogger(ScheduleDao.class);

    public Schedule create(Schedule schedule) {
        log.trace("Enter create : " + schedule);
        String sql = "insert into schedule (location, subject_id, teacher_id, group_id, date) " +
                "values (?, ?, ?, ?, ?);";
        Schedule createdSchedule = new Schedule();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setInt(1, schedule.getLectureHallId());
            log.trace("Set lecture hall id : " + schedule.getLectureHallId());
            insertStatement.setInt(2, schedule.getSubjectId());
            log.trace("Set subject id : " + schedule.getSubjectId());
            insertStatement.setInt(3, schedule.getTeacherId());
            log.trace("Set teacher id : " + schedule.getTeacherId());
            insertStatement.setInt(4, schedule.getGroupId());
            log.trace("Set group id : " + schedule.getGroupId());
            insertStatement.setDate(5, schedule.getDate());
            log.trace("Set date : " + schedule.getDate());
            insertStatement.execute();
            log.info(schedule + " was created successful");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdSchedule.setId(resultSet.getInt("id"));
                    log.trace("Get schedule id : " + schedule.getId());
                    createdSchedule.setId(resultSet.getInt("subject_id"));
                    log.trace("Get subject id in schedule : " + schedule.getId());
                    createdSchedule.setId(resultSet.getInt("teacher_id"));
                    log.trace("Get teacher id in schedule : " + schedule.getId());
                    createdSchedule.setId(resultSet.getInt("group_id"));
                    log.trace("Get subject id in schedule : " + schedule.getId());
                    createdSchedule.setId(resultSet.getInt("location"));
                    log.trace("Get lecture hall id in schedule : " + schedule.getLectureHallId());
                    createdSchedule.setDate(resultSet.getDate("date"));
                    log.trace("Get date : " + schedule.getDate());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create schedule successful");
            throw new DaoException("Can`t create schedule", e);
        }
        log.trace("Exit create");
        return createdSchedule;
    }

    public Schedule findById(int id) {
       log.trace("Enter findById with id : " + id);
        Schedule foundSchedule = new Schedule();
        String sql = "select * from schedule where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set schedule id : " + id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundSchedule.setId(resultSet.getInt("id"));
                    log.trace("Get schedule id : " + foundSchedule.getId());
                    foundSchedule.setSubjectId(resultSet.getInt("subject_id"));
                    log.trace("Get subject id in schedule : " + foundSchedule.getId());
                    foundSchedule.setTeacherId(resultSet.getInt("teacher_id"));
                    log.trace("Get teacher id in schedule : " + foundSchedule.getId());
                    foundSchedule.setGroupId(resultSet.getInt("group_id"));
                    log.trace("Get subject id in schedule : " + foundSchedule.getId());
                    foundSchedule.setLectureHallId(resultSet.getInt("location"));
                    log.trace("Get lecture hall id in schedule : " + foundSchedule.getLectureHallId());
                    foundSchedule.setDate(resultSet.getDate("date"));
                    log.trace("Get date : " + foundSchedule.getLectureHallId());
                }
            }
            log.debug(foundSchedule + " was founded");
        } catch (SQLException e) {
            log.error("Can`t read schedule");
            throw new DaoException("Can`t read schedule", e);
        }
        log.trace("Exit findById");
        return foundSchedule;
    }

    public Schedule update(Schedule schedule) {
        log.trace("Enter update : " + schedule);
        Schedule updatedSchedule = new Schedule();
        String sql = "update schedule set location = ?, subject_id = ?, teacher_id = ?, group_id = ?, date = ? " +
                "where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, schedule.getLectureHallId());
            log.trace("Set lecture hall id : " + schedule.getLectureHallId());
            statement.setInt(2, schedule.getSubjectId());
            log.trace("Set subject id : " + schedule.getSubjectId());
            statement.setInt(3, schedule.getTeacherId());
            log.trace("Set teacher id : " + schedule.getTeacherId());
            statement.setInt(4, schedule.getGroupId());
            log.trace("Set group id : " + schedule.getGroupId());
            statement.setDate(5, schedule.getDate());
            log.trace("Set date : " + schedule.getDate());
            statement.setInt(6, schedule.getId());
            log.trace("Set schedule id : " + schedule.getId());
            statement.executeUpdate();
            log.info(schedule + " updated successful");
            updatedSchedule = findById(schedule.getId());
            log.debug(updatedSchedule + " was founded");
        } catch (SQLException e) {
            log.error("Can`t update schedule successful");
            throw new DaoException("Cant update schedule", e);
        }
        log.trace("Exit update");
        return updatedSchedule;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from schedule where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set schedule id : " + id);
            statement.execute();
            log.info("Schedule with id : " + id + " was removed successful");
        } catch (SQLException e) {
            log.error("Can`t delete schedule successful");
            throw new DaoException("Cant delete schedule", e);
        }
        log.trace("Exit deleteById");
    }
}
