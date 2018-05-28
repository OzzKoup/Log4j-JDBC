package com.foxminded.university.domain;

import java.io.Serializable;
import java.util.Objects;

public class LectureHall implements Serializable {
    private int id;
    private String numberHall;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberHall() {
        return numberHall;
    }

    public void setNumberHall(String numberHall) {
        this.numberHall = numberHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureHall that = (LectureHall) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LectureHall{" +
                "id=" + id +
                ", numberHall='" + numberHall + '\'' +
                '}';
    }
}
