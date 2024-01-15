//package com.group_22.timetablemanagement;
////
////import javafx.beans.property.SimpleStringProperty;
////
////public class SchoolDay{
////    private final SimpleStringProperty day;
////    private String classCode;
////    private String fromHours;
////    private String toHours;
////
////    public SchoolDay(String day, String classCode, String fromHours, String toHours) {
////        this.day = new SimpleStringProperty();
////        this.classCode = classCode;
////        this.fromHours = fromHours;
////        this.toHours = toHours;
////    }
////
////    public SchoolDay(String day) {
////        this.day = new SimpleStringProperty(day);
////    }
////
////    public SchoolDay() {
////        this.day = new SimpleStringProperty();
////    }
////
////    public String getDay() {
////        return day.get();
////    }
////
////    public void setDay(String day) {
////        this.day.set(day);
////    }
////
////    public String getClassCode() {
////        return classCode;
////    }
////
////    public void setClassCode(String classCode) {
////        this.classCode = classCode;
////    }
////
////    public String getFromHours() {
////        return fromHours;
////    }
////
////    public void setFromHours(String fromHours) {
////        this.fromHours = fromHours;
////    }
////
////    public String getToHours() {
////        return toHours;
////    }
////
////    public void setToHours(String toHours) {
////        this.toHours = toHours;
////    }
////
////
////}
////-----------------------------------------
//package com.group_22.timetablemanagement;
//
//import javafx.beans.property.SimpleStringProperty;
//
//public class SchoolDay{
//    private final SimpleStringProperty day;
////    private String day;
//
//    private String classCode;
//    private String fromHours;
//    private String toHours;
//    private String columnName;
//
//    public SchoolDay(String day, String classCode, String fromHours, String toHours, String columnName) {
//        this.day = new SimpleStringProperty(day);
//        this.classCode = classCode;
//        this.fromHours = fromHours;
//        this.toHours = toHours;
//        this.columnName = columnName;
//    }
//
//    public SchoolDay(String day) {
//        this.day = new SimpleStringProperty(day);
//    }
//
//    public SchoolDay() {
//        this.day = new SimpleStringProperty();
//    }
//
//    public SimpleStringProperty dayProperty() {
//        return day;
//    }
//
//    public String getDay() {
//        return day.get();
//    }
//
//    public void setDay(String day) {
//        this.day.set(day);
//    }
//
//    public String getClassCode() {
//        return classCode;
//    }
//
//    public void setClassCode(String classCode) {
//        this.classCode = classCode;
//    }
//
//    public String getFromHours() {
//        return fromHours;
//    }
//
//    public void setFromHours(String fromHours) {
//        this.fromHours = fromHours;
//    }
//
//    public String getToHours() {
//        return toHours;
//    }
//
//    public void setToHours(String toHours) {
//        this.toHours = toHours;
//    }
//
//    public String getColumnName() {
//        return columnName;
//    }
//
//    public void setColumnName(String columnName) {
//        this.columnName = columnName;
//    }
//
//
//}

////-----------------------------------------
//package com.group_22.timetablemanagement;
//
//import javafx.beans.property.SimpleStringProperty;
//
//import java.util.Map;
//
//public class SchoolDay{
//    private SimpleStringProperty day;
//    private String classCode;
//    private String fromHours;
//    private String toHours;
////    private String columnName;
//
//    private Map<String, String> timeSlotValues; // Map to store values for each time slot
//    public SchoolDay(String day, String classCode, String fromHours, String toHours, String columnName) {
//        this.day = new SimpleStringProperty(day);
//        this.classCode = classCode;
//        this.fromHours = fromHours;
//        this.toHours = toHours;
//        this.columnName = columnName;
//    }
//
//    public SchoolDay(String day) {
//        this.day = new SimpleStringProperty(day);
//    }
//
//    public SchoolDay() {
//        this.day = new SimpleStringProperty();
//    }
//
//    public SimpleStringProperty dayProperty() {
//        return day;
//    }
//
//    public String getDay() {
//        return day.get();
//    }
//
//    public void setDay(String day) {
//        this.day.set(day);
//    }
//
//    public String getClassCode() {
//        return classCode;
//    }
//
//    public void setClassCode(String classCode) {
//        this.classCode = classCode;
//    }
//
//    public String getFromHours() {
//        return fromHours;
//    }
//
//    public void setFromHours(String fromHours) {
//        this.fromHours = fromHours;
//    }
//
//    public String getToHours() {
//        return toHours;
//    }
//
//    public void setToHours(String toHours) {
//        this.toHours = toHours;
//    }
//
////    public String getColumnName() {
////        return columnName;
////    }
////
////    public void setColumnName(String columnName) {
////        this.columnName = columnName;
////    }
//
//
//    public void setCellValue(int columnFor9AM, String dummyValue) {
//    }
//
//    public Map<String, String> getTimeSlotValues() {
//        return timeSlotValues;
//    }
//
//    public void setTimeSlotValues(Map<String, String> timeSlotValues) {
//        this.timeSlotValues = timeSlotValues;
//    }
//}

/////-----------------------------------------
//package com.group_22.timetablemanagement;
//
//import javafx.beans.property.SimpleStringProperty;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SchoolDay {
//    private SimpleStringProperty day;
//    private String classCode;
//    private String fromHours;
//    private String toHours;
//    private String columnName;
//
////    private Map<String, String> timeSlotValues; // Map to store values for each time slot
//    private Map<Integer, Map<Integer, String>> timeSlotValues = new HashMap<>();
//
//    public SchoolDay(String day, String classCode, String fromHours, String toHours, String columnName) {
//        this.day = new SimpleStringProperty(day);
//        this.classCode = classCode;
//        this.fromHours = fromHours;
//        this.toHours = toHours;
//        this.columnName = columnName;
//        this.timeSlotValues = new HashMap<>();
//    }
//
//    public SchoolDay(String day) {
//        this.day = new SimpleStringProperty(day);
//    }
//
//    public SchoolDay() {
//        this.day = new SimpleStringProperty();
//    }
//
//    public SimpleStringProperty dayProperty() {
//        return day;
//    }
//
//    public String getDay() {
//        return day.get();
//    }
//
//    public void setDay(String day) {
//        this.day.set(day);
//    }
//
//    public String getClassCode() {
//        return classCode;
//    }
//
//    public void setClassCode(String classCode) {
//        this.classCode = classCode;
//    }
//
//    public String getFromHours() {
//        return fromHours;
//    }
//
//    public void setFromHours(String fromHours) {
//        this.fromHours = fromHours;
//    }
//
//    public String getToHours() {
//        return toHours;
//    }
//
//    public void setToHours(String toHours) {
//        this.toHours = toHours;
//    }
//
//    public String getColumnName() {
//        return columnName;
//    }
//
//    public void setColumnName(String columnName) {
//        this.columnName = columnName;
//    }
//
////    public void setCellValue(String columnFor9AM, String dummyValue) {
////        // Set the value for the specified column (e.g., 9:00 AM)
////        timeSlotValues.put(columnFor9AM, dummyValue);
////    }
//
////    public Map<String, String> getTimeSlotValues() {
////        return timeSlotValues;
////    }
////
////    public void setTimeSlotValues(Map<String, String> timeSlotValues) {
////        this.timeSlotValues = timeSlotValues;
////    }
//
//    public void setCellValue(int day, int hour, String value) {
//        timeSlotValues.computeIfAbsent(day, k -> new HashMap<>()).put(hour, value);
//    }
//}


// ----------------------------------------------------------------------
//package com.group_22.timetablemanagement;
//
//import javafx.beans.property.SimpleStringProperty;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SchoolDay {
//    private SimpleStringProperty day;
//    private String classCode;
//    private String fromHours;
//    private String toHours;
//    private String columnName;
//
//    // Map to store values for each time slot
//    private Map<Integer, Map<Integer, String>> timeSlotValues = new HashMap<>();
//
//    public SchoolDay(String day, String classCode, String fromHours, String toHours, String columnName) {
//        this.day = new SimpleStringProperty(day);
//        this.classCode = classCode;
//        this.fromHours = fromHours;
//        this.toHours = toHours;
//        this.columnName = columnName;
//        this.timeSlotValues = new HashMap<>();
//    }
//
//    public SchoolDay(String day) {
//        this.day = new SimpleStringProperty(day);
//    }
//
//    public SchoolDay() {
//        this.day = new SimpleStringProperty();
//    }
//
//    public SimpleStringProperty dayProperty() {
//        return day;
//    }
//
//    public String getDay() {
//        return day.get();
//    }
//
//    public void setDay(String day) {
//        this.day.set(day);
//    }
//
//    public String getClassCode() {
//        return classCode;
//    }
//
//    public void setClassCode(String classCode) {
//        this.classCode = classCode;
//    }
//
//    public String getFromHours() {
//        return fromHours;
//    }
//
//    public void setFromHours(String fromHours) {
//        this.fromHours = fromHours;
//    }
//
//    public String getToHours() {
//        return toHours;
//    }
//
//    public void setToHours(String toHours) {
//        this.toHours = toHours;
//    }
//
//    public String getColumnName() {
//        return columnName;
//    }
//
//    public void setColumnName(String columnName) {
//        this.columnName = columnName;
//    }
//
//    // Set the value for the specified column (day and hour)
//    public void setCellValue(int day, int hour, String value) {
//        timeSlotValues.computeIfAbsent(day, k -> new HashMap<>()).put(hour, value);
//    }
//
//    // Get the value for the specified column (day and hour)
//    public String getCellValue(int day, int hour) {
//        return timeSlotValues.getOrDefault(day, new HashMap<>()).getOrDefault(hour, "");
//    }
//
//    // Example: Set values for columns 2, 3, and 4
////    public void setColumnValues(String value2, String value3, String value4) {
////        setCellValue(2, value2);
////        setCellValue(3, value3);
////        setCellValue(4, value4);
////    }
//}

//——————————————————————————————————————————————————————————————————————————
package com.group_22.timetablemanagement;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class SchoolDay {
    private SimpleStringProperty day;
//    private String classCode = "CMT221";
    private String classCode;
    private String fromHours;
    private String toHours;
    private String columnName;

    private Map<String, String> timeSlotValues = new HashMap<>();

    public SchoolDay(String day, String classCode, String fromHours, String toHours, String columnName) {
        this.day = new SimpleStringProperty(day);
        this.classCode = classCode;
        this.fromHours = fromHours;
        this.toHours = toHours;
        this.columnName = columnName;
        generateTimeSlots();
    }

    public SchoolDay(String day, String classCode) {
        this.day = new SimpleStringProperty(day);
        this.classCode = classCode;
    }


    public SchoolDay(String day) {
        this.day = new SimpleStringProperty(day);
    }

    public SchoolDay() {
        this.day = new SimpleStringProperty();
    }

    public SimpleStringProperty dayProperty() {
        return day;
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private void generateTimeSlots() {
        // Implement your logic to generate time slots based on fromHours and toHours
        // For simplicity, let's assume you want time slots for each hour in between
        int startHour = Integer.parseInt(fromHours.split(":")[0]);
        int endHour = Integer.parseInt(toHours.split(":")[0]);

        for (int hour = startHour; hour <= endHour; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String timeSlot = String.format("%02d:%02d", hour, minute);
                timeSlotValues.put(timeSlot, ""); // Initialize with empty data
            }
        }
    }

    public void setCellValue(String timeSlot, String value) {
        // Set the value for the specified time slot
        timeSlotValues.put(timeSlot, value);
    }

    public String getCellValue(String timeSlot) {
        // Get the value for the specified time slot
        return timeSlotValues.getOrDefault(timeSlot, "");
    }

    public Map<String, String> getTimeSlotValues() {
        return timeSlotValues;
    }
}
