package com.group_22.timetablemanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SignUpController {

    @FXML
    private Button JumpSignInButton;

    @FXML
    private Button SignUpBtn1;

//    @FXML
//    private ChoiceBox<?> UniversityChoiceBox;

//    @FXML
//    private ChoiceBox<String> UniversityChoiceBox; // Adjusted ChoiceBox type


    @FXML
    private ChoiceBox<UniversityList> UniversityChoiceBox; // Adjusted ChoiceBox type



    @FXML
    private TextField emailAddress1;

    @FXML
    private TextField fname1;

    @FXML
    private PasswordField pass1;

//    @FXML
//    private ChoiceBox<?> studentTeacherChoiiceBox;

    @FXML
    private ChoiceBox<String> studentTeacherChoiceBox; // Adjusted ChoiceBox type


    @FXML
    private Label teacherNotificationText;


    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView timeManagementIcon;

    private Stage stage;
    private Scene scene;

    private Parent root;

    FXMLLoader loader = null;

    private Connection connectDB;



    @FXML
    void initialize() {
        getUniversityNamesFromDatabase();
        // Initialize the ChoiceBox options
//        List<String> universityNames = getUniversityNamesFromDatabase();
//        UniversityChoiceBox.getItems().addAll(universityNames);
//        UniversityChoiceBox.getItems().addAll("University 1", "University 2", "University 3"); // Add your actual university names

        studentTeacherChoiceBox.getItems().addAll("Student", "Teacher");
    }

    void getUniversityNamesFromDatabase() {
        connectDB = JDBCConnection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            String query = "SELECT * FROM Universities";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<UniversityList> universities = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int universityID = resultSet.getInt("UniversityID");
                String universityName = resultSet.getString("UniversityName");

                UniversityList universityList = new UniversityList(universityID, universityName);
                universities.add(universityList);
            }

            // Assuming CourseDropDownBtn is a ChoiceBox<UniversityList>
            UniversityChoiceBox.setItems(universities);

            // If you have a custom cell factory for rendering, set it here
            // CourseDropDownBtn.setCellFactory(yourCustomCellFactory);

            // If you have a custom string converter, set it here
            // CourseDropDownBtn.setConverter(yourCustomStringConverter);

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SignUpBtn1OnClicked(ActionEvent event) {
        String fullName = fname1.getText().trim();

        UniversityList universityList = UniversityChoiceBox.getValue();
//        int universityID = universityList.getUniversityID();
        int universityID = (universityList != null) ? universityList.getUniversityID() : -1;

        String role = studentTeacherChoiceBox.getValue();

        String emailText = emailAddress1.getText().trim();
        String passwordText = pass1.getText().trim();
        connectDB = JDBCConnection.getConnection();

        System.out.println(fullName);
        System.out.println(universityList);
        System.out.println(universityID);
        System.out.println(role);
        System.out.println(emailText);
        System.out.println(passwordText);

//        if (emailText.isEmpty() || passwordText.isEmpty()) {
//            loginMessageLabel.setText("Email and password are required");
//            return;
//        }
        if (fullName.isEmpty() || universityList == null || role == null || emailText.isEmpty() || passwordText.isEmpty()) {
            loginMessageLabel.setText("All fields must be filled.");
            return;
        }

        // Check if the email already exists
        if (isEmailAlreadyUsed(emailText)) {
            loginMessageLabel.setText("This email is already in use.");
            return;
        }

        // Basic email format validation
        if (!isValidEmail(emailText)) {
            loginMessageLabel.setText("Invalid email format.");
            return;
        }

        // Check password length
        if (passwordText.length() < 6) {
            loginMessageLabel.setText("Password must be at least 6 characters long.");
            return;
        }

        // Insert the information into the database
        insertSignUpIntoDatabase(fullName, universityList, role, emailText, passwordText);

        // Clear input fields
        fname1.clear();
        UniversityChoiceBox.getSelectionModel().clearSelection();
        studentTeacherChoiceBox.getSelectionModel().clearSelection();
        emailAddress1.clear();
        pass1.clear();

        // Show sign-up success pop-up
//        showSignUpSuccessAlert();
        // Jump to the login page
        try {
            JumpSignInButtonOnClicked(event);
            showSignUpSuccessAlert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailAlreadyUsed(String email) {
        try {
            Statement statement = connectDB.createStatement();
            String query = "SELECT * FROM Users WHERE User_email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            // If there are any results, the email is already in use
            if (resultSet.next()) {
                resultSet.close();
                statement.close();
                return true;
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Email is not in use
        return false;
    }

    boolean isValidEmail(String email) {
        // You can implement a more sophisticated email validation if needed
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

//    private void insertSignUpIntoDatabase(String fullName, int universityID, String role, String emailText, String passwordText) {
//
//        if (role == "Teacher"){
//
//            String query = "INSERT INTO Users (User_email, Password, Role) VALUES (?, ?, ?)";
//            String query = "INSERT INTO Teachers (TeacherName, UniversityID, UserID) VALUES (?, ?, ?)";
//        } else if (role == "Student") {
//            String query = "INSERT INTO Users (User_email, Password, Role) VALUES (?, ?, ?)";
//            String query = "INSERT INTO Teachers (StudentName, UniversityID, UserID) VALUES (?, ?, ?)";
//
//        }
//
//
//        // Create a SQL query to insert the task information into the database
//        String query = "INSERT INTO DueDates (CourseID, DueDateTitle, DueDateText, DueDate, PostDate, PostByName) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection connection = JDBCConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            // Set the values for the parameters in the prepared statement
//            preparedStatement.setInt(1, courseID);
//            preparedStatement.setString(2, taskName);
//            preparedStatement.setString(3, taskDetail);
//            preparedStatement.setDate(4, Date.valueOf(dueDate));
//            preparedStatement.setDate(5, Date.valueOf(postDate));
//            preparedStatement.setString(6, postByName);
//
//            // Execute the query to insert the data
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exceptions as needed
//        }
//    }

    private void insertSignUpIntoDatabase(String fullName, UniversityList universityList, String role, String emailText, String passwordText) {
        connectDB = JDBCConnection.getConnection();

        try {
            // Insert into Users table
            String userInsertQuery = "INSERT INTO Users (User_email, Password, Role) VALUES (?, ?, ?)";
            try (PreparedStatement userInsertStatement = connectDB.prepareStatement(userInsertQuery, Statement.RETURN_GENERATED_KEYS)) {
                userInsertStatement.setString(1, emailText);
                userInsertStatement.setString(2, passwordText);
                userInsertStatement.setString(3, role);

                int affectedRows = userInsertStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                // Retrieve the generated UserID
                try (ResultSet generatedKeys = userInsertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userID = generatedKeys.getInt(1);

                        // Insert into Teachers or Students table based on the role
                        if ("Teacher".equals(role)) {
                            String teacherInsertQuery = "INSERT INTO Teachers (TeacherName, UniversityID, UserID) VALUES (?, ?, ?)";
                            try (PreparedStatement teacherInsertStatement = connectDB.prepareStatement(teacherInsertQuery)) {
                                teacherInsertStatement.setString(1, fullName);
                                teacherInsertStatement.setInt(2, universityList.getUniversityID());
                                teacherInsertStatement.setInt(3, userID);
                                teacherInsertStatement.executeUpdate();
                            }
                        } else if ("Student".equals(role)) {
                            String studentInsertQuery = "INSERT INTO Students (StudentName, UniversityID, UserID) VALUES (?, ?, ?)";
                            try (PreparedStatement studentInsertStatement = connectDB.prepareStatement(studentInsertQuery)) {
                                studentInsertStatement.setString(1, fullName);
                                studentInsertStatement.setInt(2, universityList.getUniversityID());
                                studentInsertStatement.setInt(3, userID);
                                studentInsertStatement.executeUpdate();
                            }
                        }
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showSignUpSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign-Up Successful");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! Your sign-up was successful.");

        // Add a custom button for OK
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        // Customize the alert style if needed
        // alert.getDialogPane().getStylesheets().add(getClass().getResource("your-custom-style.css").toExternalForm());

        // Handle the OK button click event
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {
                // Handle OK button click if needed (e.g., navigate to login page)
                try {
                    JumpSignInButtonOnClicked(null);  // Pass null instead of creating a new ActionEvent
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML
    void JumpSignInButtonOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("Sign-In.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
