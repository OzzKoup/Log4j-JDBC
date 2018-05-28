package com.foxminded.university.domain;

import java.io.Serializable;
import java.util.Objects;

public class Student extends Person implements Serializable {
    private String cardNumber;
    private int groupId;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(cardNumber, student.cardNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), cardNumber);
    }

    @Override
    public String toString() {
        return "Student{" +
                "cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
