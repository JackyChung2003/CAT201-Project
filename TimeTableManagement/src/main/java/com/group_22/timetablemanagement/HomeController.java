package com.group_22.timetablemanagement;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomeController {

    @FXML
    private Button DashboardBtn;

    @FXML
    private Button CustomizeTimeTableBtn;

//    @FXML
//    private ListView<?> DueDatesListView;

    @FXML
    private ListView<String> DueDatesListView;

    @FXML
    private Button EditPersonalBtn;

    @FXML
    private Button EditTimeTableBtn;

    @FXML
    private Button ModifyDueDatesBtn;

    @FXML
    private Button CourseUpdatesBtn1;

    @FXML
    private Button SignOutBtn;

    @FXML
    private ListView<String> UpdateListView;

    @FXML
    private Label WelcomeUserLabel;

    @FXML
    private TableView<SchoolDay> timetable;

    @FXML
    private TableColumn<SchoolDay, String> weekdaysColumn;

    private Stage loginStage;
    private Scene loginScene;

    private Stage stage;

    private Scene scene;

    private Parent root;
    // Store the active button
    private Button activeButton;

    // Variable to hold userID
    private Integer userID;


    // Variable to hold user's full name
    private String fullName;

    // Variable to hold user's role
    private String role;
    FXMLLoader loader = null;

    private Connection connectDB;
    private Integer StudentTeacherID;

    private int startDayIndex = 0;
    private int endDayIndex = 0;
    private int endTimeIndex = 0;
    private int startTimeIndex = 0;
    private int timeInterval = 0;


    List<TimetableClassData> timetableClassDataList = new ArrayList<>();

    private ObservableList<SchoolDay> schoolDay = FXCollections.observableArrayList();

    List<TableColumn<SchoolDay, String>> createdColumns = new ArrayList<>();

    @FXML
    void initialize() {
        ObservableList<String> dueDatesData = fetchDataFromDatabase();


        // Check if DueDatesListView is not null before setting items
        if (DueDatesListView != null) {
            DueDatesListView.getItems().clear();  // Clear existing items
            DueDatesListView.setItems(dueDatesData);
        } else {
            System.out.println("DueDatesListView is null");
        }

        ObservableList<String> UpdatesData = fetchAnnouncementFromDatabase();
        // Check if DueDatesListView is not null before setting items
        if (UpdateListView != null) {
            UpdateListView.getItems().clear();  // Clear existing items
            UpdateListView.setItems(UpdatesData);
        } else {
            System.out.println("UpdateListView is null");
        }

        if (weekdaysColumn != null) {
            weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        } else {
            System.out.println("weekdaysColumn is null");
        }

        // Initialize timetable data
        initializeTimetableData();
    }

    private ObservableList<String> fetchDataFromDatabase() {
        ObservableList<String> dataList = FXCollections.observableArrayList();
        int queryExecutionCounter = 0;

        try {
            // Connect to the database
            Connection connection = JDBCConnection.getConnection();

            String query = "SELECT duedates.* FROM DueDates " +
                    "LEFT JOIN courses ON courses.CourseID = duedates.CourseID " +
                    "LEFT JOIN teacherscourses ON teacherscourses.CourseID = courses.CourseID " +
                    "LEFT JOIN studentcourses ON studentcourses.CourseID = courses.CourseID " +
                    "LEFT JOIN teachers ON teachers.TeacherID = teacherscourses.TeacherID " +
                    "LEFT JOIN students ON students.StudentID = studentcourses.StudentID " +
                    "WHERE (students.UserID = " + userID + " OR teachers.UserID = " + userID + ") " +
                    "AND duedates.duedate >= CURDATE() " +  // Add this condition to filter out past due dates
                    "ORDER BY duedates.duedate";

            System.out.println("Query:" +query);


            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Iterate through the result set and add data to the list
                while (resultSet.next()) {
                    String dueDate = resultSet.getString("DueDate");
                    String dueDateTitle = resultSet.getString("DueDateTitle");
                    String dueDateText = resultSet.getString("DueDateText");
                    String postDate = resultSet.getString("PostDate");
                    String postByName = resultSet.getString("PostByName");

                    if(queryExecutionCounter % 2 == 0){
                        String rowData = String.format("「 %s 」 %nDetails: %s  %nDue Date: %s | Post By: %s",
                                dueDateTitle, dueDateText, dueDate, postByName);

                        System.out.println(rowData);
                        dataList.add(rowData);
                    }
                    queryExecutionCounter++;
                }

                // UI-related operation wrapped in Platform.runLater
                Platform.runLater(() -> {
                    // Update UI here if needed
                    if (DueDatesListView != null) {
                        DueDatesListView.setItems(dataList);
                    } else {
                        System.out.println("DueDatesListView is null");
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
        return dataList;
    }

    private ObservableList<String> fetchAnnouncementFromDatabase() {
        ObservableList<String> dataList = FXCollections.observableArrayList();

        try {
            // Connect to the database
            Connection connection = JDBCConnection.getConnection();

            String query = "SELECT * FROM ( " +
                                "SELECT announcements.*, courses.CourseCode, courses.CourseName " +
                                "FROM Announcements " +
                                "JOIN Courses ON Courses.CourseID = Announcements.CourseID " +
                                "JOIN StudentCourses ON StudentCourses.CourseID = Courses.CourseID " +
                                "JOIN Students ON Students.StudentID = StudentCourses.StudentID " +
                                "WHERE (students.UserID = " + userID + ") " +

                                "UNION " +

                                "SELECT announcements.*, courses.CourseCode, courses.CourseName " +
                                "FROM Announcements " +
                                "JOIN Courses ON Courses.CourseID = Announcements.CourseID " +
                                "JOIN TeachersCourses ON TeachersCourses.CourseID = Courses.CourseID " +
                                "JOIN Teachers ON Teachers.TeacherID = TeachersCourses.TeacherID " +
                                "WHERE (teachers.UserID = " + userID +") " +

                                ") AS combined_result " +
                            "WHERE combined_result.PostDate >= CURDATE() - INTERVAL 7 DAY " +
                            "ORDER BY combined_result.PostDate";

            System.out.println("Query:" +query);

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Iterate through the result set and add data to the list
                while (resultSet.next()) {
                    String courseCode = resultSet.getString("CourseCode");
                    String courseName = resultSet.getString("CourseName");
                    String announcementTitle = resultSet.getString("AnnouncementTitle");
                    String announcementDetails = resultSet.getString("AnnouncementDetails");
                    String postDate = resultSet.getString("PostDate");
                    String postByName = resultSet.getString("PostByName");

                    String rowData = String.format("「 %s 」 %n->%s %s %nDetails: %s  %n Post By: %s| %s ",
                            announcementTitle, courseCode, courseName, announcementDetails, postByName, postDate);

                    System.out.println(rowData);
                    dataList.add(rowData);
                }

                // UI-related operation wrapped in Platform.runLater
                Platform.runLater(() -> {
                    // Update UI here if needed
                    if (UpdateListView != null) {
                        UpdateListView.setItems(dataList);
                    } else {
                        System.out.println("UpdateListView is null");
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return dataList;
    }

    private void initializeTimetableData() {
        // Ensure that userID is set
        if (userID == null) {
            System.out.println("User ID is null. Timetable data cannot be initialized.");
            return;
        }

        initializeTimetableDayFromDatabase();

        initializeTimetableTimeFromDatabase();

        initializeTimetableClassFromDatabase();
    }

    public void fetchTimetableData() {
//        loadTimetableDayFromDatabase();
        initializeTimetableDayFromDatabase();
        initializeTimetableTimeFromDatabase();
        initializeTimetableClassFromDatabase();

    }

    void initializeTimetableDayFromDatabase() {
        // Fetch timetable data  from the database
        // Adjust the SQL query based on your database schema
        String query = "SELECT * FROM TimetableDays WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Clear existing timetable data
//            timetable.getItems().clear();
//            timetable.getColumns().clear();

            // Process the resultSet and populate your timetable
            while (resultSet.next()) {
                startDayIndex = resultSet.getInt("StartDay");
                endDayIndex = resultSet.getInt("EndDay");

                System.out.println("initial SQL: Start day" + startDayIndex);
                System.out.println("initial SQL: End day" + endDayIndex);
            }
            // Use setTimetableDayData method to populate the timetable
            setTimetableDayData(startDayIndex, endDayIndex);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        timetable.refresh();
    }

    // Method to set timetable data based on the retrieved information
    void setTimetableDayData(int startDayIndex, int endDayIndex) {

        schoolDay.clear();

        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        // Validate indices to ensure they are within the expected range
        if (startDayIndex >= 0 && startDayIndex < days.length && endDayIndex >= 0 && endDayIndex < days.length) {
            for (int i = startDayIndex; i <= endDayIndex; i++) {
                SchoolDay newSchoolDay = new SchoolDay(days[i]);
                schoolDay.add(newSchoolDay);
                System.out.println(newSchoolDay);
            }

            timetable.setItems(schoolDay);
            timetable.refresh();
            System.out.println("Done Day");

            // Update the items in cbClassInfoActualDay
            List<String> allDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            List<String> selectedSchoolDays = allDays.subList(startDayIndex, endDayIndex + 1);
            // cbClassInfoActualDay.getItems().setAll(selectedSchoolDays);

            // Store selected school days in the database if needed
            // storeSchoolDaysInDatabase(userID, startDayIndex, endDayIndex);
            // updateOrInsertSchoolDaysInDatabase(userID, startDayIndex, endDayIndex);

            timetable.refresh();
        } else {
            System.out.println("Invalid startDayIndex or endDayIndex");
        }

//        for (int i = startDayIndex; i <= endDayIndex; i++) {
//            SchoolDay newSchoolDay = new SchoolDay(days[i]);
//            schoolDay.add(newSchoolDay);
//            System.out.println(newSchoolDay);
//        }
//
//        timetable.setItems(schoolDay);
//        timetable.refresh();
//        System.out.println("Done Day");
//
//        // Update the items in cbClassInfoActualDay
//        List<String> allDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
////        int fromIndex = allDays.indexOf(cbSchoolDayStartsFrom.getValue());
////        int toIndex = allDays.indexOf(cbSchoolDayStartsTo.getValue());
////        List<String> schoolDays = allDays.subList(fromIndex, toIndex + 1);
//        List<String> schoolDays = allDays.subList(startDayIndex, endDayIndex + 1);
////        cbClassInfoActualDay.getItems().setAll(schoolDays);
//
//        // Store selected school days in the database
////        storeSchoolDaysInDatabase(userID, fromIndex, toIndex);
////        updateOrInsertSchoolDaysInDatabase(userID, startDayIndex, endDayIndex);
//
//        timetable.refresh();
    }

    SchoolDay createEmptySchoolDay() {
        return new SchoolDay("");
    }

    void initializeTimetableTimeFromDatabase() {
        // Fetch timetable time  from the database
        String query = "SELECT * FROM TimetableTime WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the resultSet and populate your timetable
            while (resultSet.next()) {
                startTimeIndex = resultSet.getInt("StartTime");
                endTimeIndex = resultSet.getInt("EndTime");
                timeInterval = resultSet.getInt("IntervalTime");

                System.out.println("initial SQL: Start time" + startTimeIndex);
                System.out.println("initial SQL: End time" + endTimeIndex);
                System.out.println("initial SQL: time interval" + timeInterval);
            }
            // Use setTimetableDayData method to populate the timetable
            setTimetableTimeData(startTimeIndex, endTimeIndex, timeInterval);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTimetableTimeData(int startTimeIndex, int endTimeIndex, int timeInterval) {

        timetable.getColumns().removeAll(createdColumns);
        createdColumns.clear();

        for (int i = startTimeIndex; i < endTimeIndex; i++) {
            for (int j = 0; j < 60; j += timeInterval) {
                int endMinute = j + timeInterval;

                if (i == startTimeIndex && endMinute >= 60) {
                    // Skip creating columns beyond the specified end time
                    continue;
                }

                // Calculate start and end time
                String startTime = String.format("%02d:%02d", i, j);
                String endTime = String.format("%02d:%02d", i + (endMinute / 60), endMinute % 60);

                // Create column header
                String columnHeader = startTime + " - " + endTime;

                // Create and add TableColumn
                TableColumn<SchoolDay, String> column = new TableColumn<>(columnHeader);
                timetable.getColumns().add(column);
                createdColumns.add(column);

                // Add the column header to the ComboBox items
                System.out.println(columnHeader + "is added into drop down");
            }
        }
    }


    void initializeTimetableClassFromDatabase() {
        // Fetch timetable time  from the database
        String query = "SELECT * FROM TimetableClasses WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the resultSet and populate your timetable
            while (resultSet.next()) {
                int rowIndex = resultSet.getInt("RowIndex");
                int columnIndex = resultSet.getInt("ColumnIndex");
                int courseId = resultSet.getInt("CourseID");

                // Create an object to represent the timetable class data
                TimetableClassData timetableClassData = new TimetableClassData(courseId, rowIndex, columnIndex);

                // Add the object to the list
                timetableClassDataList.add(timetableClassData);

                System.out.println("initial SQL: Course ID" + courseId);
                System.out.println("initial SQL: Row index" + rowIndex);
                System.out.println("initial SQL: Column index" + columnIndex);
            }
            // Use setTimetableClassData method to populate the timetable
            setTimetableClassData(timetableClassDataList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Define a method to set timetable class data based on the retrieved information
    void setTimetableClassData(List<TimetableClassData> timetableClassDataList) {
        // Iterate through the list and populate the timetable with the retrieved data
        for (TimetableClassData timetableClassData : timetableClassDataList) {
            int rowIndex = timetableClassData.getRowIndex();
            int columnIndex = timetableClassData.getColumnIndex();
            int courseId = timetableClassData.getCourseId();

            // Insert the data into the corresponding cell in the timetable
            // Implement this method based on your requirements
//            updateTimetableCell(rowIndex, columnIndex, /* Add other parameters as needed */);

            String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
            String day = daysOfWeek[rowIndex];

            String classCode = getCourseCodeFromDatabase(courseId);

//            String day = cbClassInfoActualDay.getValue();
//            CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
//            String classCode = selectedCourseInfo.getCourseCode();

            SchoolDay newClassInfo = new SchoolDay(day, classCode);

            // Check if the column index is within the valid range
            if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {

                TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);

                tableColumn.setCellValueFactory(data -> {
                    if (data.getValue().equals(newClassInfo) && data.getTableView().getColumns().indexOf(data.getTableColumn()) == columnIndex) {
                        // Return the new value for the specified cell
                        return new SimpleStringProperty(newClassInfo.getClassCode());
                    } else {
                        // Return the existing value for other cells
                        return new SimpleStringProperty(data.getValue().getClassCode());
                    }
                });

                // Set the cell factory to customize the rendering
                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        int currentRowIndex = getIndex();
                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());

                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
                            setText(newClassInfo.getClassCode());
                        } else {
                            setText(item);
                        }
                    }
                });

                // Refresh the TableView to reflect the changes
                timetable.refresh();
            } else {
                System.out.println("Invalid column index: " + columnIndex);
            }
        }

        // Optionally, refresh the timetable view
        timetable.refresh();
    }

    private String getCourseCodeFromDatabase(int courseId) {
        String courseCode = null;

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT CourseCode FROM Courses WHERE CourseID = ?")) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                courseCode = resultSet.getString("courseCode");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseCode;
    }

    @FXML
    void DashboardBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("TeacherHomePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        System.out.println("Dashboard is clicked");
        homeController.initData(userID, StudentTeacherID,  fullName, role);
        homeController.fetchTimetableData();

        System.out.println("Dashboard initData done");
        homeController.initialize();

        System.out.println("Initialize");

        System.out.println(role);

    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
//        // Load the new FXML file
//        if ("Student".equals(role)) {
//            CourseUpdatesBtn1.setVisible(false);
//        }
        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
//        HomeController homeController = loader.getController();
//        homeController.initData(userID, StudentTeacherID,  fullName, role);
        CustomizePageController customizePageController = loader.getController();
        customizePageController.setUserData(userID, StudentTeacherID, fullName, role);


        customizePageController.initializeCourseInfo();
        customizePageController.initializeTimetableDayFromDatabase();
        customizePageController.initializeTimetableTimeFromDatabase();
        customizePageController.initializeTimetableClassFromDatabase();
//        customizePageController.initializeNotExistsCourseInfo();
//        customizePageController.initializeCourseInfo();
    }

//    @FXML
//    void EditTimeTableBtnOnClicked(ActionEvent event) throws IOException {
////        loader = new FXMLLoader(getClass().getResource("EditTimeTable.fxml"));{
////        loader = new FXMLLoader(getClass().getResource("manualPage.fxml"));
//        loader = new FXMLLoader(getClass().getResource("EditTimeTable.fxml"));
////        loader = new FXMLLoader(getClass().getResource("manualPage.fxml"));
//        root = loader.load();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        // Pass any necessary data to the HomeController
////        HomeController homeController = loader.getController();
////        homeController.initData(userID, StudentTeacherID,  fullName, role);
//
//        System.out.println(role);
//    }

    @FXML
    void EditTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("EditTimeTable.fxml"));
        root = loader.load();

        // Pass any necessary data to the EditTimeTableController using the set method
        EditTimeTableController editTimeTableController = loader.getController();
        editTimeTableController.setUserData(userID, StudentTeacherID, fullName, role);
        // Now initialize course names
        editTimeTableController.initializeNotExistsCourseInfo();
        editTimeTableController.initializeCourseInfo();
        editTimeTableController.initializeTimetableDayFromDatabase();
        editTimeTableController.initializeTimetableTimeFromDatabase();
        editTimeTableController.initializeTimetableClassFromDatabase();
//        editTimeTableController.loadTimetableDayFromDatabase();
        System.out.println("EditTimeTableBtn is clicked");
        System.out.println("User ID:" + userID);
        System.out.println("Name:" + fullName);
        System.out.println("Role:" + role);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ModifyDueDatesBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("ModifyDueDates.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID,  fullName, role);

        System.out.println(role);
    }

    @FXML
    void EditPersonalBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("EditPersonalInfo.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID,  fullName, role);
        System.out.println(role);
    }

    @FXML
    void CourseUpdatesBtn1OnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("CourseUpdates.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the CourseUpdatesController using the set method
        CourseUpdatesController courseUpdatesController = loader.getController();
        courseUpdatesController.setUserData(userID, StudentTeacherID, fullName, role);
        // Now initialize course names
        courseUpdatesController.initializeCourseNames();
        System.out.println(userID);
        System.out.println(fullName);
        System.out.println(role);

    }


    @FXML
    void SignOutBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("Sign-In.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCourseUpdatesBtnVisibility(String role) {
        if ("Student".equals(role)) {
            CourseUpdatesBtn1.setVisible(false);
        }
    }



    public void initData(Integer userID, Integer StudentTeacherID, String fullName, String role) {
//        this.fullName = fullName;
        this.StudentTeacherID = StudentTeacherID;
        this.userID = userID;
        this.fullName = fullName;
        this.role = role;

        System.out.println("Studet/Teacher ID:" + StudentTeacherID);
        System.out.println("User ID:" + userID);
        System.out.println("Name:" + fullName);
        System.out.println("Role:" + role);

//        WelcomeUserLabel.setText("Welcome back, " + fullName + "!");
        if ("Student".equals(role)) {
            CourseUpdatesBtn1.setVisible(false);
        }

        // Check if WelcomeUserLabel is null before setting text
        if (WelcomeUserLabel != null) {


            if (fullName != null) {
                WelcomeUserLabel.setText("Welcome back, " + fullName + "!");
            } else {
                // Display something else on subsequent visits
                String[] motivationalQuotes = {
                        "Make today amazing!",
                        "Embrace the challenge, conquer the day.",
                        "Small steps lead to big victories.",
                        "Every moment is a fresh beginning.",
                        "Your dedication today defines your success tomorrow.",
                        "Dream big, work hard, stay focused.",
                        "Seeds of discipline blossom into fields of achievement.",
                        "Believe in your hustle; miracles happen when you do.",
                        "Effort today, triumph tomorrow.",
                        "Success is a journey not a destination.",
                        "It always seems impossible until it's done.",
                        "Success is liking yourself, liking what you do, and liking how you do it.",
                        "Aim for the moon. If you miss, you may hit a star.",
                        "If you cannot do great things, do small things in a great way.",
                        "Success only comes to those who dare to attempt.",
                        "I never dreamed about success. I worked for it.",
                        "If opportunity doesn't knock, build a door.",
                        "You’ve got to learn to leave the table when love’s no longer being served.",
                        "Talk to yourself like someone you love.",
                        "Love yourself first and everything else falls into place.",
                        "If you prioritize yourself, you are going to save yourself.",
                        "It takes courage to grow up and become who you really are.",
                        "You are your best thing.",
                        "You are enough just as you are.",
                        "The most powerful relationship you will ever have is the relationship with yourself.",
                        "The only person you should try to be better than is the person you were yesterday.",
                        "You yourself, as much as anybody in the entire universe, deserve your love and affection.",
                        "To improve is to change; to be perfect is to change often.",
                        "Everyone thinks of changing the world, but no one thinks of changing himself.",
                        "Life isn't about finding yourself. Life is about creating yourself.",
                        "You are never too old to set another goal or to dream a new dream.",
                        "Rise above the storm and you will find the sunshine.",
                        "Vitality shows not only in the ability to persist but in the ability to start over.",
                        "It is never too late to be what you might have been.",
                        "You must be the change you wish to see in the world.",
                        "Mistakes are a fact of life. It is the response to the errors that counts.",
                        "The only person you are destined to become is the person you decide to be.",
                        "The best way out is always through.",
                        "You can't cross the sea merely by standing and staring at the water.",
                        "With the new day comes new strength and new thoughts.",
                        "What we fear of doing most is usually what we most need to do.",
                        "Success is not final, failure is not fatal: it is the courage to continue that counts."
                };


                // Display a random motivational quote
                Random random = new Random();
                String randomQuote = motivationalQuotes[random.nextInt(motivationalQuotes.length)];
                WelcomeUserLabel.setText(randomQuote);
            }
        }
        else {
            // Handle the case when WelcomeUserLabel is null
            System.out.println("WelcomeUserLabel is null");
        }
    }
}
