package com.foxminded.university.dao;

import com.foxminded.university.dao.exceptions.DaoException;
import com.foxminded.university.domain.LectureHall;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class LectureHallDao {
    private static final Logger log = Logger.getLogger(LectureHallDao.class);

    public LectureHall create(LectureHall lectureHall) {
        log.trace("Enter create : " + lectureHall);
        String sql = "insert into lecture_hall (number_hall) values (?);";
        LectureHall createdLectureHall = new LectureHall();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.debug("Sql statement : " + sql);
            insertStatement.setString(1, lectureHall.getNumberHall());
            log.trace("Set lecture hall number : " + lectureHall.getNumberHall());
            insertStatement.execute();
            log.info(lectureHall + " was created");
            try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    createdLectureHall.setId(resultSet.getInt("id"));
                    log.trace("Get lecture hall id: " + lectureHall.getId());
                    createdLectureHall.setNumberHall(resultSet.getString("number_hall"));
                    log.trace("Get lecture hall number : " + lectureHall.getNumberHall());
                }
            }
        } catch (SQLException e) {
            log.error("Can`t create lecture hall");
            throw new DaoException("Can`t create lecture hall", e);
        }
        log.trace("Exit create");
        return createdLectureHall;
    }

    public LectureHall findById(int id) {
        log.trace("Enter findById with id : " + id);
        LectureHall foundLectureHall = new LectureHall();
        String sql = "select * from lecture_hall where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundLectureHall.setId(resultSet.getInt("id"));
                    log.trace("Get lecture hall id: " + foundLectureHall.getId());
                    foundLectureHall.setNumberHall(resultSet.getString("number_hall"));
                    log.trace("Get lecture hall number : " + foundLectureHall.getNumberHall());
                }
            }
            log.debug(foundLectureHall + " was founded");
        } catch (SQLException e) {
            throw new DaoException("Can`t read lecture hall", e);
        }
        log.trace("Exit findById");
        return foundLectureHall;
    }

    public LectureHall update(LectureHall newLectureHall) {
        log.trace("Enter update : " + newLectureHall);
        LectureHall updatedLectureHall = new LectureHall();
        String sql = "update lecture_hall set number_hall = ? where id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setString(1, newLectureHall.getNumberHall());
            log.trace("Set lecture hall number : " + newLectureHall.getNumberHall());
            statement.setInt(2, newLectureHall.getId());
            log.trace("Set lecture hall id : " + newLectureHall.getId());
            statement.executeUpdate();
            log.info(newLectureHall + " was updated successful");
            updatedLectureHall = findById(newLectureHall.getId());
            log.trace(updatedLectureHall + " was founded");
        } catch (SQLException e) {
            throw new DaoException("Cant update lecture hall", e);
        }
        log.trace("Exit update");
        return updatedLectureHall;
    }

    public void deleteById(int id) {
        log.trace("Enter deleteById with id : " + id);
        String sql = "delete from lecture_hall where id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.debug("Sql statement : " + sql);
            statement.setInt(1, id);
            log.trace("Set lecture hall id : " + id);
            statement.execute();
            log.info("Successful removed with id : " + id);
        } catch (SQLException e) {
            log.error("Cant delete lecture hall");
            throw new DaoException("Cant delete lecture hall", e);
        }
        log.trace("Exit deleteById");
    }
}
