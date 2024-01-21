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
import javafx.util.StringConverter;
import javafx.scene.control.TableCell;
import javafx.scene.control.ColorPicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.*;

public class CustomizeController implements Initializable {
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        fetchExistingTimeTable();

        weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

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

        initializeCourseInfoManually();
    }

    private void fetchExistingTimeTable() {
        try {
            // Clear existing data in the timetable
            schoolDay.clear();

            // Fetch timetable data from the database
            // Modify the query based on your database schema
            String query = "SELECT * FROM Timetable WHERE UserID = " + userID;
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String day = resultSet.getString("Day");
                String classCode = resultSet.getString("ClassCode");

                SchoolDay existingSchoolDay = new SchoolDay(day, classCode);
                schoolDay.add(existingSchoolDay);
            }

            // Update the timetable with the fetched data
            timetable.setItems(schoolDay);
            timetable.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Call a method to initialize course names
        initializeCourseInfo();
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

    void initializeCourseInfoManually() {
        // Create a list of CourseInfo objects
        List<CourseInfo> manuallyAddedCourses = Arrays.asList(
                new CourseInfo(1, "CSE101", "Introduction to Computer Science"),
                new CourseInfo(2, "MAT202", "Linear Algebra"),
                new CourseInfo(3, "BIO301", "Cell Biology")
                // Add more courses as needed
        );

        // Convert the list to an observable list
        ObservableList<CourseInfo> courses = FXCollections.observableArrayList(manuallyAddedCourses);

        // Set the items in the ComboBox
        if (courses.isEmpty()) {
            cbClassInfoClassCode.setPromptText("No course found");
        } else {
            cbClassInfoClassCode.setItems(courses);
        }
    }


    void initializeCourseInfo() {
//        connectDB = JDBCConnection.getConnection();
//        // Initialize database connection
//        try {
//            Statement statement = connectDB.createStatement();
//
//            String query = "SELECT * " +
//                    "FROM Courses " +
//                    "JOIN StudentCourses ON StudentCourses.CourseID = Courses.CourseID " +
//                    "JOIN Students ON Students.StudentID = StudentCourses.StudentID " +
//                    "WHERE Students.UserID = " + userID;
//
//            ResultSet resultSet = statement.executeQuery(query);
//
//            ObservableList<CourseInfo> courses = FXCollections.observableArrayList();
//
//            while (resultSet.next()) {
//                String courseCode = resultSet.getString("courseCode");
//                String courseName = resultSet.getString("CourseName");
//                int courseId = resultSet.getInt("CourseID");
//
//                CourseInfo courseInfo = new CourseInfo(courseId, courseCode, courseName);
//                courses.add(courseInfo);
//
//                System.out.println("Put course code into choicebox" + courseName);
//            }
//
//            if (courses.isEmpty()) {
//                cbClassInfoClassCode.setPromptText("No course found");
//            } else {
//                cbClassInfoClassCode.setItems(courses);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        initializeCourseInfoManually();
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
    @FXML
    void updateClassInfo(ActionEvent event) throws IOException {

        String day = cbClassInfoActualDay.getValue();
        CourseInfo selectedCourseInfo = cbClassInfoClassCode.getValue();
        String classCode = selectedCourseInfo.getCourseCode();
        String selectedStartTime = StudyTimeDropDownBtn.getValue();

//        int rowIndex = 3;  // Replace with the actual row index you want to insert data into
        int rowIndex = findRowIndexByCriteria(day, classCode);

        // Find the column index based on the selected value
        int columnIndex = findColumnIndexByHeader(selectedStartTime);

//        System.out.println("columnIndex: " + columnIndex);

        SchoolDay newClassInfo = new SchoolDay(day, classCode);

        // Check if the column index is within the valid range
        if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
            TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);

            //Set the cell factory to customize the rendering
            tableColumn.setCellFactory(column -> new TableCell<SchoolDay, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    // Display the cell's value
                    this.setText(item == null ? "" : item);

                    // Get the color from the ColorPicker
                    setText(item == null? "": item);

//                    //Set the cell color based on the color picked
//                    this.setStyle("-fx-background-color: " + toRgbaString(cellColor) + ";");

                    int currentRowIndex = getIndex();
                    int currentColumnIndex = getTableView().getColumns().indexOf(getTableColumn());

                    if (currentRowIndex == rowIndex && currentColumnIndex == columnIndex) {
                        Color cellColor = colorPickerbtn1.getValue();

                        setFont(Font.font("NewTimesRoman", FontWeight.BOLD, 12));

                        // Set the cell color based on the color picked
                        setBackground(empty ? null : new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
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

        // Clear input fields after processing
        cbClassInfoActualDay.setValue(null);
        StudyTimeDropDownBtn.getSelectionModel().clearSelection();
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
        CustomizeController customizeController = loader.getController();
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