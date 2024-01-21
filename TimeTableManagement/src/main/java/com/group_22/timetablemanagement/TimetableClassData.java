package com.group_22.timetablemanagement;

public class TimetableClassData {
    private int courseId;
    private int rowIndex;
    private int columnIndex;

    public TimetableClassData(int courseId, int rowIndex, int columnIndex) {
        this.courseId = courseId;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}
