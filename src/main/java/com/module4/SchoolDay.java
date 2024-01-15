package com.module4;

import javafx.beans.property.SimpleStringProperty;

public class SchoolDay{
    private final SimpleStringProperty day;
    private String classCode;
    private String fromHours;
    private String toHours;

    public SchoolDay(String day, String classCode, String fromHours, String toHours) {
        this.day = new SimpleStringProperty();
        this.classCode = classCode;
        this.fromHours = fromHours;
        this.toHours = toHours;
    }

    public SchoolDay(String day) {
        this.day = new SimpleStringProperty(day);
    }

    public SchoolDay() {
        this.day = new SimpleStringProperty();
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getFromHours() {
        return fromHours;
    }

    public void setFromHours(String fromHours) {
        this.fromHours = fromHours;
    }

    public String getToHours() {
        return toHours;
    }

    public void setToHours(String toHours) {
        this.toHours = toHours;
    }

    
}