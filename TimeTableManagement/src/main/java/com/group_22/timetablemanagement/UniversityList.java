package com.group_22.timetablemanagement;

//public class UniversityList {
//}

public class UniversityList {
    private String UniversityName;
    private int UniversityID;

    public UniversityList(int UniversityID,  String UniversityName) {
        this.UniversityID = UniversityID;
        this.UniversityName = UniversityName;
        System.out.println(UniversityID);
        System.out.println(UniversityName);

    }

    public String getUniversityName() {
        return UniversityName;
    }

    public int getUniversityID() {
        return UniversityID;
    }

    @Override
    public String toString() {
        return UniversityName; // Display course name in the ChoiceBox
    }
}
