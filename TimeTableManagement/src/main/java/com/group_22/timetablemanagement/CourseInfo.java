package com.group_22.timetablemanagement;

public class CourseInfo {
    private String courseName;

    private String courseCode;
    private int courseId;

    // Constructor for the ChoiceBox in CourseUpdatesController
    public CourseInfo(int courseId,  String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseId = courseId;
        System.out.println(courseName);
        System.out.println(courseId);
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return courseName; // Display course name in the ChoiceBox
    }
}
