package com.group_22.timetablemanagement;

//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import java.io.IOException;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

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

    FXMLLoader loader = null;

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
                        loader = new FXMLLoader(getClass().getResource("TeacherHomePage.fxml"));
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
                } else {
                    loginMessageLabel.setText("Incorrect password");
                }
            } else {
                loginMessageLabel.setText("User not found");
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void JumpSignUpButtonOnAction (ActionEvent event) throws IOException {

//        loginMessageLabel.setText("You try to login");
        loader = new FXMLLoader(getClass().getResource("Sign-Up.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
