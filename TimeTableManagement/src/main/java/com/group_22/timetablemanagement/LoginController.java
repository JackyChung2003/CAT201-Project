package com.group_22.timetablemanagement;

//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.Parent;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;

//public class LoginController implements Initializable {
public class LoginController {

    @FXML
    private Button JumpSignUpButton;

    @FXML
    private Button SignInBtn1;

    @FXML
    private TextField emailAddress1;

    @FXML
    private PasswordField password;

//    @FXML
//    private TextField fname1;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView timeManagementIcon;

    private Connection connectDB;

    private Stage stage;
    private Stage homeStage;
    private Scene homeScene;


    private Scene scene;

    private Parent root;



//    @Override
//    public  void initialize() {
////        File brandingFile = new File("")
//        connectDB = JDBCConnection.getConnection();
//    }

    // Method to initialize the database connection
//    public void initializeDatabaseConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connectDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcde", "your_username", "your_password");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private String getFullName(String studentFullName, String teacherFullName) {
        return (studentFullName != null) ? studentFullName : teacherFullName;
    }

    private Integer getStudentTeacherID(Integer studentID, Integer teacherID) {
        return (studentID != null) ? studentID : teacherID;
    }

    public void SignInBtn1OnAction (ActionEvent event) {

        String emailText = emailAddress1.getText().trim();
        String passwordText = password.getText().trim();
        connectDB = JDBCConnection.getConnection();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            loginMessageLabel.setText("Email and password are required");
            return;
        }

        try {
            Statement statement = connectDB.createStatement();

//            String query = "SELECT Users.*, Students.StudentName AS StudentFullName, Teachers.TeacherName AS TeacherFullName " +
            String query = "SELECT Users.*, Students.StudentName AS StudentFullName, " +
                    "Teachers.TeacherName AS TeacherFullName, Students.StudentID, " +
                    "Teachers.TeacherID " +
                    "FROM Users " +
                    "LEFT JOIN Students ON Users.UserID = Students.UserID " +
                    "LEFT JOIN Teachers ON Users.UserID = Teachers.UserID " +
                    "WHERE Users.User_email = '" + emailText + "'";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String dbPassword = resultSet.getString("Password");
                String role = resultSet.getString("Role");
                String studentFullName = resultSet.getString("StudentFullName");
                String teacherFullName = resultSet.getString("TeacherFullName");
                Integer userID = resultSet.getInt("UserID");

                Integer StudentID = resultSet.getInt("StudentID");
                Integer TeacherID = resultSet.getInt("TeacherID");

                System.out.println(StudentID);
                System.out.println(TeacherID);


                if (passwordText.equals(dbPassword)) {
                    // Successful login
                    loginMessageLabel.setText("Welcome, " + getFullName(studentFullName, teacherFullName) + "!");


                    // Load the home page
                    FXMLLoader loader = null;

                    // Load based on the role
                    if (role.equals("Student")) {
                        loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
                    } else if (role.equals("Teacher")) {
                        loader = new FXMLLoader(getClass().getResource("TeacherHomePage.fxml"));
                    } else if (role.equals("Admin")) {
                        loader = new FXMLLoader(getClass().getResource("TeacherHomePage.fxml"));
                    }
                    if (loader != null) {
                        Parent root = loader.load();
                        Scene homeScene = new Scene(root);
                        Stage homeStage = new Stage();
                        homeStage.setScene(homeScene);

                        // Pass any necessary data to the controllers
                        Object controller = loader.getController();
                        if (controller instanceof HomeController) {
                            ((HomeController) controller).initData(userID, getStudentTeacherID(StudentID,TeacherID),getFullName(studentFullName, teacherFullName), role);
                            ((HomeController) controller).initialize();
                        }

                        // Show the home stage
                        homeStage.show();

                        // Close the login stage if needed
                        ((Stage) SignInBtn1.getScene().getWindow()).close();
                    } else {
                        loginMessageLabel.setText("Invalid role");
                    }

//                    if (homeStage == null) {
//                        // If home stage is not created yet, create it
//                        loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
//                        root = loader.load();
//                        homeScene = new Scene(root);
//                        homeStage = new Stage();
//                        homeStage.setScene(homeScene);
//                    }
//                    // Show the home stage
//                    homeStage.show();
//
//                    // Pass any necessary data to the HomeController
//                    HomeController homeController = loader.getController();
//                     homeController.initData(fullName);  // You can create a method like initData to pass data
//
//                    // Close the login stage if needed
//                    ((Stage) SignInBtn1.getScene().getWindow()).close();
                } else {
                    loginMessageLabel.setText("Incorrect password");
                }
//                    // Load the home page
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
//                        root = loader.load();
//
//                        // Create a new scene
//                        Scene homeScene = new Scene(root);
//
//                        // Set the stage for the new scene
//                        Stage homeStage = new Stage();
//                        homeStage.setScene(homeScene);
//
//                        // Pass any necessary data to the HomeController
//                        HomeController homeController = loader.getController();
////                        homeController.initData(fullName);  // You can create a method like initData to pass data
//
//                        // Show the new stage (home page)
//                        homeStage.show();
//
//                        // Close the login page if needed
//                        // ((Stage) SignInBtn1.getScene().getWindow()).close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    loginMessageLabel.setText("Incorrect password");
//                }
            } else {
                loginMessageLabel.setText("User not found");
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void SignInBtn1OnAction (ActionEvent event) {
////        loginMessageLabel.setText("You try to login");
////        signInUsingDatabase();
//
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
//
//            while (resultSet.next()) {
//                // Display data from the "user" table
//                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3) + resultSet.getString(4));
//            }
//
//            resultSet.close();
//            statement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        loginMessageLabel.setText("Data from the user table displayed in console.");
//    }

    public void JumpSignUpButtonOnAction (ActionEvent event) {
        loginMessageLabel.setText("You try to login");
    }



//    public void
//    public void cancelButtonOnAction(ActionEvent event) {
//        PaintRenderJob cancelButton;
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
//        stage.close();
//    }

//    DatabaseConnection connectNow = new DatabaseConnection();
//    Connection connectDB = connectNow.getConnection();
//
//    String connectQuery = "SELECT UserName FROM UserAccount";
//
//    try{
//        Statement statement = connectDB.createStatement();
//        ResultSet queryOutput = statement.executeQuery(connectQuery);
//
//        while (queryOutput.next()) {
//
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

}
