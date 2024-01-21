package com.group_22.timetablemanagement;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.scene.control.TableCell;
import javafx.scene.control.ColorPicker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.*;

public class CustomizePageController implements Initializable {
    @FXML
    private ComboBox<String> StudyTimeDropDownBtn;

    @FXML
    private Button CourseUpdatesBtn1;

    @FXML ColorPicker colorPickerbtn1;

    @FXML
    private Button CustomizeTimeTableBtn1;

    @FXML
    private Button DashboardBtn;

    @FXML
    private Button EditPersonalBtn;

    @FXML
    private Button EditTimeTableBtn;

    @FXML
    private Button ModifyDueDatesBtn;

    @FXML
    private Button SignOutBtn;

    @FXML
    private Button btUpdateClassInfo;

    @FXML
    private Button btUpdateSchoolDay;

    @FXML
    private Button btUpdateStudyTime;

    @FXML
    private ComboBox<String> cbSchoolDayStartsFrom;

    @FXML
    private ComboBox<String> cbSchoolDayStartsTo;

    @FXML
    private ComboBox<String> cbClassTimeInterval;

    @FXML
    private ComboBox<CourseInfo> cbClassInfoClassCode;

    @FXML
    private ComboBox<String> cbClassInfoActualDay;

    @FXML
    private Label lbClassInfoCode;

    @FXML
    private Label lbStudyTimeStartsFromTo;

    @FXML
    private TableView<SchoolDay> timetable;

    @FXML
    private TableColumn<SchoolDay, String> weekdaysColumn;

    @FXML
    private Spinner<Integer> SpinnerStudyTimeFromHours;

    @FXML
    private Spinner<Integer> SpinnerStudyTimeToHours;

    private Stage stage;

    private Scene scene;

    private Parent root;

    // Variable to hold userID
    private Integer userID;

    // Variable to hold user's full name
    private String fullName;

    // Variable to hold user's role
    private String role;

    // Variable to hold StudentTeacherID
    private Integer StudentTeacherID;

    private Connection connectDB;

    FXMLLoader loader = null;

    @FXML
    private TableColumn<?, ?> timeColumn6;

    List<TableColumn<SchoolDay, String>> createdColumns = new ArrayList<>();
    private ObservableList<SchoolDay> schoolDay = FXCollections.observableArrayList();

    Map<String, Color> cellColor = new HashMap<>();
    private String[] classCodesToHandle;

    private int startDayIndex = 0;
    private int endDayIndex = 0;
    private int endTimeIndex = 0;
    private int startTimeIndex = 0;
    private int timeInterval = 0;

    // Create a list to store timetable class data
    List<TimetableClassData> timetableClassDataList = new ArrayList<>();


    // Create a list to store timetable course data
    List<TimetableClassData> timetableClassColourList = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

//        weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        if (weekdaysColumn != null) {
            weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        } else {
            System.out.println("weekdaysColumn is null");
        }

        // Add items to all comboboxes
        cbSchoolDayStartsFrom.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"));
        cbSchoolDayStartsTo.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"));
        cbClassTimeInterval.setItems(FXCollections.observableArrayList("30 Minutes", "1 Hour"));

        // Add items to StudyTimeDropDownBtn
        SpinnerValueFactory<Integer> valueFactoryFromHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> valueFactoryFromMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> valueFactoryToHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> valueFactoryToMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

        // Set default values for the spinners
        valueFactoryFromHours.setValue(0);
        valueFactoryFromMinutes.setValue(0);
        valueFactoryToHours.setValue(0);
        valueFactoryToMinutes.setValue(0);

        // Set the spinner value factory and converter for leading zeros
        SpinnerStudyTimeFromHours.setValueFactory(valueFactoryFromHours);

        SpinnerStudyTimeFromHours.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });

        SpinnerStudyTimeToHours.setValueFactory(valueFactoryToHours);

        SpinnerStudyTimeToHours.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });

        // Disable confirm button if any combobox inside SchoolDayStarts is empty
        BooleanBinding isAnyComboBoxSchoolDayStartsEmpty = cbSchoolDayStartsFrom.valueProperty().isNull()
                .or(cbSchoolDayStartsTo.valueProperty().isNull());

        btUpdateSchoolDay.disableProperty().bind(isAnyComboBoxSchoolDayStartsEmpty);

        // Disable confirm button if any combobox inside StudyTime is empty
        BooleanBinding areFieldsStudyTimeStartsEmpty = cbClassTimeInterval.valueProperty().isNull();

        btUpdateStudyTime.disableProperty().bind(areFieldsStudyTimeStartsEmpty);

        // Disable update button if any text fields inside ClassInfo is empty
        BooleanBinding areFieldsClassInfoEmpty = cbClassInfoClassCode.valueProperty().isNull()
                .or(StudyTimeDropDownBtn.valueProperty().isNull())
                .or(cbClassInfoActualDay.valueProperty().isNull());

        btUpdateClassInfo.disableProperty().bind(areFieldsClassInfoEmpty);

        // Disable save button if all combobox and text fields are empty
        BooleanBinding areAllFieldsEmpty = cbSchoolDayStartsFrom.valueProperty().isNull()
                .or(cbSchoolDayStartsTo.valueProperty().isNull())
                .or(cbClassTimeInterval.valueProperty().isNull())
                .or(cbClassInfoClassCode.valueProperty().isNull())
                .or(StudyTimeDropDownBtn.valueProperty().isNull())
                .or(cbClassInfoActualDay.valueProperty().isNull());

//        initializeCourseInfoManually();
    }
//
//    private void fetchExistingTimeTable() {
////        try {
////            // Clear existing data in the timetable
////            schoolDay.clear();
////
////            // Fetch timetable data from the database
////            // Modify the query based on your database schema
////            String query = "SELECT * FROM Timetable WHERE UserID = " + userID;
////            Statement statement = connectDB.createStatement();
////            ResultSet resultSet = statement.executeQuery(query);
////
////            while (resultSet.next()) {
////                String day = resultSet.getString("Day");
////                String classCode = resultSet.getString("ClassCode");
////
////                SchoolDay existingSchoolDay = new SchoolDay(day, classCode);
////                schoolDay.add(existingSchoolDay);
////            }
////
////            // Update the timetable with the fetched data
////            timetable.setItems(schoolDay);
////            timetable.refresh();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }

    @FXML
    private void initialize() {
        // Call a method to initialize course names
        initializeCourseInfo();
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
    }

    // Method to set timetable data based on the retrieved information
    void setTimetableDayData(int startDayIndex, int endDayIndex) {

        schoolDay.clear();

        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        for (int i = startDayIndex; i <= endDayIndex; i++) {
            SchoolDay newSchoolDay = new SchoolDay(days[i]);
            schoolDay.add(newSchoolDay);
        }

        timetable.setItems(schoolDay);
        timetable.refresh();
        System.out.println("Done Day");

        // Update the items in cbClassInfoActualDay
        List<String> allDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
//        int fromIndex = allDays.indexOf(cbSchoolDayStartsFrom.getValue());
//        int toIndex = allDays.indexOf(cbSchoolDayStartsTo.getValue());
//        List<String> schoolDays = allDays.subList(fromIndex, toIndex + 1);
        List<String> schoolDays = allDays.subList(startDayIndex, endDayIndex + 1);
        cbClassInfoActualDay.getItems().setAll(schoolDays);

        // Store selected school days in the database

        timetable.refresh();
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
                StudyTimeDropDownBtn.getItems().add(columnHeader);
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

    public void setUserData(Integer userID, Integer StudentTeacherID, String fullName, String role) {
        this.StudentTeacherID = StudentTeacherID;
        this.userID = userID;
        this.fullName = fullName;
        this.role = role;

        // Now you can use these values in your EditTimeTableController as needed
        if ("Student".equals(role)) {
            CourseUpdatesBtn1.setVisible(false);
        }
    }

//    void initializeCourseInfoManually() {
//        // Create a list of CourseInfo objects
//        List<CourseInfo> manuallyAddedCourses = Arrays.asList(
//                new CourseInfo(1, "CSE101", "Introduction to Computer Science"),
//                new CourseInfo(2, "MAT202", "Linear Algebra"),
//                new CourseInfo(3, "BIO301", "Cell Biology")
//                // Add more courses as needed
//        );
//
//        // Convert the list to an observable list
//        ObservableList<CourseInfo> courses = FXCollections.observableArrayList(manuallyAddedCourses);
//
//        // Set the items in the ComboBox
//        if (courses.isEmpty()) {
//            cbClassInfoClassCode.setPromptText("No course found");
//        } else {
//            cbClassInfoClassCode.setItems(courses);
//        }
//    }


    void initializeCourseInfo() {
        connectDB = JDBCConnection.getConnection();
        // Initialize database connection
        try {
            Statement statement = connectDB.createStatement();

            String query = "SELECT * " +
                    "FROM Courses " +
                    "JOIN StudentCourses ON StudentCourses.CourseID = Courses.CourseID " +
                    "JOIN Students ON Students.StudentID = StudentCourses.StudentID " +
                    "WHERE Students.UserID = " + userID;

            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<CourseInfo> courses1 = FXCollections.observableArrayList();
            List<String> classCodesList = new ArrayList<>();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("CourseID");
                String courseCode = resultSet.getString("courseCode");
                String courseName = resultSet.getString("CourseName");

                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses1.add(courseInfo);
                classCodesList.add(courseCode);

                System.out.println("Put course code into choicebox (enrolled)" + courseName);
            }

            // Convert the list to an array
            classCodesToHandle = classCodesList.toArray(new String[0]);


            if (courses1.isEmpty()) {
                cbClassInfoClassCode.setPromptText("No course found");
                cbClassInfoClassCode.getItems().add(new CourseInfo(-1, "No course found", "No course found"));
            } else {
                cbClassInfoClassCode.setPromptText("");
                cbClassInfoClassCode.setItems(courses1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        initializeCourseInfoManually();
    }

    @FXML
    void onClassStartDropDownClicked(ActionEvent event) {
        int selectedIndex = StudyTimeDropDownBtn.getSelectionModel().getSelectedIndex();
        System.out.println("Selected Index: " + (selectedIndex + 1));
        String selectedValue = StudyTimeDropDownBtn.getValue();
        System.out.println("Selected Value: " + selectedValue);
    }

    @FXML
    void updateSchoolDay(ActionEvent event) throws IOException {
        Map<String, String> dayToNumber = Map.of(
                "Monday", "1",
                "Tuesday", "2",
                "Wednesday", "3",
                "Thursday", "4",
                "Friday", "5",
                "Saturday", "6",
                "Sunday", "7");

        String schoolDayStartsFrom = dayToNumber.get(cbSchoolDayStartsFrom.getValue());
        String schoolDayStartsTo = dayToNumber.get(cbSchoolDayStartsTo.getValue());

        schoolDay.clear();

        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        for (int i = Integer.parseInt(schoolDayStartsFrom); i <= Integer.parseInt(schoolDayStartsTo); i++) {
            SchoolDay newSchoolDay = new SchoolDay(days[i - 1]);
            schoolDay.add(newSchoolDay);
        }

        timetable.setItems(schoolDay);
        timetable.refresh();
        System.out.println("Done Day");

        // Update the items in cbClassInfoActualDay
        List<String> allDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        int fromIndex = allDays.indexOf(cbSchoolDayStartsFrom.getValue());
        int toIndex = allDays.indexOf(cbSchoolDayStartsTo.getValue());
        List<String> schoolDays = allDays.subList(fromIndex, toIndex + 1);
        cbClassInfoActualDay.getItems().setAll(schoolDays);
    }

    // Working!
//    @FXML
//    void updateClassInfo(ActionEvent event) throws IOException {
//
//        String day = cbClassInfoActualDay.getValue();
//        CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
//        String classCode = selectedCourseInfo.getCourseCode();
//        String selectedStartTime = StudyTimeDropDownBtn.getValue();
//
////        int rowIndex = 3;  // Replace with the actual row index you want to insert data into
//        int rowIndex = findRowIndexByCriteria(day, classCode);
//
//        // Find the column index based on the selected value
//        int columnIndex = findColumnIndexByHeader(selectedStartTime);
//
////        System.out.println("columnIndex: " + columnIndex);
//
//        SchoolDay newClassInfo = new SchoolDay(day, classCode);
//
//        // Check if the column index is within the valid range
//        if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
//            TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
//
//            //Set the cell factory to customize the rendering
//            tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    // Display the cell's value
//                    this.setText(item == null ? "" : item);
//
//                    // Get the color from the ColorPicker
//                    setText(item == null? "": item);
//
////                    //Set the cell color based on the color picked
////                    this.setStyle("-fx-background-color: " + toRgbaString(cellColor) + ";");
//
//                    int currentRowIndex = getIndex();
//                    int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
//                    if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                        Color cellColor = colorPickerbtn1.getValue();
//
//                        // Set the cell color based on the color picked
//                        setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//                    } else {
//                        setText(item);
//                    }
//                }
//            });
//
//            // Refresh the TableView to reflect the changes
//            timetable.refresh();
//        } else {
//            System.out.println("Invalid column index: " + columnIndex);
//        }
//
//        // Clear input fields after processing
//        cbClassInfoActualDay.setValue(null);
//        StudyTimeDropDownBtn.getSelectionModel().clearSelection();
//    }

    // This can works
//    @FXML
//    void updateClassInfo(ActionEvent event) throws IOException {
//
////        String day = cbClassInfoActualDay.getValue();
//
//        // get the course code
//        CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
//        String classCode = selectedCourseInfo.getCourseCode();
//        Integer courseId = selectedCourseInfo.getCourseId();
//
//        System.out.println("Course ID to update colour:" + courseId);
////        String selectedStartTime = StudyTimeDropDownBtn.getValue();
//
//        // Fetch timetable time  from the database
//        String query = "SELECT * FROM TimetableClasses WHERE UserID = ? AND CourseID = ?";
//
//        try (Connection connection = JDBCConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setInt(1, userID);
//            preparedStatement.setInt(2, courseId);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println("Result Set:" + resultSet);
//
//            // Process the resultSet and populate your timetable
//            while (resultSet.next()) {
//                int rowIndex = resultSet.getInt("RowIndex");
//                int columnIndex = resultSet.getInt("ColumnIndex");
//
//                // Create an object to represent the timetable class data
//                TimetableClassData timetableClassData = new TimetableClassData(courseId, rowIndex, columnIndex);
//
//                // Add the object to the list
//                timetableClassColourList.add(timetableClassData);
////                timetableClassDataList.add(timetableClassData);
//
//                System.out.println("colour SQL: Course ID" + courseId);
//                System.out.println("colour SQL: Row index" + rowIndex);
//                System.out.println("colour SQL: Column index" + columnIndex);
//            }
//            // Use setTimetableClassData method to populate the timetable
////            setTimetableClassData(timetableClassDataList);
//            setTimeTableClassColour(timetableClassColourList);
//
//            System.out.println("Before Clear" + timetableClassColourList);
//            timetableClassColourList.clear();
//            System.out.println("After Clear" + timetableClassColourList);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
//
////        // Find the column index based on the selected value
////        int columnIndex = findColumnIndexByHeader(selectedStartTime);
////
////        // Find all the rows (days) that belong to the selected course code
////        List<Integer> rowIndexes = findRowIndicesByCourseCode(classCode);
////
////        // Check if the column index is within the valid range
////        if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
////            TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
////
////            // Iterate through each row index and update the color
////            for (int rowIndex : rowIndexes) {
////                // Set the cell color based on the color picked
////                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
////                    @Override
////                    protected void updateItem(String item, boolean empty) {
////                        super.updateItem(item, empty);
////
////                        // Display the cell's value
////                        this.setText(item == null ? "" : item);
////
////                        int currentRowIndex = getIndex();
////                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
////
////                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
////                            Color cellColor = colorPickerbtn1.getValue();
////                            setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
////                        } else {
////                            setText(item);
////                        }
////                    }
////                });
////            }
////
////            // Refresh the TableView to reflect the changes
////            timetable.refresh();
////        } else {
////            System.out.println("Invalid column index: " + columnIndex);
////        }
//
//        // Clear input fields after processing
//        cbClassInfoActualDay.setValue(null);
//        StudyTimeDropDownBtn.getSelectionModel().clearSelection();
//    }

    @FXML
    void updateClassInfo(ActionEvent event) throws IOException {

        // get the course code
        CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
        String classCode = selectedCourseInfo.getCourseCode();
        Integer courseId = selectedCourseInfo.getCourseId();

        System.out.println("Course ID to update colour:" + courseId);
//        String selectedStartTime = StudyTimeDropDownBtn.getValue();

        // Fetch timetable time  from the database
        String query = "SELECT * FROM TimetableClasses WHERE UserID = ? AND CourseID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Result Set:" + resultSet);

            // Process the resultSet and populate your timetable
            while (resultSet.next()) {
                int rowIndex = resultSet.getInt("RowIndex");
                int columnIndex = resultSet.getInt("ColumnIndex");

                // Create an object to represent the timetable class data
                TimetableClassData timetableClassData = new TimetableClassData(courseId, rowIndex, columnIndex);

                // Add the object to the list
                timetableClassColourList.add(timetableClassData);
//                timetableClassDataList.add(timetableClassData);

                System.out.println("colour SQL: Course ID" + courseId);
                System.out.println("colour SQL: Row index" + rowIndex);
                System.out.println("colour SQL: Column index" + columnIndex);
            }

            // Loop through the timetableClassDataList and call setTimeTableClassColour for each item
            for (TimetableClassData timetableClassData : timetableClassColourList) {
                setTimeTableClassColour(timetableClassData);
            }
            // Use setTimetableClassData method to populate the timetable
//            setTimetableClassData(timetableClassDataList);
//            for (TimetableClassData timetableClassData : timetableClassDataList) {
//                setTimeTableClassColour(timetableClassData);
//            }

            System.out.println("Before Clear" + timetableClassColourList);
            timetableClassColourList.clear();
            System.out.println("After Clear" + timetableClassColourList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for (TimetableClassData timetableClassData : timetableClassDataList) {
//            setTimeTableClassColour(timetableClassData);
//        }


        // Clear input fields after processing
        cbClassInfoActualDay.setValue(null);
        StudyTimeDropDownBtn.getSelectionModel().clearSelection();
    }



    // have previous memory
//    private void setTimeTableClassColour(List<TimetableClassData> timetableClassDataList) {
//        System.out.println("EnterColour FunctioN:" + timetableClassDataList);
//        for (TimetableClassData timetableClassData : timetableClassDataList) {
//            int rowIndex = timetableClassData.getRowIndex();
//            int columnIndex = timetableClassData.getColumnIndex();
//            int courseId = timetableClassData.getCourseId();
//
//            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//            String day = daysOfWeek[rowIndex];
//
//            String classCode = getCourseCodeFromDatabase(courseId);
//
//            SchoolDay newClassInfo = new SchoolDay(day, classCode);
//
//            // Check if the column index is within the valid range
//            if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
////                System.out.println("Current column : " + columnIndex);
////                System.out.println("table column : " + timetable.getColumns().size());
//
//
//                TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
//                System.out.println("TableColumn" + tableColumn);
//
//                //Set the cell factory to customize the rendering
////                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
////                    @Override
////                    protected void updateItem(String item, boolean empty) {
////                        super.updateItem(item, empty);
////
////                        // Display the cell's value
////                        this.setText(item == null ? "" : item);
////
////                        // Get the color from the ColorPicker
////                        setText(item == null ? "" : item);
////
//////                    //Set the cell color based on the color picked
//////                    this.setStyle("-fx-background-color: " + toRgbaString(cellColor) + ";");
////
////                        int currentRowIndex = getIndex();
////                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
////
////                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
////                            Color cellColor = colorPickerbtn1.getValue();
////
////                            // Set the cell color based on the color picked
////                            setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
////                        } else {
////                            setText(item);
////                        }
////                    }
////                });
//
//            //Set the cell factory to customize the rendering
////            tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
////                @Override
////                protected void updateItem(String item, boolean empty) {
////                    super.updateItem(item, empty);
////
////                    // Display the cell's value
////                    this.setText(item == null ? "" : item);
////
////                    // Get the color from the ColorPicker
////                    setText(item == null? "": item);
////
//////                    //Set the cell color based on the color picked
//////                    this.setStyle("-fx-background-color: " + toRgbaString(cellColor) + ";");
////
////                    int currentRowIndex = getIndex();
////                    int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
////
////                    if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
////                        Color cellColor = colorPickerbtn1.getValue();
////
////                        // Set the cell color based on the color picked
////                        setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
////                    } else {
////                        setText(item);
////                    }
////                }
////            });
//
//                // Set the cell factory to customize the rendering
//                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        // Display the cell's value
//                        this.setText(item == null ? "" : item);
////                        this.setText(classCode);
////                        System.out.println("Item inside: " + item);
//
//
//                        int currentRowIndex = getIndex();
//                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
////                        System.out.println("Current row inside : " + currentRowIndex);
////                        System.out.println("current column inside : " + currentColumnIndex);
//
//                        // Check if the current cell corresponds to the course you want to update
//                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                            Color cellColor = colorPickerbtn1.getValue();
//                            System.out.println("Successful printed");
//
//                            this.setText(classCode);
//
//                            // Set the cell color based on the color picked
//                            setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//
//                        } else {
//                            // For other cells, reset the background color
//                            setBackground(null);
//                        }
//                    }
//                });
////                tableColumn.setCellValueFactory(data -> {
////                    if (data.getValue().equals(newClassInfo) && data.getTableView().getColumns().indexOf(data.getTableColumn()) == columnIndex) {
////                        // Return the new value for the specified cell
////                        return new SimpleStringProperty(newClassInfo.getClassCode());
////                    } else {
////                        // Return the existing value for other cells
////                        return new SimpleStringProperty(data.getValue().getClassCode());
////                    }
////                });
////
////                // Set the cell factory to customize the rendering
////                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
////                    @Override
////                    protected void updateItem(String item, boolean empty) {
////                        super.updateItem(item, empty);
////
////                        int currentRowIndex = getIndex();
////                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
////
////                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
////                            setText(newClassInfo.getClassCode());
////                        } else {
////                            setText(item);
////                        }
////                    }
////                });
//
//                // Refresh the TableView to reflect the changes
//                timetable.refresh();
//            } else {
//                System.out.println("Invalid column index: " + columnIndex);
//            }
//        }
//    }
    private void setTimeTableClassColour(TimetableClassData timetableClassData) {
        System.out.println("new Type of classdata:" + timetableClassData);
//        for (TimetableClassData timetableClassData : timetableClassDataList) {
//            int rowIndex = timetableClassData.getRowIndex();
//            int columnIndex = timetableClassData.getColumnIndex();
//            int courseId = timetableClassData.getCourseId();
//
//            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//            String day = daysOfWeek[rowIndex];
//
//            String classCode = getCourseCodeFromDatabase(courseId);
//
//            SchoolDay newClassInfo = new SchoolDay(day, classCode);
//
//            // Check if the column index is within the valid range
//            if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
////                System.out.println("Current column : " + columnIndex);
////                System.out.println("table column : " + timetable.getColumns().size());
//
//
//                TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
//                System.out.println("TableColumn" + tableColumn);
//
//                tableColumn.setCellValueFactory(data -> {
//                    if (data.getValue().equals(newClassInfo) && data.getTableView().getColumns().indexOf(data.getTableColumn()) == columnIndex) {
//                        // Return the new value for the specified cell
//                        return new SimpleStringProperty(newClassInfo.getClassCode());
//                    } else {
//                        // Return the existing value for other cells
//                        return new SimpleStringProperty(data.getValue().getClassCode());
//                    }
//                });
//
//                // Set the cell factory to customize the rendering
//                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        // Display the cell's value
//                        this.setText(item == null ? "" : item);
////                        this.setText(classCode);
////                        System.out.println("Item inside: " + item);
//
//
//                        int currentRowIndex = getIndex();
//                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
////                        System.out.println("Current row inside : " + currentRowIndex);
////                        System.out.println("current column inside : " + currentColumnIndex);
//
//                        // Check if the current cell corresponds to the course you want to update
//                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                            Color cellColor = colorPickerbtn1.getValue();
//                            System.out.println("Successful printed");
//
//                            this.setText(classCode);
//
//                            // Set the cell color based on the color picked
//                            setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//
//                        } else {
//                            // For other cells, reset the background color
//                            setBackground(null);
//                        }
//                    }
//                });
//
//                // Refresh the TableView to reflect the changes
//                timetable.refresh();
//            } else {
//                System.out.println("Invalid column index: " + columnIndex);
//            }
//        }
        int rowIndex = timetableClassData.getRowIndex();
        int columnIndex = timetableClassData.getColumnIndex();
        int courseId = timetableClassData.getCourseId();

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String day = daysOfWeek[rowIndex];

        String classCode = getCourseCodeFromDatabase(courseId);

        SchoolDay newClassInfo = new SchoolDay(day, classCode);

        // Check if the column index is within the valid range
        if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
//            System.out.println("Current column : " + columnIndex);
//            System.out.println("table column : " + timetable.getColumns().size());


            TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
            System.out.println("TableColumn" + tableColumn);

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
//            tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    // Display the cell's value
//                    this.setText(item == null ? "" : item);
////                    this.setText(classCode);
////                    System.out.println("Item inside: " + item);
//
//
//                    int currentRowIndex = getIndex();
//                    int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
////                    System.out.println("Current row inside : " + currentRowIndex);
////                    System.out.println("current column inside : " + currentColumnIndex);
//
//                    // Check if the current cell corresponds to the course you want to update
//                    if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                        Color cellColor = colorPickerbtn1.getValue();
//                        System.out.println("Successful printed");
//
//                        this.setText(classCode);
//
//                        // Set the cell color based on the color picked
//                        setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//
//                    } else {
//                        // For other cells, reset the background color
//                        setBackground(null);
//                    }
//                }
//            });
            // Set the cell factory to customize the rendering
            tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    int currentRowIndex = getIndex();
                    int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());

                    if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
                        Color cellColor = determineCellColor(newClassInfo.getClassCode(), colorPickerbtn1.getValue()); // Adjust this method
//                        Color cellColor = colorPickerbtn1.getValue();

                        setText(newClassInfo.getClassCode());
                        setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        setText(item);
                        setBackground(null);
                    }
                }
            });

            // Refresh the TableView to reflect the changes
            timetable.refresh();
        } else {
            System.out.println("Invalid column index: " + columnIndex);
        }
    }

//    private Color ColourForCourse1;
    private static final Map<String, Color> classCodeColorMap = new HashMap<>();
    private Color determineCellColor(String classCode, Color value) {
//        // List of class codes you want to handle
//        String[] classCodesToHandle = {"CMT221", "CAT201", "CST232"};
//        if ("CMT221".equals(classCode)) {
//            // Check if the color is already assigned for CMT221
//            if (classCodeColorMap.containsKey("CMT221")) {
//                return classCodeColorMap.get("CMT221");
//            } else {
//                // If not assigned, store the color globally for CMT221
//                classCodeColorMap.put("CMT221", value);
//                return value;
//            }
//        } else {
//            return Color.GREEN; // or any other default color
//        }
        if (containsIgnoreCase(classCodesToHandle, classCode)) {
            if (classCodeColorMap.containsKey(classCode)) {
                return classCodeColorMap.get(classCode);
            } else {
                // If the color is not set, set it to the provided value
                classCodeColorMap.put(classCode, value);
                return value;
            }
        } else {
            return Color.GREEN; // or any other default color
        }
    }

    // Helper method to check if an array contains a specific value (case-insensitive)
    private boolean containsIgnoreCase(String[] array, String value) {
        for (String item : array) {
            if (item.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }


//    private void setTimeTableClassColour(List<TimetableClassData> timetableClassDataList) {
//        for (TimetableClassData timetableClassData : timetableClassDataList) {
//            int rowIndex = timetableClassData.getRowIndex();
//            int columnIndex = timetableClassData.getColumnIndex();
//            int courseId = timetableClassData.getCourseId();
//
//            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//            String day = daysOfWeek[rowIndex];
//
//            String classCode = getCourseCodeFromDatabase(courseId);
//
//            // Check if the column index is within the valid range
//            if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
//                TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
//
//                // Set the cell factory to customize the rendering
//                tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        // Display the cell's value
//                        this.setText(item == null ? "" : item);
//
//                        int currentRowIndex = getIndex();
//                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
//                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                            // Check if the class code matches a specific condition
//                            if ("YourDesiredCourseCode".equals(classCode)) {
//                                // Set a specific color for this course code
//                                Color cellColor = Color.RED; // Replace with your desired color
//                                setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//                            } else {
//                                // Set the cell color based on the color picked
//                                Color cellColor = colorPickerbtn1.getValue();
//                                setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
//                            }
//                        } else {
//                            setText(item);
//                        }
//                    }
//                });
//
//                // Refresh the TableView to reflect the changes
//                timetable.refresh();
//            } else {
//                System.out.println("Invalid column index: " + columnIndex);
//            }
//        }
//    }



    // Helper method to find all row indices that belong to the specified course code
    private List<Integer> findRowIndicesByCourseCode(String courseCode) {
        List<Integer> rowIndexes = new ArrayList<>();

        // Iterate through the timetable rows (days) and check if the course code matches
        for (int i = 0; i < timetable.getItems().size(); i++) {
            SchoolDay schoolDay = timetable.getItems().get(i);
            if (schoolDay.getClassCode().equals(courseCode)) {
                rowIndexes.add(i);
            }
        }

        return rowIndexes;
    }


    public static class TableCellTest extends TableCell<SchoolDay, String> {
        private final ColorPicker colorPicker;

        public TableCellTest(ColorPicker colorPicker) {
            this.colorPicker = colorPicker;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            // Display the cell's value
            this.setText(item == null ? "" : item);

            // Set the cell color based on the color picked in Scene Builder
            this.setStyle("-fx-background-color: " + toRgbaString(colorPicker.getValue()) + ";");
        }
    }

    private static String toRgbaString(Color color) {
        return String.format("rgba(%d, %d, %d, %.2f)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                color.getOpacity());
    }

    // Method to find the column index based on the header
    private int findColumnIndexByHeader(String columnHeader) {
        for (int i = 0; i < timetable.getColumns().size(); i++) {
            if (timetable.getColumns().get(i).getText().equals(columnHeader)) {
                return i;
            }
        }
        return -1; // Column not found
    }

    private int findRowIndexByCriteria(String day, String classCode) {
        if ("Monday".equals(day)) {
            return 0;
        } else if ("Tuesday".equals(day)) {
            return 1;
        } else if ("Wednesday".equals(day)) {
            return 2;
        } else if ("Thursday".equals(day)) {
            return 3;
        } else if ("Friday".equals(day)) {
            return 4;
        } else if ("Saturday".equals(day)) {
            return 5;
        } else if ("Sunday".equals(day)) {
            return 6;
        } else {
            return -1; // Return -1 if the day is not recognized
        }
    }

    @FXML
    void updateStudyTime(ActionEvent event) throws IOException {
        String studyTimeStartFromHours, studyTimeStartToHours, studyTimeStartInterval;
        int timeIntervalInt;

        studyTimeStartFromHours = Integer.toString(SpinnerStudyTimeFromHours.getValue());
        studyTimeStartToHours = Integer.toString(SpinnerStudyTimeToHours.getValue());
        studyTimeStartInterval = cbClassTimeInterval.getValue();

        StudyTimeDropDownBtn.getItems().clear();

        System.out.println("Real interval" + studyTimeStartInterval);

        timetable.getColumns().removeAll(createdColumns);
        createdColumns.clear();

        // Set timeIntervalInt based on the selected value in cbClassTimeInterval
        if ("30 Minutes".equals(studyTimeStartInterval)) {
            timeIntervalInt = 30;
        } else if ("1 Hour".equals(studyTimeStartInterval)) {
            timeIntervalInt = 60;
        } else {
            // Handle the case where an unexpected value is selected
            timeIntervalInt = 0; //
        }

        for (int i = Integer.parseInt(studyTimeStartFromHours); i < Integer.parseInt(studyTimeStartToHours); i++) {
            for (int j = 0; j < 60; j += timeIntervalInt) {
                int endMinute = j + timeIntervalInt;

                if (i == Integer.parseInt(studyTimeStartToHours) && endMinute >= 60) {
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
                StudyTimeDropDownBtn.getItems().add(columnHeader);
                System.out.println(columnHeader + "is added into drop down");
            }
        }
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
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        CustomizePageController customizeController = loader.getController();
        customizeController.setUserData(userID, StudentTeacherID,  fullName, role);

        customizeController.initializeCourseInfo();
        System.out.println(userID);
        System.out.println(fullName);
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

        // Pass any necessary data to the EditTimeTableController using the set method
        EditTimeTableController editTimeTableController = loader.getController();
        editTimeTableController.setUserData(userID, StudentTeacherID, fullName, role);
        // Now initialize course names
        editTimeTableController.initializeCourseInfo();
        System.out.println(userID);
        System.out.println(fullName);
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