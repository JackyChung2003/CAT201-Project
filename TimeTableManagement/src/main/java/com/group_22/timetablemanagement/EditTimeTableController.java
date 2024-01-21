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
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditTimeTableController implements Initializable {
    @FXML
    private ComboBox<String> StudyTimeDropDownBtn;

    @FXML
    private Button CourseUpdatesBtn1;

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
    private Button btEnrollClass;

    @FXML
    private Button btUpdateSchoolDay;

    @FXML
    private Button btUpdateStudyTime;

    @FXML
    private Button btUpdateClassInfo;

    @FXML
    private ComboBox<CourseInfo> cbEnrollClass;

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
    private TableView<SchoolDay> timetable;

    @FXML
    private TableColumn<SchoolDay, String> weekdaysColumn;

    @FXML
    private Spinner<Integer> SpinnerStudyTimeFromHours;

    @FXML
    private Spinner<Integer> SpinnerStudyTimeToHours;

    @FXML
    private ListView<String> DueDatesListView;

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

    // Variable to hold StudentTeacherID
    private Integer StudentTeacherID;

    private Connection connectDB;

    FXMLLoader loader = null;

    List<TableColumn<SchoolDay, String>> createdColumns = new ArrayList<>();

    private ObservableList<SchoolDay> schoolDay = FXCollections.observableArrayList();


    private int startDayIndex = 0;
    private int endDayIndex = 0;
    private int endTimeIndex = 0;
    private int startTimeIndex = 0;
    private int timeInterval = 0;

    // Create a list to store timetable class data
    List<TimetableClassData> timetableClassDataList = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("Start to enter initial page");
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

        // Disable the enroll button if no course is selected
        btEnrollClass.disableProperty().bind(cbEnrollClass.valueProperty().isNull());

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

//        loadTimetableDayFromDatabase();
//        System.out.println("Enter the edit time initialze function:");
//        initializeTimetableDayFromDatabase();
    }

    @FXML
    private void initialize() {
        System.out.println("Start to enter page");
        // Call a method to initialize course names
//        loadTimetableDayFromDatabase();
        initializeNotExistsCourseInfo();
        initializeCourseInfo();
        initializeTimetableDayFromDatabase();

        // Load timetable data from the database
//        loadTimetableDayFromDatabase();
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
//    void setTimetableClassData(List<TimetableClassData> timetableClassDataList) {
//        // Iterate through the list and populate the timetable with the retrieved data
//        for (TimetableClassData timetableClassData : timetableClassDataList) {
//            int rowIndex = timetableClassData.getRowIndex();
//            int columnIndex = timetableClassData.getColumnIndex();
//            int courseId = timetableClassData.getCourseId();
//
//            // Insert the data into the corresponding cell in the timetable
//            // Implement this method based on your requirements
////            updateTimetableCell(rowIndex, columnIndex, /* Add other parameters as needed */);
//
//            String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
//            String day = daysOfWeek[rowIndex];
//
//            String classCode = getCourseCodeFromDatabase(courseId);
//
////            String day = cbClassInfoActualDay.getValue();
////            CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
////            String classCode = selectedCourseInfo.getCourseCode();
//
//            SchoolDay newClassInfo = new SchoolDay(day, classCode);
//
//            // Check if the column index is within the valid range
//            if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
//
//                TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
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
//                        int currentRowIndex = getIndex();
//                        int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());
//
//                        if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
//                            setText(newClassInfo.getClassCode());
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
//
//        // Optionally, refresh the timetable view
//        timetable.refresh();
//    }

    //Testing
    void setTimetableClassData(List<TimetableClassData> timetableClassDataList) {
        // Iterate through the list and populate the timetable with the retrieved data
        for (TimetableClassData timetableClassData : timetableClassDataList) {
            int rowIndex = timetableClassData.getRowIndex();
            int columnIndex = timetableClassData.getColumnIndex();
            int courseId = timetableClassData.getCourseId();

            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            String day = daysOfWeek[rowIndex];

            String classCode = getCourseCodeFromDatabase(courseId);

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
                            Color cellColor = determineCellColor(newClassInfo.getClassCode()); // Adjust this method
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

        // Optionally, refresh the timetable view
        timetable.refresh();
    }

    // Adjust this method to determine the color based on the class code or any other condition
    private Color determineCellColor(String classCode) {
        // Implement your logic to determine the color based on the class code
        // Example: Check if classCode is "YourDesiredClassCode" and return Color.RED, else return a default color
        if ("CMT221".equals(classCode)) {
            return Color.RED;
        } else {
            return Color.GREEN; // or any other default color
        }
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


//    private void updateTimetableCell(int dayIndex, int timeIndex, String classCode) {
//        // Check if indices are within valid range
//        if (dayIndex >= 0 && dayIndex < schoolDay.size() && timeIndex >= 0 && timeIndex < createdColumns.size()) {
//            // Find the corresponding cell in the timetable and update the value
//            SchoolDay schoolDayItem = schoolDay.get(dayIndex);
//            TableColumn<SchoolDay, String> tableColumn = createdColumns.get(timeIndex);
//
//            tableColumn.setCellValueFactory(data -> {
//                if (data.getValue().equals(schoolDayItem)) {
//                    // Return the new value for the specified cell
//                    return new SimpleStringProperty(classCode);
//                } else {
//                    // Return the existing value for other cells
//                    return new SimpleStringProperty(data.getValue().getClassCode());
//                }
//            });
//
//            // Refresh the TableView to reflect the changes
//            timetable.refresh();
//        }
//    }

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

            while (resultSet.next()) {
                int courseId = resultSet.getInt("CourseID");
                String courseCode = resultSet.getString("courseCode");
                String courseName = resultSet.getString("CourseName");

                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses1.add(courseInfo);

                System.out.println("Put course code into choicebox (enrolled)" + courseName);
            }


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
    }

    void initializeNotExistsCourseInfo(){
        connectDB = JDBCConnection.getConnection();
        // Initialize database connection
        try {
            Statement statement = connectDB.createStatement();

            String query = "SELECT C.CourseID, C.CourseCode, C.CourseName " +
                    "FROM courses C " +
                    "LEFT JOIN studentcourses SC ON C.CourseID = SC.CourseID " +
                    "AND SC.StudentID = " + StudentTeacherID + " " +
                    "WHERE SC.StudentID IS NULL";

            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<CourseInfo> courses2 = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("CourseID");
                String courseCode = resultSet.getString("courseCode");
                String courseName = resultSet.getString("CourseName");

                CourseInfo notExistsCourseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses2.add(notExistsCourseInfo);

                System.out.println("Put course code into choicebox (not enrolled)" + courseName);
            }

            if (courses2.isEmpty()) {
                System.out.println("No course found");
                cbEnrollClass.setPromptText("No course found");
                cbEnrollClass.getItems().add(new CourseInfo(-1, "No course found", "No course found"));
            } else {
                cbEnrollClass.setItems(courses2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClassStartDropDownClicked(ActionEvent event) {
        int selectedIndex = StudyTimeDropDownBtn.getSelectionModel().getSelectedIndex();
        System.out.println("Selected Index: " + (selectedIndex + 1));
        String selectedValue = StudyTimeDropDownBtn.getValue();
        System.out.println("Selected Value: " + selectedValue);
    }

    @FXML
    void enrollClass(ActionEvent event) throws IOException {
        try {
            Statement statement = connectDB.createStatement();

            CourseInfo selectedCourseInfo = cbEnrollClass.getValue();
            int courseId = selectedCourseInfo.getCourseId();
            String courseCode = selectedCourseInfo.getCourseCode();
            String courseName = selectedCourseInfo.getCourseName();

            // Insert the new course into the database
            String query = "INSERT INTO StudentCourses (StudentID, CourseID) " +
                    "VALUES (" + StudentTeacherID + ", " + courseId + ")";

            statement.executeUpdate(query);

            System.out.println("Enrolled in course " + courseId + " " + courseCode);

            // //Initialize course names
            initializeCourseInfo();

            // Fetch the updated list of courses from the database
            ObservableList<CourseInfo> courses = FXCollections.observableArrayList();
            query = "SELECT * FROM Courses WHERE CourseID NOT IN (SELECT CourseID FROM StudentCourses WHERE StudentID = " + StudentTeacherID + ")";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                courseId = resultSet.getInt("CourseID");
                courseCode = resultSet.getString("CourseCode");
                courseName = resultSet.getString("CourseName");

                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
                courses.add(courseInfo);
            }

            cbEnrollClass.setItems(courses);

            // If the ChoiceBox is empty, display a message
            if (cbEnrollClass.getItems().isEmpty()) {
                System.out.println("No course found in the database");
                cbEnrollClass.getItems().add(new CourseInfo(-1, "No course found", "No course found"));
                cbEnrollClass.getSelectionModel().selectFirst();

                // Disable the enroll button
                btEnrollClass.disableProperty().unbind();
                btEnrollClass.setDisable(true);
            } else {
                // Enable the enroll button if there are courses in the ChoiceBox
                btEnrollClass.disableProperty().unbind();
                btEnrollClass.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // Store selected school days in the database
//        storeSchoolDaysInDatabase(userID, fromIndex, toIndex);
        updateOrInsertSchoolDaysInDatabase(userID, fromIndex, toIndex);
    }

//    private void storeSchoolDaysInDatabase(int userID, int startDay, int endDay) {
//        // Create a SQL query to insert the task information into the database
//        String query = "INSERT INTO TimetableDays (UserID, StartDay, EndDay) VALUES (?, ?, ?)";
//        try (Connection connection = JDBCConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            // Set the values for the parameters in the prepared statement
//            preparedStatement.setInt(1, userID);
//            preparedStatement.setInt(2, startDay);
//            preparedStatement.setInt(3, endDay);
//
//
//            // Execute the query to insert the data
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exceptions as needed
//        }
//    }

    private void updateOrInsertSchoolDaysInDatabase(int userID, int startDay, int endDay) {
        // Check if a record already exists for the user
        boolean recordExists = checkIfDayRecordExists(userID);

        if (recordExists) {
            // If the record exists, update it
            updateSchoolDays(userID, startDay, endDay);
            System.out.println("TimetableDays record updated for UserID: " + userID);
        } else {
            // If the record doesn't exist, insert a new one
            insertSchoolDays(userID, startDay, endDay);
            System.out.println("TimetableDays record inserted for UserID: " + userID);
        }
    }

    private boolean checkIfDayRecordExists(int userID) {
        String query = "SELECT COUNT(*) AS count FROM TimetableDays WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return false;
    }

    private void updateSchoolDays(int userID, int startDay, int endDay) {
        String updateQuery = "UPDATE TimetableDays SET StartDay = ?, EndDay = ? WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, startDay);
            preparedStatement.setInt(2, endDay);
            preparedStatement.setInt(3, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    private void insertSchoolDays(int userID, int startDay, int endDay) {
        String insertQuery = "INSERT INTO TimetableDays (UserID, StartDay, EndDay) VALUES (?, ?, ?)";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, startDay);
            preparedStatement.setInt(3, endDay);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public static class TableCellTest<T> extends TableCell<SchoolDay, T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            // Display the cell's value
            this.setText(item == null ? "" : item.toString());

            // Check the cell's index and set its color
            if (this.getIndex() < 12)
                this.setStyle("-fx-background-color: rgba(253, 255, 150, 0.4);");
            else
                this.setStyle("");
        }
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

        System.out.println("Real interval " + studyTimeStartInterval);

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

        int studyTimeStartInt = SpinnerStudyTimeFromHours.getValue();
        int studyTimeEndInt = SpinnerStudyTimeToHours.getValue();

        // Store selected school days in the database
//        updateOrInsertSchoolTimeInDatabase(userID, startTime, endTime, intervalTime);
        updateOrInsertSchoolTimeInDatabase(userID, studyTimeStartInt, studyTimeEndInt, timeIntervalInt);

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

    private void updateOrInsertSchoolTimeInDatabase(int userID, int startTime, int endTime, int intervalTime) {
        // Check if a record already exists for the user
        boolean recordExists = checkIfTimeRecordExists(userID);

        if (recordExists) {
            // If the record exists, update it
            updateSchoolTime(userID, startTime, endTime, intervalTime);
            System.out.println("TimetableTime record updated for UserID: " + userID);
        } else {
            // If the record doesn't exist, insert a new one
//            insertSchoolTime(userID, startDay, endDay);
            insertSchoolTime(userID, startTime, endTime, intervalTime);
            System.out.println("TimetableTime record inserted for UserID: " + userID);
        }
    }

    private boolean checkIfTimeRecordExists(int userID) {
        String query = "SELECT COUNT(*) AS count FROM TimetableTime WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return false;
    }

    private void updateSchoolTime(int userID, int startTime, int endTime, int intervalTime) {
        String updateQuery = "UPDATE TimetableTime SET StartTime = ?, EndTime = ?, IntervalTime = ? WHERE UserID = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, startTime);
            preparedStatement.setInt(2, endTime);
            preparedStatement.setInt(3, intervalTime);
            preparedStatement.setInt(4, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }


    private void insertSchoolTime(int userID, int startTime, int endTime, int intervalTime) {
        String insertQuery = "INSERT INTO TimetableTime (UserID, StartTime, EndTime, IntervalTime) VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, startTime);
            preparedStatement.setInt(3, endTime);
            preparedStatement.setInt(4, intervalTime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    @FXML
    void updateClassInfo(ActionEvent event) throws IOException {

        String day = cbClassInfoActualDay.getValue();
        CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
        String classCode = selectedCourseInfo.getCourseCode();
        String selectedStartTime = StudyTimeDropDownBtn.getValue();

        // Find the row index based on the selected value
        int rowIndex = findRowIndexByCriteria(day, classCode);

        System.out.println("rowIndex: " + rowIndex);

        // Find the column index based on the selected value
        int columnIndex = findColumnIndexByHeader(selectedStartTime);

        System.out.println("columnIndex: " + columnIndex);

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

        int courseId = 0;
//        int courseId = 6969;
        if (selectedCourseInfo != null) {
            courseId = selectedCourseInfo.getCourseId();
            System.out.println("Selected Course ID: " + courseId);

            // Now you have the course ID and can use it as needed.
        } else {
            System.out.println("No course selected");
        }

        // Store the information in the database
        storeTimetableClassToDatabase(userID, courseId, rowIndex, columnIndex);

        // Clear input fields after processing and show prompt text
        cbClassInfoActualDay.setValue(null);

        // Disable "-- Select --" option in ComboBox
        StudyTimeDropDownBtn.getSelectionModel().clearSelection();
    }

    private void storeTimetableClassToDatabase(int userID, int courseID, int rowIndex, int columnIndex) {
        // Check if a record already exists for the user
        boolean recordExists = checkIfClassRecordColumnExists(userID, columnIndex);

        if (recordExists) {
            // If the record exists, update it
            updateClassTime(userID, courseID, rowIndex, columnIndex);
            System.out.println("TimetableTime record updated for UserID: " + userID);
        } else {
            // If the record doesn't exist, insert a new one
//            insertSchoolTime(userID, startDay, endDay);
            insertClassTime(userID, courseID, rowIndex, columnIndex);
            System.out.println("TimetableTime record inserted for UserID: " + userID);
        }
//        String insertQuery = "INSERT INTO TimetableClasses (UserID, CourseID, RowIndex, ColumnIndex) VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = JDBCConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
//
//            preparedStatement.setInt(1, userID);
//            preparedStatement.setInt(2, courseID);
//            preparedStatement.setInt(3, rowIndex);
//            preparedStatement.setInt(4, columnIndex);
//
//            preparedStatement.executeUpdate();
//            System.out.println("TimetableClasses record inserted for UserID: " + userID);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exceptions as needed
//        }
    }

//    private void updateOrInsertSchoolTimeInDatabase(int userID, int startTime, int endTime, int intervalTime) {
//        // Check if a record already exists for the user
//        boolean recordExists = checkIfTimeRecordExists(userID);
//
//        if (recordExists) {
//            // If the record exists, update it
//            updateSchoolTime(userID, startTime, endTime, intervalTime);
//            System.out.println("TimetableTime record updated for UserID: " + userID);
//        } else {
//            // If the record doesn't exist, insert a new one
////            insertSchoolTime(userID, startDay, endDay);
//            insertSchoolTime(userID, startTime, endTime, intervalTime);
//            System.out.println("TimetableTime record inserted for UserID: " + userID);
//        }
//    }

    private boolean checkIfClassRecordColumnExists(int userID, int columnIndex) {
        String query = "SELECT COUNT(*) AS count FROM TimetableClasses WHERE UserID = ? AND ColumnIndex = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, columnIndex);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return false;
    }



    private void updateClassTime(int userID, int courseID, int rowIndex, int columnIndex) {
//        String updateQuery = "UPDATE TimetableTime SET StartTime = ?, EndTime = ?, IntervalTime = ? WHERE UserID = ?";
//        String query = "SELECT COUNT(*) AS count FROM TimetableClasses WHERE UserID = ? AND ColumnIndex = ?";
        String updateQuery = "UPDATE TimetableClasses SET CourseID = ?, RowIndex = ? WHERE UserID = ? AND ColumnIndex = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, courseID);
            preparedStatement.setInt(2, rowIndex);
            preparedStatement.setInt(3, userID);
            preparedStatement.setInt(4, columnIndex);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }


    private void insertClassTime(int userID, int courseID, int rowIndex, int columnIndex) {
//        String updateQuery = "UPDATE TimetableClasses SET CourseID = ?, RowIndex = ? WHERE UserID = ? AND ColumnIndex = ?";
        String insertQuery = "INSERT INTO TimetableClasses (UserID, CourseID, RowIndex, ColumnIndex) VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, courseID);
            preparedStatement.setInt(3, rowIndex);
            preparedStatement.setInt(4, columnIndex);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

//    private void storeTimetableToDatabase() {
//        try {
//            Connection conn = JDBCConnection.getConnection();
//
//            // Use existing database connection
//            CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
//            String courseCode = selectedCourseInfo.getCourseCode();
//            int dayIndex = findRowIndexByCriteria(cbClassInfoActualDay.getValue(), courseCode);
//            int timeIndex = findColumnIndexByHeader(StudyTimeDropDownBtn.getValue());
//
//            // Insert the new course into the database
//            String query = "INSERT INTO timetable (UserID, RowIndex, ColumnIndex, ClassCode) VALUES (?, ?, ?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, userID);
//            pstmt.setInt(2, dayIndex);
//            pstmt.setInt(3, timeIndex);
//            pstmt.setString(4, courseCode);
//            pstmt.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void DashboardBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        loader = new FXMLLoader(getClass().getResource("TeacherHomePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(userID, StudentTeacherID,  fullName, role);
        homeController.fetchTimetableData();
        homeController.initialize();

        System.out.println(role);
    }

//    @FXML
//    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
//        // Load the new FXML file
//        if ("Student".equals(role)) {
//            CourseUpdatesBtn1.setVisible(false);
//        }
//        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
//        root = loader.load();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        // Pass any necessary data to the HomeController
//        HomeController homeController = loader.getController();
//        homeController.initData(userID, StudentTeacherID, fullName, role);
//
//        System.out.println(role);
//    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("CustomizePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        CustomizePageController customizePageController = loader.getController();
        customizePageController.setUserData(userID, StudentTeacherID, fullName, role);
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
        editTimeTableController.fetchTimetableData();
        System.out.println(userID);
        System.out.println(fullName);
        System.out.println(role);
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
        homeController.initData(userID, StudentTeacherID, fullName, role);

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
        homeController.initData(userID, StudentTeacherID, fullName, role);

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
}