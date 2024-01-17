package com.group_22.timetablemanagement;

import com.group_22.timetablemanagement.CourseInfo;
import com.group_22.timetablemanagement.JDBCConnection;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomizeController {

    @FXML
    private Button btDayApply;

    @FXML
    private ChoiceBox<CourseInfo> courseChoiceBox;

    @FXML
    private ColorPicker cellColorPicker;

    @FXML
    private ColorPicker backgroundColorPicker;

    private Connection connectDB;

    private Integer userID;

    @FXML
    private ChoiceBox<String> dayStartChoice;

    @FXML
    private ChoiceBox<String> dayEndChoice;

    @FXML
    private void initialize() {
        initializeCourseNames();
        initializeStartDays();
        initializeEndDays();

        // Disable btDayApply button if any choice boxes inside ClassInfo is null or empty
        BooleanBinding areFieldsClassInfoEmpty = dayStartChoice.valueProperty().isNull()
                .or(dayEndChoice.valueProperty().isNull());

        btDayApply.disableProperty().bind(areFieldsClassInfoEmpty);
    }

    public void setUserData(Integer userID) {
        this.userID = userID;
    }

    void initializeCourseNames() {
        Connection connectDB = JDBCConnection.getConnection();
        try {
            Statement statement = connectDB.createStatement();
            String query = "SELECT * " +
                    "FROM Courses " +
                    "JOIN StudentCourses ON StudentCourses.CourseID = Courses.CourseID " +
                    "JOIN Students ON Students.TeacherID = StudentCourses.StudentID " +
                    "WHERE Students.UserID = " + userID;

            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<CourseInfo> courses = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String courseCode = resultSet.getString("courseCode");
                String courseName = resultSet.getString("CourseName");
                int courseId = resultSet.getInt("CourseID");

                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses.add(courseInfo);
            }

            courseChoiceBox.setItems(courses);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void initializeStartDays() {
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        dayStartChoice.setItems(daysOfWeek);
    }

    void initializeEndDays() {
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        dayEndChoice.setItems(daysOfWeek);
    }

    public class TableCellTest<T> extends TableCell<SchoolDay, T> {
        private Color selectedCellColor;
        private Color selectedBackgroundColor;

        private Color getSelectedCellColor() {
            return selectedCellColor;
        }

        private Color getSelectedBackgroundColor() {
            return selectedBackgroundColor;
        }

        public void setSelectedCellColor(Color selectedCellColor) {
            this.selectedCellColor = selectedCellColor;
            updateCellColors();
        }

        public void setSelectedBackgroundColor(Color selectedBackgroundColor) {
            this.selectedBackgroundColor = selectedBackgroundColor;
            updateCellColors();
        }

        private void updateCellColors() {
            if (selectedCellColor != null) {
                setStyle("-fx-background-color: " + colorToCss(selectedCellColor) + ";" + getStyle());
            }

            if (selectedBackgroundColor != null) {
                setStyle("-fx-background-color: " + colorToCss(selectedBackgroundColor) + ";" + getStyle());
            }
        }

        private String colorToCss(Color color) {
            return String.format("rgba(%d, %d, %d, %f)",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255),
                    color.getOpacity());
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            setText(item == null ? "" : item.toString());

            if (item instanceof CourseInfo) {
                CourseInfo courseInfo = (CourseInfo) item;

                if (selectedCellColor != null) {
                    setStyle("-fx-background-color: " + colorToCss(selectedCellColor) + ";" + getStyle());
                }

                if (selectedBackgroundColor != null) {
                    setStyle("-fx-background-color: " + colorToCss(selectedBackgroundColor) + ";" + getStyle());
                }
            } else {
                setStyle("");
            }
        }
    }

    @FXML
    void onApplyCellColorButtonClicked() {
        CourseInfo selectedCourse = courseChoiceBox.getValue();
        Color cellColor = cellColorPicker.getValue();
        updateCellColor(selectedCourse.getCourseId(), cellColor);
    }

    @FXML
    void onApplyBackgroundColorButtonClicked() {
        CourseInfo selectedCourse = courseChoiceBox.getValue();
        Color backgroundColor = backgroundColorPicker.getValue();
        updateBackgroundColor(selectedCourse.getCourseId(), backgroundColor);
    }

    @FXML
    void onDayApplyButtonClicked() {
        // Get the selected values from the ChoiceBox components
        String selectedDayStart = dayStartChoice.getValue();
        String selectedDayEnd = dayEndChoice.getValue();
        updateDay(selectedDayStart, selectedDayEnd);

    }

    private void updateDay(String selectedDayStart,String selectedDayEnd){

    }

    private void updateCellColor(int courseId, Color cellColor) {
        // Implement JDBC logic to update the cell color in the database
    }

    private void updateBackgroundColor(int courseId, Color backgroundColor) {
        // Implement JDBC logic to update the background color in the database
    }
}
