package com.group_22.timetablemanagement;

//import javafx.collections.FXCollections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class CourseUpdatesController {
    @FXML
    private TextArea DueDateDetailsField;

    @FXML
    private TextField DueDateTitleField;

    @FXML
    private TextArea AnnouncementDetailsField;

    @FXML
    private TextField AnnouncementTitleField;

    @FXML
    private TextArea ClassCancelDetailsField;

    @FXML
    private TextField ClassCancelTitleField;

//    @FXML
//    private TextArea ClassScheduleDetailsField;
//
//    @FXML
//    private TextField ClassScheduleTitleField;

    @FXML
    private Button ClassScheduleBtn1;

    @FXML
    private Button ClassScheduleBtn2;

    @FXML
    private TextArea ClassScheduleDetailsField1;

    @FXML
    private TextArea ClassScheduleDetailsField2;

    @FXML
    private TextField ClassScheduleTitleField1;

    @FXML
    private TextField ClassScheduleTitleField2;

    @FXML
    private Button DueDateSubmitBtn;

    @FXML
    private Button AnnouncementsBtn;

    @FXML
    private Button ClassCancelBtn;

    @FXML
    private Button ClassScheduleBtn;
//
//    @FXML
//    private ChoiceBox<?> CourseDropDownBtn;


//    @FXML
//    private ChoiceBox<String> CourseDropDownBtn;

    @FXML
    private ChoiceBox<CourseInfo> CourseDropDownBtn;

    @FXML
    private Button CourseUpdatesBtn1;

    @FXML
    private Button CustomizeTimeTableBtn1;

    @FXML
    private Button DashboardBtn;

    @FXML
    private DatePicker DueDateBtn;


    @FXML
    private Button EditPersonalBtn;

    @FXML
    private Button EditTimeTableBtn;

    @FXML
    private Spinner<?> EndTimeBtn;

    @FXML
    private ChoiceBox<?> FrequencyDropDownBtn;

    @FXML
    private ChoiceBox<?> ListClassAvailableDropDownBtn;

    @FXML
    private ChoiceBox<?> ListClassCancelDropDownBtn;

    @FXML
    private Button ModifyDueDatesBtn;

    @FXML
    private DatePicker NewScheduleDateBtn;

    @FXML
    private Button SignOutBtn;

    @FXML
    private Spinner<?> StartTimeBtn;


    private Connection connectDB;

    private Integer userID;

    private String fullName;
    private String role;

    private Integer StudentTeacherID;

    private Stage stage;

    private Scene scene;

    private Parent root;
    FXMLLoader loader = null;

    @FXML
    private void initialize() {
        // Call a method to initialize course names
        initializeCourseNames();
    }

    public void setUserData(Integer userID, Integer StudentTeacherID,String fullName, String role) {
        this.StudentTeacherID = StudentTeacherID;
        this.userID = userID;
        this.fullName = fullName;
        this.role = role;

        // Now you can use these values in your CourseUpdatesController as needed
    }

    void initializeCourseNames() {
        connectDB = JDBCConnection.getConnection();
        // Initialize database connection
        try {
            Statement statement = connectDB.createStatement();

            String query = "SELECT * " +
                    "FROM Courses " +
                    "JOIN TeachersCourses ON TeachersCourses.CourseID = Courses.CourseID " +
                    "JOIN Teachers ON Teachers.TeacherID = TeachersCourses.TeacherID " +
                    "WHERE Teachers.UserID = " + userID;

            ResultSet resultSet = statement.executeQuery(query);

//            ObservableList<String> courseNames = FXCollections.observableArrayList();
            ObservableList<CourseInfo> courses = FXCollections.observableArrayList();

            while (resultSet.next()) {
                // Assuming there is a column named "CourseName" in the Courses table

                String courseCode = resultSet.getString("courseCode");
                String courseName = resultSet.getString("CourseName");
                int courseId = resultSet.getInt("CourseID");

                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses.add(courseInfo);
//                courseNames.add(courseName);
//                System.out.println(courseName);
            }

//            // Set the items of your CourseDropDownBtn
//            CourseDropDownBtn.setItems(courseNames);

            // Set the items of your CourseDropDownBtn
            CourseDropDownBtn.setItems(courses);

//            resultSet.close();
//            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DueDateSubmitBtnOnClicked(ActionEvent event) {
        // Retrieve task name, task detail, and due date from UI components
        String taskName = DueDateTitleField.getText();
        String taskDetail = DueDateDetailsField.getText();
        LocalDate dueDate = DueDateBtn.getValue();

        // Get the selected course info from the ChoiceBox
        CourseInfo selectedCourse = CourseDropDownBtn.getValue();
        int courseID = selectedCourse.getCourseId();

        // Get the current date as post date
        LocalDate postDate = LocalDate.now();

        // Get the full name of the user who is posting
        String postByName = fullName;

        System.out.println(courseID);
        System.out.println(taskName);
        System.out.println(taskDetail);
        System.out.println(dueDate);
        System.out.println(postDate);
        System.out.println(postByName);



        // Insert the information into the database
        insertTaskIntoDatabase(courseID, taskName, taskDetail, dueDate, postDate, postByName);

        // Optionally, you can clear the input fields after submitting
        clearDueInputFields();
    }

    private void insertTaskIntoDatabase(int courseID, String taskName, String taskDetail, LocalDate dueDate, LocalDate postDate, String postByName) {
            // Create a SQL query to insert the task information into the database
            String query = "INSERT INTO DueDates (CourseID, DueDateTitle, DueDateText, DueDate, PostDate, PostByName) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the values for the parameters in the prepared statement
            preparedStatement.setInt(1, courseID);
            preparedStatement.setString(2, taskName);
            preparedStatement.setString(3, taskDetail);
            preparedStatement.setDate(4, Date.valueOf(dueDate));
            preparedStatement.setDate(5, Date.valueOf(postDate));
            preparedStatement.setString(6, postByName);

            // Execute the query to insert the data
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    private void clearDueInputFields() {
        // Clear the input fields after submitting
        DueDateTitleField.clear();
        DueDateDetailsField.clear();
        DueDateBtn.setValue(null);
    }

    @FXML
    void AnnouncementsBtnOnClicked(ActionEvent event) {
        // Retrieve announcement title, announcement detail
        String announcementTitle = AnnouncementTitleField.getText();
        String announcementDetail = AnnouncementDetailsField.getText();
//        LocalDate dueDate = DueDateBtn.getValue();

        // Get the selected course info from the ChoiceBox
        CourseInfo selectedCourse = CourseDropDownBtn.getValue();
        int courseID = selectedCourse.getCourseId();

        // Get the current date as post date
        LocalDate postDate = LocalDate.now();

        // Get the full name of the user who is posting
        String postByName = fullName;

        System.out.println(courseID);
        System.out.println(announcementTitle);
        System.out.println(announcementDetail);
        System.out.println(postDate);
        System.out.println(postByName);

        // Insert the information into the database
        insertAnnouncementIntoDatabase(courseID, announcementTitle, announcementDetail, postDate, postByName);

        // Optionally, you can clear the input fields after submitting
        clearAnnouncementInputFields();
    }

    private void insertAnnouncementIntoDatabase(int courseID, String announcementName, String announcementDetail, LocalDate postDate, String postByName) {
        // Create a SQL query to insert the task information into the database
        String query = "INSERT INTO Announcements (CourseID, AnnouncementTitle, AnnouncementDetails, PostDate, PostByName) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the values for the parameters in the prepared statement
            preparedStatement.setInt(1, courseID);
            preparedStatement.setString(2, announcementName);
            preparedStatement.setString(3, announcementDetail);
            preparedStatement.setDate(4, Date.valueOf(postDate));
            preparedStatement.setString(5, postByName);

            // Execute the query to insert the data
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    private void clearAnnouncementInputFields() {
        // Clear the input fields after submitting
        AnnouncementTitleField.clear();
        AnnouncementDetailsField.clear();
    }

    @FXML
    void ClassCancelBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void ClassScheduleBtn1OnClicked(ActionEvent event) {

    }

    @FXML
    void ClassScheduleBtn2OnClicked(ActionEvent event) {

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
        homeController.initData(userID, StudentTeacherID,  fullName, role);

        System.out.println(role);
    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
//        // Load the new FXML file
//        root = FXMLLoader.load(getClass().getResource("CustomizePage.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        // Load the new FXML file
        if ("Student".equals(role)) {
            CourseUpdatesBtn1.setVisible(false);
        }
        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID, fullName, role);




        System.out.println(role);
    }

    @FXML
    void EditTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("EditTimeTable.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID, fullName, role);

        System.out.println(role);
    }

    @FXML
    void ModifyDueDatesBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("ModifyDueDates.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID, fullName, role);

        System.out.println(role);
    }

    @FXML
    void EditPersonalBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("EditPersonalInfo.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID, fullName, role);

        System.out.println(role);
    }

    @FXML
    void CourseUpdatesBtn1OnClicked(ActionEvent event) throws IOException {
//        System.out.println(role);
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
}

