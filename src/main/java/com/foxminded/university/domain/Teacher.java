package com.foxminded.university.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Teacher extends Person implements Serializable {
    private String position;
    private int departmentId;
    private List<Subject> subjects;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return departmentId == teacher.departmentId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), departmentId);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                ", position='" + position + '\'' +
                '}';
    }
}
