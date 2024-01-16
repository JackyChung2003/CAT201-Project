package com.group_22.timetablemanagement;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditTimeTableController {

    @FXML
    private ComboBox<?> ClassEndDropDownBtn;

    // @FXML
    //// private ComboBox<?> ClassStartDropDownBtn;

    @FXML
    private ComboBox<String> ClassStartDropDownBtn;

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
    private AnchorPane anchorPane;

    @FXML
    private Button btBack;

    @FXML
    private Button btUpdateClassInfo;

    @FXML
    private Button btUpdateSchoolDay;

    @FXML
    private Button btUpdateStudyTime;

    @FXML
    private ComboBox<String> cbClassInfoActualDay;

    @FXML
    private ComboBox<String> cbClassTimeInterval;

    @FXML
    private ComboBox<String> cbSchoolDayStartsFrom;

    @FXML
    private ComboBox<String> cbSchoolDayStartsTo;

    @FXML
    private Label lbClassInfoCode;

    @FXML
    private Label lbClassInfoFromTo;

    @FXML
    private Label lbSchoolDayStartsFrom;

    @FXML
    private Label lbSchoolDayStartsTo;

    @FXML
    private Label lbStudyTimeStartsFromTo;

    @FXML
    private Label lbStudyTimeStartsTo;

    @FXML
    private TextField tfClassInfoCode;

    @FXML
    private TextField tfClassInfoFromHours;

    @FXML
    private TextField tfClassInfoFromMinutes;

    @FXML
    private TextField tfClassInfoToHours;

    @FXML
    private TextField tfClassInfoToMinutes;

    @FXML
    private TextField tfStudyTimeStartsFromHours;

    @FXML
    private TextField tfStudyTimeStartsFromMinutes;

    @FXML
    private TextField tfStudyTimeStartsToHours;

    @FXML
    private TextField tfStudyTimeStartsToMinutes;

    @FXML
    private TableView<SchoolDay> timetable;

    @FXML
    private TableColumn<SchoolDay, String> weekdaysColumn;

    @FXML
    private TableColumn<?, ?> timeColumn1;

    @FXML
    private TableColumn<?, ?> timeColumn2;

    @FXML
    private TableColumn<?, ?> timeColumn3;

    @FXML
    private TableColumn<?, ?> timeColumn4;

    @FXML
    private TableColumn<?, ?> timeColumn5;

    @FXML
    private TableColumn<?, ?> timeColumn6;

    List<TableColumn<SchoolDay, String>> createdColumns = new ArrayList<>();
    private ObservableList<SchoolDay> schoolDay = FXCollections.observableArrayList();

    private ObservableList<StudyTimeConfiguration> studyTimeConfigurations = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        // Add items to comboboxes
        cbSchoolDayStartsFrom.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"));
        cbSchoolDayStartsTo.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"));
        cbClassTimeInterval.setItems(FXCollections.observableArrayList("30 Minutes", "1 Hour"));
        cbClassInfoActualDay.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"));

        // Disable confirm button if any combobox inside SchoolDayStarts is empty
        BooleanBinding isAnyComboBoxSchoolDayStartsEmpty = cbSchoolDayStartsFrom.valueProperty().isNull()
                .or(cbSchoolDayStartsTo.valueProperty().isNull());

        btUpdateSchoolDay.disableProperty().bind(isAnyComboBoxSchoolDayStartsEmpty);

        // Disable confirm button if any text fields inside StudyTimeStarts is empty
        BooleanBinding areFieldsStudyTimeStartsEmpty = tfStudyTimeStartsFromHours.textProperty().isEmpty()
                .or(tfStudyTimeStartsFromMinutes.textProperty().isEmpty())
                .or(tfStudyTimeStartsToHours.textProperty().isEmpty())
                .or(tfStudyTimeStartsToMinutes.textProperty().isEmpty())
                .or(cbClassTimeInterval.valueProperty().isNull());

        btUpdateStudyTime.disableProperty().bind(areFieldsStudyTimeStartsEmpty);

        // Disable update button if any text fields inside ClassInfo is empty
        BooleanBinding areFieldsClassInfoEmpty = tfClassInfoCode.textProperty().isEmpty()
                // .or(tfClassInfoFromHours.textProperty().isEmpty())
                // .or(tfClassInfoFromMinutes.textProperty().isEmpty())
                // .or(tfClassInfoToHours.textProperty().isEmpty())
                // .or(tfClassInfoToMinutes.textProperty().isEmpty())
                .or(ClassStartDropDownBtn.valueProperty().isNull())
                .or(cbClassInfoActualDay.valueProperty().isNull());

        btUpdateClassInfo.disableProperty().bind(areFieldsClassInfoEmpty);
    }

    // @FXML
    // void updateClassInfo(ActionEvent event) throws IOException {
    //// String day = cbClassInfoActualDay.getValue();
    //// String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    //// String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    //// String classCode = tfClassInfoCode.getText();
    ////
    //// SchoolDay newClassInfo = new SchoolDay(day, classCode, startTime, endTime,
    // startTime);
    ////
    //// for (int day = 1; day <= 7; day++) {
    //// // Loop through hours (assuming hours are numbered from 8 to 12 with 1-hour
    // intervals)
    //// for (int hour = 8; hour <= 12; hour++) {
    //// // Set the cell value for each day and hour to "1"
    //// newClassInfo.setCellValue(day, hour, "1");
    //// }
    //// }
    ////
    //// // Add the newClassInfo to the timetable
    //// schoolDay.add(newClassInfo);
    //
    // // Get values from UI components (assuming you have these components declared
    // in your FXML)
    // String day = cbClassInfoActualDay.getValue();
    // String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    // String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    // String classCode = tfClassInfoCode.getText();
    //
    // // Create a new SchoolDay object
    // SchoolDay newClassInfo = new SchoolDay(day, classCode, startTime, endTime,
    // startTime);
    //
    // // Loop through days (assuming days are numbered from 1 to 7 for Monday to
    // Sunday)
    // for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
    // // Loop through hours (assuming hours are numbered from 8 to 12 with 1-hour
    // intervals)
    // for (int hour = 8; hour <= 12; hour++) {
    // // Set the cell value for each day and hour to "1"
    // newClassInfo.setCellValue(dayOfWeek, hour, "1");
    // }
    // }
    //
    // // Add the newClassInfo to the timetable
    // schoolDay.add(newClassInfo);
    //
    // // Refresh the timetable display
    // timetable.setItems(schoolDay);
    // timetable.refresh();
    //
    // // Clear UI components
    // tfClassInfoCode.clear();
    // cbClassInfoActualDay.getSelectionModel().clearSelection();
    // tfClassInfoFromHours.clear();
    // tfClassInfoFromMinutes.clear();
    // tfClassInfoToHours.clear();
    // tfClassInfoToMinutes.clear();
    //
    //// schoolDay.add(newClassInfo);
    ////
    //// timetable.setItems(schoolDay);
    //// timetable.refresh();
    ////
    //// tfClassInfoCode.clear();
    //// cbClassInfoActualDay.getSelectionModel().clearSelection();
    //// tfClassInfoFromHours.clear();
    //// tfClassInfoFromMinutes.clear();
    //// tfClassInfoToHours.clear();
    //// tfClassInfoToMinutes.clear();
    ////
    //// int row = 0;
    //// int columnFor9AM = findColumnIndex(9, 0);
    //
    //// if (columnFor9AM != -1) {
    ////// timetable.getItems().get(row).setCellValue(columnFor9AM, "Dummy Value");
    ////// timetable.refresh();
    //// // Convert the integer to a String before setting the cell value
    //// String dummyValue = String.valueOf(columnFor9AM);
    //// timetable.getItems().get(row).setCellValue(columnFor9AM, dummyValue);
    //// timetable.refresh();
    //// }
    //// if (columnFor9AM != -1) {
    //// String dummyValue = "Dummy Value";
    //// timetable.getItems().get(row).setCellValue(String.valueOf(columnFor9AM),
    // dummyValue);
    //// timetable.refresh();
    //// }
    // }

    @FXML
    void updateClassInfo(ActionEvent event) throws IOException {

        String day = cbClassInfoActualDay.getValue();
        String classCode = tfClassInfoCode.getText();
        String selectedStartTime = ClassStartDropDownBtn.getValue();

//        int rowIndex = 3;  // Replace with the actual row index you want to insert data into
        int rowIndex = findRowIndexByCriteria(day, classCode);

        // Find the column index based on the selected value
        int columnIndex = findColumnIndexByHeader(selectedStartTime);

        System.out.println("columnIndex: " + columnIndex);
        SchoolDay newClassInfo = new SchoolDay(day, classCode);

        // Check if the column index is within the valid range
//        if (columnIndex >= 0 && columnIndex < timetable.getColumns().size()) {
//            TableColumn<SchoolDay, String> tableColumn = (TableColumn<SchoolDay, String>) timetable.getColumns().get(columnIndex);
//
////            // Update the specific cell (row 1, column 1) with the new value
////            tableColumn.setCellValueFactory(cellData -> {
////                if (cellData.get().getRow() == rowIndex && cellData.getTableColumn() == tableColumn) {
////                    return new SimpleStringProperty(newClassInfo.getClassCode());
////                } else {
////                    // Return existing value for other cells
////                    return new SimpleStringProperty(cellData.getValue().getClassCode());
////                }
////            });
//
////            // Update the specific cell (row 1, column 1) with the new value
////            tableColumn.setCellValueFactory(cellData -> {
////                if (cellData.getValue().equals(newClassInfo) && cellData.getIndex() == rowIndex) {
////                    return new SimpleStringProperty(newClassInfo.getClassCode());
////                } else {
////                    // Return existing value for other cells
////                    return new SimpleStringProperty(cellData.getValue().getClassCode());
////                }
////            });
//
//            // Refresh the TableView to reflect the changes
//            timetable.refresh();
//        } else {
//            System.out.println("Invalid column index: " + columnIndex);
//        }


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
            //            tableColumn.setCellValueFactory(new PropertyValueFactory<>("classCode"));
            // Assuming you have a TableColumn object named "tableColumn" and a local variable named "newClassInfo"

//            tableColumn.setCellValueFactory(cellData -> {
//                if (cellData.get().getRow() == rowIndex && cellData.getTableColumn() == tableColumn) {
//                    return new SimpleStringProperty(newClassInfo.getClassCode());
//                } else {
//                    // Return existing value for other cells
//                    return new SimpleStringProperty(cellData.getValue().getClassCode());
//                }
//            });

            // Using a lambda expression
//            tableColumn.setCellValueFactory(data -> new SimpleStringProperty(newClassInfo.getClassCode()));           // working without adding new row, but duplicate value


            // Using a method reference
//            tableColumn.setCellValueFactory(new PropertyValueFactory<>(newClassInfo.getClassCode()));

//            tableColumn.setCellValueFactory(new PropertyValueFactory);
            // Get the SchoolDay object from the specified row
//            SchoolDay schoolDayObject = timetable.getItems().get(rowIndex);
//
//            // Set the value in the specified column and row
//            timetable.getColumns().get(columnIndex).setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassCode()));

////        yourColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(newClassInfo.getClassCode()));     // update all column with same value
////        yourColumn.setCellValueFactory(new PropertyValueFactory<>("classCode"));      // same row will have same memory

            // Refresh the TableView to reflect the changes
            timetable.refresh();
        } else {
            System.out.println("Invalid column index: " + columnIndex);
        }

        // Clear input fields after processing
        cbClassInfoActualDay.setValue(null);
        tfClassInfoCode.clear();
        ClassStartDropDownBtn.getSelectionModel().clearSelection();
    }

    // @FXML
    // void updateClassInfo(ActionEvent event) throws IOException {
    //
    // String day = cbClassInfoActualDay.getValue();
    //// String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    //// String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    // String classCode = tfClassInfoCode.getText();
    // String selectedStartTime = ClassStartDropDownBtn.getValue();
    //// String selectedStartTime = (String) ClassStartDropDownBtn.getValue();
    //
    //
    //// int columnIndex = 2; // Replace with the actual column index you want to
    // insert data into
    // int rowIndex = 0; // Replace with the actual row index you want to insert
    // data into
    //
    // // Assuming your ClassStartDropDownBtn items are in the format "HH:mm -
    // HH:mm"
    // String selectedColumnHeader = selectedStartTime;
    //
    // // Find the column index based on the selected value
    // int columnIndex = findColumnIndexByHeader(selectedColumnHeader);
    //
    // System.out.println("columnIndex" + columnIndex);
    //
    // SchoolDay newClassInfo = new SchoolDay(day, classCode);
    //
    // // Add the new SchoolDay to the TableView
    // timetable.getItems().add(newClassInfo);
    //
    //
    // // Get the SchoolDay object from the specified row
    // SchoolDay schoolDayObject = timetable.getItems().get(rowIndex);
    // System.out.println("This is timetable.getItems().get(rowIndex)" +
    // timetable.getItems().get(rowIndex));
    // System.out.println("This is timetable.getItems()" + timetable.getItems());
    // System.out.println("This is timetable.getColumns()" +
    // timetable.getColumns());
    // System.out.println("This is timetable.getColumns().get(columnIndex)" +
    // timetable.getColumns().get(columnIndex));
    // System.out.println("This is time" + timetable.getItems().get(rowIndex));
    // System.out.println("This is time" + timetable.getItems().get(rowIndex));
    //
    //
    // // Assuming columnHeader is the header of the column where you want to insert
    // data
    // String columnHeader = timetable.getColumns().get(columnIndex).getText();
    //
    // System.out.println("This is columnHeader" + columnHeader);
    //
    // TableColumn<SchoolDay, String> yourColumn = (TableColumn<SchoolDay, String>)
    // timetable.getColumns().get(columnIndex);
    // System.out.println(yourColumn);
    //// yourColumn.setCellValueFactory(cellData -> new
    // ReadOnlyObjectWrapper<>(newClassInfo.getClassCode())); // update all column
    // with same value
    //
    // // In case we have multiple columns with percent-values it
    // // might come in handy to store our formatter
    //// Callback<TableColumn, TableCell> test =
    //// new Callback<TableColumn, TableCell>() {
    //// public TableCell call(TableColumn p) {
    //// return new PercantageFormatCell();
    //// }
    //// };
    //// yourColumn.setCellFactory(test);
    //
    //// Callback<TableColumn<SchoolDay, String>, TableCell<SchoolDay, String>> test
    // =
    //// new Callback<TableColumn<SchoolDay, String>, TableCell<SchoolDay,
    // String>>() {
    //// public TableCell<SchoolDay, String> call(TableColumn<SchoolDay, String> p)
    // {
    //// return new PercantageFormatCell();
    //// }
    //// };
    //// yourColumn.setCellFactory(test);
    //// yourColumn.setCellValueFactory(new PropertyValueFactory<SchoolDay,
    // String>("classCode"));
    //// yourColumn.setCellFactory(new <TableColumn<SchoolDay,
    // String>,TableCell<SchoolDay, String>>(classCode));
    //// yourColumn.setCellFactory(column -> new PercantageFormatCell());
    //
    //
    //// Callback<TableColumn<SchoolDay, String>, TableCell<SchoolDay, String>> test
    // =
    //// new Callback<TableColumn<SchoolDay, String>, TableCell<SchoolDay,
    // String>>() {
    //// public TableCell<SchoolDay, String> call(TableColumn<SchoolDay, String> p)
    // {
    //// return new PercantageFormatCell();
    //// }
    //// };
    //// yourColumn.setCellFactory(test);
    // // Set a custom cell factory for formatting
    //// yourColumn.setCellFactory(column -> new PercantageFormatCell());
    //
    //// yourColumn.setCellValueFactory(new PropertyValueFactory<>("classCode")); //
    // same row will have same memory
    //
    //// yourColumn.setCellFactory(E -> new TableCellTest<String, String>());
    //// yourColumn.setCellValueFactory(E -> new
    // SimpleStringProperty(E.getValue()));
    //
    // // Set a custom cell factory for formatting
    // yourColumn.setCellFactory(column -> new TableCellTest<>());
    //
    // // Set the value in the specified column and row
    // yourColumn.setCellValueFactory(data -> new
    // SimpleStringProperty(data.getValue().getClassCode()));
    //
    // yourColumn.setEditable(true);
    //
    //// TableColumn<SchoolDay, String> yourColumn = new TableColumn<>();
    //// yourColumn.setCellFactory(c-> new
    // SimpleStringProperty(c.getValue().getTestBedName()));
    //// yourColumn.setCellValueFactory(cellData -> new
    // ReadOnlyObjectWrapper<>(Double.toString(cellData.getValue().getAmount())));
    //// // Set the value in the specified column and row
    //// String newValue = "your data"; // Replace with the actual data you want to
    // set
    //// yourColumn.getCellObservableValue(rowIndex).setValue(newValue);
    //
    //
    // // Set the value in the specified column and row
    //// schoolDayObject.setCellValue(columnHeader, "your data");
    //
    // // Refresh the TableView to reflect the changes
    // timetable.refresh();
    //
    // // Clear input fields after processing
    //
    // cbClassInfoActualDay.setValue(null);
    // tfClassInfoCode.clear();
    // ClassStartDropDownBtn.getSelectionModel().clearSelection();
    // }

    // public static class TableCellTest<S, T> extends TableCell<S, T>
    // {
    //
    // @Override
    // protected void updateItem(T item, boolean empty)
    // {
    // super.updateItem(item, empty);
    //
    // // dipslays cells' value
    // this.setText((String)this.getItem());
    //
    // // checks cells index and set its color.
    // if(this.getIndex() < 12)
    // this.setStyle("-fx-background-color: rgba(253, 255, 150, 0.4);");
    // else this.setStyle("");
    // }
    // }

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

    // @FXML
    // void updateClassInfo(ActionEvent event) throws IOException {
    // String day = cbClassInfoActualDay.getValue();
    //// String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    //// String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    // String classCode = tfClassInfoCode.getText();
    // String selectedStartTime = ClassStartDropDownBtn.getValue();
    //// String selectedStartTime = (String) ClassStartDropDownBtn.getValue();
    //
    //
    //// int columnIndex = 2; // Replace with the actual column index you want to
    // insert data into
    // int rowIndex = 0; // Replace with the actual row index you want to insert
    // data into
    //
    // // Assuming your ClassStartDropDownBtn items are in the format "HH:mm -
    // HH:mm"
    // String selectedColumnHeader = selectedStartTime;
    //
    // // Find the column index based on the selected value
    // int columnIndex = findColumnIndexByHeader(selectedColumnHeader);
    //
    // System.out.println("columnIndex" + columnIndex);
    //
    //// if (columnIndex != -1) {
    //// // Get the SchoolDay object from the specified row
    //// SchoolDay schoolDayObject = timetable.getItems().get(rowIndex);
    ////
    //// // Set the value in the specified column and row
    //// schoolDayObject.setCellValue(String.valueOf(columnIndex), "your data");
    ////
    //// // Refresh the TableView to reflect the changes
    //// timetable.refresh();
    //// } else {
    //// System.out.println("Column not found for header: " + selectedColumnHeader);
    //// }
    //
    // SchoolDay newClassInfo = new SchoolDay(day, classCode);
    //
    // // Add the new SchoolDay to the TableView
    // timetable.getItems().add(newClassInfo);
    //
    //
    // // Get the SchoolDay object from the specified row
    // SchoolDay schoolDayObject = timetable.getItems().get(rowIndex);
    // System.out.println("This is timetable.getItems().get(rowIndex)" +
    // timetable.getItems().get(rowIndex));
    // System.out.println("This is timetable.getItems()" + timetable.getItems());
    // System.out.println("This is timetable.getColumns()" +
    // timetable.getColumns());
    // System.out.println("This is timetable.getColumns().get(columnIndex)" +
    // timetable.getColumns().get(columnIndex));
    // System.out.println("This is time" + timetable.getItems().get(rowIndex));
    // System.out.println("This is time" + timetable.getItems().get(rowIndex));
    //
    //
    // // Assuming columnHeader is the header of the column where you want to insert
    // data
    // String columnHeader = timetable.getColumns().get(columnIndex).getText();
    //
    // System.out.println("This is columnHeader" + columnHeader);
    //
    // TableColumn<SchoolDay, String> yourColumn = (TableColumn<SchoolDay, String>)
    // timetable.getColumns().get(columnIndex);
    // System.out.println(yourColumn);
    // yourColumn.setCellValueFactory(new PropertyValueFactory<>("classCode"));
    //
    //// TableColumn<SchoolDay, String> yourColumn = new TableColumn<>();
    //// yourColumn.setCellFactory(c-> new
    // SimpleStringProperty(c.getValue().getTestBedName()));
    //// yourColumn.setCellValueFactory(cellData -> new
    // ReadOnlyObjectWrapper<>(Double.toString(cellData.getValue().getAmount())));
    //// // Set the value in the specified column and row
    //// String newValue = "your data"; // Replace with the actual data you want to
    // set
    //// yourColumn.getCellObservableValue(rowIndex).setValue(newValue);
    //
    //
    // // Set the value in the specified column and row
    //// schoolDayObject.setCellValue(columnHeader, "your data");
    //
    // // Refresh the TableView to reflect the changes
    // timetable.refresh();
    //
    // // Clear input fields after processing
    // cbClassInfoActualDay.setValue(null);
    // tfClassInfoCode.clear();
    // ClassStartDropDownBtn.getSelectionModel().clearSelection();
    // }

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
    // String day = cbClassInfoActualDay.getValue();
    // String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    // String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    // String classCode = tfClassInfoCode.getText();
    //
    // System.out.println(day);
    // System.out.println(startTime);
    // System.out.println(endTime);
    // System.out.println(classCode);
    //
    // SchoolDay newClassInfo = new SchoolDay(day, classCode, startTime, endTime,
    // startTime);
    //
    // timetable.getItems().add(newClassInfo);
    // }

    // @FXML
    // void updateClassInfo(ActionEvent event) throws IOException {
    // // Get values from UI components
    // String day = cbClassInfoActualDay.getValue();
    // String startTime = tfClassInfoFromHours.getText() + ":" +
    // tfClassInfoFromMinutes.getText();
    // String endTime = tfClassInfoToHours.getText() + ":" +
    // tfClassInfoToMinutes.getText();
    // String classCode = tfClassInfoCode.getText();
    //
    // System.out.println(day);
    // System.out.println(startTime);
    // System.out.println(endTime);
    // System.out.println(classCode);
    //
    // // Create a new SchoolDay object
    // SchoolDay newClassInfo = new SchoolDay(day, classCode, startTime, endTime,
    // startTime);
    //
    // // Assuming that SchoolDay has appropriate methods for setting values in
    // columns 2, 3, and 4
    //// newClassInfo.setCellValue(2, "your data for column 2");
    //// newClassInfo.setCellValue(3, "your data for column 3");
    //// newClassInfo.setCellValue(4, "your data for column 4");
    //
    // int dayValue = 1; // Replace with the actual day value (1 for Monday, 2 for
    // Tuesday, etc.)
    // int hourValueColumn2 = 9; // Replace with the actual hour value for column 2
    // int hourValueColumn3 = 10; // Replace with the actual hour value for column 3
    //
    // // Example: Set values for columns 2, 3, and 4
    // newClassInfo.setCellValue(dayValue, hourValueColumn2, "your data for column
    // 2");
    // newClassInfo.setCellValue(dayValue, hourValueColumn3, "your data for column
    // 3");
    //
    // // Assuming that SchoolDay has appropriate methods for setting values in
    // columns 2, 3, and 4
    //// newClassInfo.setColumnValues("your data for column 2", "your data for
    // column 3", "your data for column 4");
    //
    //
    //
    // // Add the newClassInfo to the timetable
    // schoolDay.add(newClassInfo);
    //
    // // Refresh the timetable display
    // timetable.setItems(schoolDay);
    // timetable.refresh();
    //
    // // Clear UI components
    // tfClassInfoCode.clear();
    // cbClassInfoActualDay.getSelectionModel().clearSelection();
    // tfClassInfoFromHours.clear();
    // tfClassInfoFromMinutes.clear();
    // tfClassInfoToHours.clear();
    // tfClassInfoToMinutes.clear();
    // }

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
    }

    @FXML
    void updateStudyTime(ActionEvent event) throws IOException {
        boolean checkStudyTimeFromTo = false;
        String studyTimeStartFromHours, studyTimeStartFromMinutes, studyTimeStartToHours, studyTimeStartToMinutes,
                studyTimeStartInterval;
        studyTimeStartFromHours = tfStudyTimeStartsFromHours.getText();
        studyTimeStartFromMinutes = tfStudyTimeStartsFromMinutes.getText();
        studyTimeStartToHours = tfStudyTimeStartsToHours.getText();
        studyTimeStartToMinutes = tfStudyTimeStartsToMinutes.getText();
        studyTimeStartInterval = cbClassTimeInterval.getValue();
        int timeIntervalInt;

        ClassStartDropDownBtn.getItems().clear();

        System.out.println("Real interval" + studyTimeStartInterval);

        if (Integer.parseInt(studyTimeStartFromHours) >= 0 && Integer.parseInt(studyTimeStartFromHours) <= 24
                && (Integer.parseInt(studyTimeStartFromMinutes) == 0
                        || Integer.parseInt(studyTimeStartFromMinutes) == 30)
                && Integer.parseInt(studyTimeStartToHours) >= 0 && Integer.parseInt(studyTimeStartToHours) <= 24
                && (Integer.parseInt(studyTimeStartToMinutes) == 0
                        || Integer.parseInt(studyTimeStartToMinutes) == 30)) {
            checkStudyTimeFromTo = true;
            lbStudyTimeStartsFromTo.setText("");
        } else {
            checkStudyTimeFromTo = false;
            lbStudyTimeStartsFromTo.setText("From & To Hours (00 - 24) and Minutes (00 or 30)");
        }

        if (checkStudyTimeFromTo) {
            lbClassInfoCode.setText("");
            timetable.getColumns().removeAll(createdColumns);
            createdColumns.clear();

            // int interval = Integer.parseInt(studyTimeStartInterval.split(" ")[0]);
            // Set timeIntervalInt based on the selected value in cbClassTimeInterval

            if ("30 Minutes".equals(studyTimeStartInterval)) {
                timeIntervalInt = 30;
            } else if ("1 Hour".equals(studyTimeStartInterval)) {
                timeIntervalInt = 60;
            } else {
                // Handle the case where an unexpected value is selected
                timeIntervalInt = 0; //
            }

            // StudyTimeConfiguration studyTimeConfiguration = new StudyTimeConfiguration(
            // studyTimeStartFromHours + ":" + studyTimeStartFromMinutes,
            // studyTimeStartToHours + ":" + studyTimeStartToMinutes,
            // timeIntervalInt
            // );
            //
            // // Store the study time configuration for later access
            // storeStudyTimeConfiguration(studyTimeConfiguration);
            //
            // // Initialize the ClassStartDropDownBtn with values based on the
            // configuration
            // initializeClassStartDropDown();

            // System.out.println(interval);

            // for (int i = Integer.parseInt(studyTimeStartFromHours); i <=
            // Integer.parseInt(studyTimeStartToHours); i++) {
            // if (interval == 30 && studyTimeStartFromMinutes.equals("00") &&
            // studyTimeStartToMinutes.equals("00")) {
            // for (int j = 0; j < 60; j += interval) {
            // if (i == Integer.parseInt(studyTimeStartToHours) && j == 30) {
            // break;
            // }
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // String.format("%02d", j));
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // } else if (interval == 30 && studyTimeStartFromMinutes.equals("30") &&
            // studyTimeStartToMinutes.equals("30")) {
            // for (int j = (i == Integer.parseInt(studyTimeStartFromHours)) ? 30 : 0; j <
            // 60; j += interval) {
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // String.format("%02d", j));
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // if (j == 30) {
            // break;
            // }
            // }
            // } else if (interval == 30 && studyTimeStartFromMinutes.equals("00") &&
            // studyTimeStartToMinutes.equals("30")) {
            // for (int j = 0; j < 60; j += interval) {
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // String.format("%02d", j));
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // } else if (interval == 30 && studyTimeStartFromMinutes.equals("30") &&
            // studyTimeStartToMinutes.equals("00")) {
            // for (int j = (i == Integer.parseInt(studyTimeStartFromHours)) ? 30 : 0; j <
            // 60; j += interval) {
            // if (i == Integer.parseInt(studyTimeStartToHours) && j == 30) {
            // break;
            // }
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // String.format("%02d", j));
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // } else if (interval == 30 && studyTimeStartFromMinutes.equals("30") &&
            // studyTimeStartToMinutes.equals("30")) {
            // for (int j = 30; j < 60; j += interval) {
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // String.format("%02d", j));
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // } else if (interval == 1 && studyTimeStartFromMinutes.equals("30") &&
            // studyTimeStartToMinutes.equals("30")) {
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // studyTimeStartFromMinutes);
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            //
            // } else if (interval == 1 && studyTimeStartFromMinutes.equals("00") &&
            // studyTimeStartToMinutes.equals("00")) {
            // TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" +
            // studyTimeStartFromMinutes);
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // } else {
            // lbStudyTimeStartsFromTo.setText("Invalid study time. Please try again.");
            // }
            // }

            // for (int i = Integer.parseInt(studyTimeStartFromHours); i <=
            // Integer.parseInt(studyTimeStartToHours); i++) {
            // for (int j = 0; j < 60; j += timeIntervalInt) {
            // int endMinute = j + timeIntervalInt;
            //
            // if (i == Integer.parseInt(studyTimeStartToHours) && endMinute > 60) {
            // endMinute = 60;
            // }
            //
            // String startTime = String.format("%02d:%02d", i, j);
            // String endTime = String.format("%02d:%02d", i + (endMinute / 60), endMinute %
            // 60);
            //
            // String columnHeader = startTime + " - " + endTime;
            // TableColumn<SchoolDay, String> column = new TableColumn<>(columnHeader);
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // }
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
                    ClassStartDropDownBtn.getItems().add(columnHeader);
                    System.out.println(columnHeader + "is added into drop down");
                }
            }

            // for (int i = Integer.parseInt(studyTimeStartFromHours); i <=
            // Integer.parseInt(studyTimeStartToHours); i++) {
            // for (int j = 0; j < 60; j += interval) {
            // if (i == Integer.parseInt(studyTimeStartToHours) && j == 30) {
            // break;
            // }
            //
            // int endMinute = j + 60;
            // if (endMinute >= 60) {
            // endMinute = 0;
            // }
            //
            // String startTime = String.format("%02d:%02d", i, j);
            // String endTime = String.format("%02d:%02d", i + (endMinute / 60), endMinute %
            // 60);
            //
            // String columnHeader = startTime + " - " + endTime;
            // TableColumn<SchoolDay, String> column = new TableColumn<>(columnHeader);
            // timetable.getColumns().add(column);
            // createdColumns.add(column);
            // }
            // }

        }
    }

    // Method to store the study time configuration
    // private void storeStudyTimeConfiguration(StudyTimeConfiguration
    // studyTimeConfiguration) {
    // // You can store it in a list, in a database, or any other suitable data
    // structure
    // // For simplicity, let's assume you have a list to store multiple
    // configurations
    // List<StudyTimeConfiguration> studyTimeConfigurations = new ArrayList<>();
    // studyTimeConfigurations.add(studyTimeConfiguration);
    //
    // // Later, you can access the stored configurations from the list
    // for (StudyTimeConfiguration config : studyTimeConfigurations) {
    // System.out.println("Start Time: " + config.getStartTime());
    // System.out.println("End Time: " + config.getEndTime());
    // System.out.println("Interval: " + config.getInterval());
    // }
    // }

    // Method to initialize the ClassStartDropDownBtn with values based on the study
    // time configuration
    // private void initializeClassStartDropDown() {
    // ClassStartDropDownBtn.getItems().clear(); // Clear existing items
    //
    // // Retrieve the stored study time configurations
    // for (StudyTimeConfiguration config : studyTimeConfigurations) {
    // // Generate a string representation of the study time
    // String studyTimeString = config.getStartTime() + " - " + config.getEndTime();
    // // Add the string representation to the ComboBox items
    // ClassStartDropDownBtn.getItems().add(studyTimeString);
    // }
    // }

    @FXML
    void onClassStartDropDownClicked(ActionEvent event) {
        int selectedIndex = ClassStartDropDownBtn.getSelectionModel().getSelectedIndex();
        System.out.println("Selected Index: " + (selectedIndex + 1));
        String selectedValue = ClassStartDropDownBtn.getValue();
        System.out.println("Selected Value: " + selectedValue);
    }

    @FXML
    void CourseUpdatesBtn1OnClicked(ActionEvent event) {

    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void DashboardBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void EditPersonalBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void EditTimeTableBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void ModifyDueDatesBtnOnClicked(ActionEvent event) {

    }

    @FXML
    void SignOutBtnOnClicked(ActionEvent event) {

    }
}
