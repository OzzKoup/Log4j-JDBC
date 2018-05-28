package com.foxminded.university.domain;

import java.sql.Date;
import java.util.Objects;

public class Schedule {
    private int id;
    private Date date;
    private int lectureHallId;
    private int subjectId;
    private int groupId;
    private int teacherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLectureHallId() {
        return lectureHallId;
    }

    public void setLectureHallId(int lectureHallId) {
        this.lectureHallId = lectureHallId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", lectureHallId=" + lectureHallId +
                ", subjectId=" + subjectId +
                ", groupId=" + groupId +
                ", teacherId=" + teacherId +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
