package com.group_22.timetablemanagement;

public class StudyTimeConfiguration {
    private String startTime;
    private String endTime;
    private int interval;

    public StudyTimeConfiguration(String startTime, String endTime, int interval) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.interval = interval;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getInterval() {
        return interval;
    }
}
