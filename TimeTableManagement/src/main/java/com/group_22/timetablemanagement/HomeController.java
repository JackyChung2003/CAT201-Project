package com.group_22.timetablemanagement;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class HomeController {

    @FXML
    private Button DashboardBtn;

    @FXML
    private Button CustomizeTimeTableBtn;

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

    private Stage stage;

    private Scene scene;

    private Parent root;

    // Variable to hold userID
    private Integer userID;

    // Variable to hold user's full name
    private String fullName;

    // Variable to hold user's role
    private String role;
    
    private Integer StudentTeacherID;

    private Connection connectDB;

    FXMLLoader loader = null;

    @FXML
    void initialize() {
        // Call the method to initialize course names
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

    @FXML
    void DashboardBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        System.out.println("Dashboard is clicked");
        homeController.initData(userID, StudentTeacherID,  fullName, role);

        System.out.println("Dashboard initData done");
        homeController.initialize();

        System.out.println("Initialize");

        System.out.println(role);
    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID,  fullName, role);
    }

    @FXML
    void EditTimeTableBtnOnClicked(ActionEvent event) throws IOException {
                // Load the new FXML file
                loader = new FXMLLoader(getClass().getResource("EditTimeTable.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // Pass any necessary data to the EditTimeTableController using the set method
                EditTimeTableController editTimeTableController = loader.getController();
                editTimeTableController.setUserData(userID, StudentTeacherID, fullName, role);
                // Now initialize course names
                editTimeTableController.initializeNotExistsCourseInfo();
                editTimeTableController.initializeCourseInfo();
                System.out.println("EditTimeTableBtn is clicked");
                System.out.println("User ID:" + userID);
                System.out.println("Name:" + fullName);
                System.out.println("Role:" + role);
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
