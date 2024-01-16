//package com.group_22.timetablemanagement;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//import javafx.beans.binding.BooleanBinding;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import javafx.fxml.Initializable;
//
//public class ManualPageController implements Initializable{
//    @FXML
//    private TableView<SchoolDay> timetable;
//
//    @FXML
//    private TableColumn<SchoolDay, String> weekdaysColumn;
//
//    @FXML
//    private Button btBack;
//
//    @FXML
//    private Button btUpdateSchoolDay;
//
//    @FXML
//    private Button btUpdateStudyTime;
//
//    @FXML
//    private Button btUpdateClassInfo;
//
//    @FXML
//    private ComboBox<String> cbSchoolDayStartsFrom;
//
//    @FXML
//    private ComboBox<String> cbSchoolDayStartsTo;
//
//    @FXML
//    private ComboBox<String> cbClassTimeInterval;
//
//    @FXML
//    private ComboBox<String> cbClassInfoActualDay;
//
//    @FXML
//    private TextField tfStudyTimeStartsFromHours;
//
//    @FXML
//    private TextField tfStudyTimeStartsFromMinutes;
//
//    @FXML
//    private TextField tfStudyTimeStartsToHours;
//
//    @FXML
//    private TextField tfStudyTimeStartsToMinutes;
//
//    @FXML
//    private TextField tfClassInfoFromHours;
//
//    @FXML
//    private TextField tfClassInfoFromMinutes;
//
//    @FXML
//    private TextField tfClassInfoToHours;
//
//    @FXML
//    private TextField tfClassInfoToMinutes;
//
//    @FXML
//    private TextField tfClassInfoCode;
//
//    @FXML
//    private Label lbSchoolDayStartsFrom;
//
//    @FXML
//    private Label lbSchoolDayStartsTo;
//
//    @FXML
//    private Label lbStudyTimeStartsFromTo;
//
//    @FXML
//    private Label lbClassInfoCode;
//
//    @FXML
//    private Label lbClassInfoFromTo;
//
//    @FXML
//    public void switchToMain(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
//        Parent newView = loader.load();
//        Scene scene = new Scene(newView);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //Set weekdaysColumn to display day
//        weekdaysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
//
//        //Add items to comboboxes
//        cbSchoolDayStartsFrom.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
//        cbSchoolDayStartsTo.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
//        cbClassTimeInterval.setItems(FXCollections.observableArrayList("30 Minutes", "1 Hours"));
//        cbClassInfoActualDay.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
//
//        //Disable confirm button if any combobox inside SchoolDayStarts is empty
//        BooleanBinding isAnyComboBoxSchoolDayStartsEmpty =
//                cbSchoolDayStartsFrom.valueProperty().isNull()
//                        .or(cbSchoolDayStartsTo.valueProperty().isNull());
//
//        btUpdateSchoolDay.disableProperty().bind(isAnyComboBoxSchoolDayStartsEmpty);
//
//        //Disable confirm button if any text fields inside StudyTimeStarts is empty
//        BooleanBinding areFieldsStudyTimeStartsEmpty =
//                tfStudyTimeStartsFromHours.textProperty().isEmpty()
//                        .or(tfStudyTimeStartsFromMinutes.textProperty().isEmpty())
//                        .or(tfStudyTimeStartsToHours.textProperty().isEmpty())
//                        .or(tfStudyTimeStartsToMinutes.textProperty().isEmpty())
//                        .or(cbClassTimeInterval.valueProperty().isNull());
//
//        btUpdateStudyTime.disableProperty().bind(areFieldsStudyTimeStartsEmpty);
//
//        //Disable update button if any text fields inside ClassInfo is empty
//        BooleanBinding areFieldsClassInfoEmpty =
//                tfClassInfoCode.textProperty().isEmpty()
//                        .or(tfClassInfoFromHours.textProperty().isEmpty())
//                        .or(tfClassInfoFromMinutes.textProperty().isEmpty())
//                        .or(tfClassInfoToHours.textProperty().isEmpty())
//                        .or(tfClassInfoToMinutes.textProperty().isEmpty())
//                        .or(cbClassInfoActualDay.valueProperty().isNull());
//
//        btUpdateClassInfo.disableProperty().bind(areFieldsClassInfoEmpty);
//    }
//
//    private ObservableList<SchoolDay> schoolDay = FXCollections.observableArrayList();
//
//    @FXML
//    public void updateSchoolDay(ActionEvent event) throws IOException {
//        Map<String, String> dayToNumber = new HashMap<>();
//        dayToNumber.put("Monday", "1");
//        dayToNumber.put("Tuesday", "2");
//        dayToNumber.put("Wednesday", "3");
//        dayToNumber.put("Thursday", "4");
//        dayToNumber.put("Friday", "5");
//        dayToNumber.put("Saturday", "6");
//        dayToNumber.put("Sunday", "7");
//
//        String schoolDayStartsFrom = dayToNumber.get(cbSchoolDayStartsFrom.getValue());
//        String schoolDayStartsTo = dayToNumber.get(cbSchoolDayStartsTo.getValue());
//
//        schoolDay.clear();
//
//        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//
//        // Add row for each school day
//        for (int i = Integer.parseInt(schoolDayStartsFrom); i <= Integer.parseInt(schoolDayStartsTo); i++) {
//            SchoolDay newschoolDay = new SchoolDay(days[i - 1]);
//            schoolDay.add(newschoolDay);
//        }
//
//        // Add school day to table
//        timetable.setItems(schoolDay);
//        timetable.refresh();
//
//    }
//
//    List<TableColumn<SchoolDay, String>> createdColumns = new ArrayList<>();
//
//    public void updateStudyTime(ActionEvent event) throws IOException {
//        boolean checkStudyTimeFromTo = false;
//        String studyTimeStartFromHours, studyTimeStartFromMinutes, studyTimeStartToHours, studyTimeStartToMinutes, studyTimeStartInterval;
//        studyTimeStartFromHours = tfStudyTimeStartsFromHours.getText();
//        studyTimeStartFromMinutes = tfStudyTimeStartsFromMinutes.getText();
//        studyTimeStartToHours = tfStudyTimeStartsToHours.getText();
//        studyTimeStartToMinutes = tfStudyTimeStartsToMinutes.getText();
//        studyTimeStartInterval = cbClassTimeInterval.getValue();
//
//        if(Integer.parseInt(studyTimeStartFromHours) >= 0 && Integer.parseInt(studyTimeStartFromHours) <= 24 && (Integer.parseInt(studyTimeStartFromMinutes) == 0 || Integer.parseInt(studyTimeStartFromMinutes) == 30) && Integer.parseInt(studyTimeStartToHours) >= 0 && Integer.parseInt(studyTimeStartToHours) <= 24 && (Integer.parseInt(studyTimeStartToMinutes) == 0 || Integer.parseInt(studyTimeStartToMinutes) == 30)){
//            checkStudyTimeFromTo = true;
//            lbStudyTimeStartsFromTo.setText("");
//        }
//        else{
//            checkStudyTimeFromTo = false;
//            lbStudyTimeStartsFromTo.setText("From & To Hours (00 - 24) and Minutes (00 or 30)");
//        }
//
//        if(checkStudyTimeFromTo == true){
//            lbClassInfoCode.setText("");
//            // Remove only created columns
//            timetable.getColumns().removeAll(createdColumns);
//            createdColumns.clear();
//            int interval = Integer.parseInt(studyTimeStartInterval.split(" ")[0]);
//            for (int i = Integer.parseInt(studyTimeStartFromHours); i <= Integer.parseInt(studyTimeStartToHours); i++) {
//                if (interval == 30 && studyTimeStartFromMinutes.equals("00") && studyTimeStartToMinutes.equals("00")) {
//                    for (int j = 0; j < 60; j += interval) {
//                        if (i == Integer.parseInt(studyTimeStartToHours) && j == 30) {
//                            break;
//                        }
//                        TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + String.format("%02d", j));
//                        timetable.getColumns().add(column);
//                        // Add column to list of created columns
//                        createdColumns.add(column);
//                    }
//                }
//                else if (interval == 30 && studyTimeStartFromMinutes.equals("30") && studyTimeStartToMinutes.equals("30")) {
//                    for (int j = (i == Integer.parseInt(studyTimeStartFromHours)) ? 30 : 0; j < 60; j += interval) {
//                        TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + String.format("%02d", j));
//                        timetable.getColumns().add(column);
//                        // Add column to list of created columns
//                        createdColumns.add(column);
//                        if (j == 30) {
//                            break;
//                        }
//                    }
//                }
//                else if (interval == 30 && studyTimeStartFromMinutes.equals("00") && studyTimeStartToMinutes.equals("30")) {
//                    for (int j = 0; j < 60; j += interval) {
//                        TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + String.format("%02d", j));
//                        timetable.getColumns().add(column);
//                        // Add column to list of created columns
//                        createdColumns.add(column);
//                    }
//                }
//                else if (interval == 30 && studyTimeStartFromMinutes.equals("30") && studyTimeStartToMinutes.equals("00")) {
//                    for (int j = (i == Integer.parseInt(studyTimeStartFromHours)) ? 30 : 0; j < 60; j += interval) {
//                        if (i == Integer.parseInt(studyTimeStartToHours) && j == 30) {
//                            break;
//                        }
//                        TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + String.format("%02d", j));
//                        timetable.getColumns().add(column);
//                        // Add column to list of created columns
//                        createdColumns.add(column);
//                    }
//                }
//                else if (interval == 30 && studyTimeStartFromMinutes.equals("30") && studyTimeStartToMinutes.equals("30")) {
//                    for (int j = 30; j < 60; j += interval) {
//                        TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + String.format("%02d", j));
//                        timetable.getColumns().add(column);
//                        // Add column to list of created columns
//                        createdColumns.add(column);
//                    }
//                }
//                else if (interval == 1 && studyTimeStartFromMinutes.equals("30") && studyTimeStartToMinutes.equals("30")) {
//                    TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + studyTimeStartFromMinutes);
//                    timetable.getColumns().add(column);
//                    // Add column to list of created columns
//                    createdColumns.add(column);
//
//                }
//                else if (interval == 1 && studyTimeStartFromMinutes.equals("00") && studyTimeStartToMinutes.equals("00")) {
//                    TableColumn<SchoolDay, String> column = new TableColumn<>(i + ":" + studyTimeStartFromMinutes);
//                    timetable.getColumns().add(column);
//                    // Add column to list of created columns
//                    createdColumns.add(column);
//                }
//                else{
//                    lbStudyTimeStartsFromTo.setText("Invalid study time. Please try again.");
//                }
//            }
//            //Add class info to database here
//        }
//
//    }
//
//    // @FXML
//    // public void updateClassInfo(ActionEvent event) throws IOException {
//    //     boolean checkClassInfoClassCode, checkClassInfoFromTo = false;
//    //     String classCode, classInfoFromHours, classInfoFromMinutes, classInfoToHours, classInfoToMinutes;
//    //     classInfoFromHours = tfClassInfoFromHours.getText();
//    //     classInfoFromMinutes = tfClassInfoFromMinutes.getText();
//    //     classInfoToHours = tfClassInfoToHours.getText();
//    //     classInfoToMinutes = tfClassInfoToMinutes.getText();
//    //     classCode = tfClassInfoCode.getText();
//
//    //     if(Integer.parseInt(classInfoFromHours) >= 0 && Integer.parseInt(classInfoFromHours) <= 24 && (Integer.parseInt(classInfoFromMinutes) == 0 || Integer.parseInt(classInfoFromMinutes) == 30) && Integer.parseInt(classInfoToHours) >= 0 && Integer.parseInt(classInfoToHours) <= 24 && (Integer.parseInt(classInfoToMinutes) == 0 || Integer.parseInt(classInfoToMinutes) == 30)){
//    //         checkClassInfoFromTo = true;
//    //         lbClassInfoFromTo.setText("");
//    //     }
//    //     else{
//    //         checkClassInfoFromTo = false;
//    //         lbClassInfoFromTo.setText("From & To Hours (00 - 24) and Minutes (00 or 30)");
//    //     }
//
//    //     classCode = tfClassInfoCode.getText();
//
//    //     if (classCode.length() == 6) {
//    //         checkClassInfoClassCode = true;
//    //         lbClassInfoCode.setText("");
//    //     }
//    //     else {
//    //         checkClassInfoClassCode = false;
//    //         lbClassInfoCode.setText("Class Code must be 6 characters.");
//    //     }
//
//    //     int dayIndex = cbClassInfoActualDay.getSelectionModel().getSelectedIndex();
//    //     // int hourIndex = -1;
//    //     // if (!timetable.getSelectionModel().getSelectedCells().isEmpty()) {
//    //     //     hourIndex = timetable.getColumns().indexOf(timetable.getSelectionModel().getSelectedCells().get(0).getTableColumn());
//    //     // }
//    //     int columnIndex = -1;
//    //     // for (TableColumn<SchoolDay, ?> column : timetable.getColumns()) {
//    //     //     if (column.getText().equals(classInfoFromHours + ":" + classInfoFromMinutes)) {
//    //     //         columnIndex = timetable.getColumns().indexOf(column);
//    //     //         break;
//    //     //     }
//    //     // }
//
//    //     if (checkClassInfoFromTo && checkClassInfoClassCode) {
//    //         lbClassInfoCode.setText("");
//
//    //         // Assuming a simple scenario where we add the values as Strings
//    //         ObservableList<String> rowData = FXCollections.observableArrayList(classCode, cbClassInfoActualDay.getValue(), classInfoFromHours + ":" + classInfoFromMinutes, classInfoToHours + ":" + classInfoToMinutes);
//
//    //         // Clear the text fields and combo box after updating the table
//    //         // tfClassInfoCode.clear();
//    //         // cbClassInfoActualDay.getSelectionModel().clearSelection();
//
//    //         System.out.println("Class Code: " + classCode.toUpperCase());
//    //         System.out.println("Class Time: " + classInfoFromHours + ":" + classInfoFromMinutes + " - " + classInfoToHours + ":" + classInfoToMinutes);
//    //         System.out.println("Class Day: " + dayIndex);
//    //         System.out.println("Class Hour: " + columnIndex);
//    //     }
//    //         // Clear the text field and combo box after adding to the table
//    //         // tfClassInfoCode.clear();
//    //         // cbClassInfoActualDay.getSelectionModel().clearSelection();
//    //         // rowData.set(columnIndex, classCode.toUpperCase());
//    //         // timetable.getItems().get(dayIndex + 1).set(2, classCode.toUpperCase());
//    //         //Add class info to database here
//    //         // if (dayIndex < timetable.getItems().size() && hourIndex < timetable.getColumns().size()) {
//    //         //     SchoolDay newClassInfo = new SchoolDay(classCode, classInfoFromHours, classInfoFromMinutes, classInfoToHours, classInfoToMinutes);
//    //         //     timetable.getItems().add(newClassInfo);
//    //         // }
//    //     }
//
//    @FXML
//    public void updateClassInfo(ActionEvent event) throws IOException {
//        SchoolDay newClass = new SchoolDay("Monday", "CMT221", "10:00", "11:30");
//
//// Add the new class to your data model (schoolDay list)
//        schoolDay.add(newClass);
//
//// Refresh the TableView
//        timetable.setItems(schoolDay);
//        timetable.refresh();
////        boolean checkClassInfoClassCode, checkClassInfoFromTo = false;
////        String classCode, classInfoFromHours, classInfoFromMinutes, classInfoToHours, classInfoToMinutes;
////        classInfoFromHours = tfClassInfoFromHours.getText();
////        classInfoFromMinutes = tfClassInfoFromMinutes.getText();
////        classInfoToHours = tfClassInfoToHours.getText();
////        classInfoToMinutes = tfClassInfoToMinutes.getText();
////        classCode = tfClassInfoCode.getText();
////
////        if (Integer.parseInt(classInfoFromHours) >= 0 && Integer.parseInt(classInfoFromHours) <= 24 && (Integer.parseInt(classInfoFromMinutes) == 0 || Integer.parseInt(classInfoFromMinutes) == 30) && Integer.parseInt(classInfoToHours) >= 0 && Integer.parseInt(classInfoToHours) <= 24 && (Integer.parseInt(classInfoToMinutes) == 0 || Integer.parseInt(classInfoToMinutes) == 30)) {
////            checkClassInfoFromTo = true;
////            lbClassInfoFromTo.setText("");
////        } else {
////            checkClassInfoFromTo = false;
////            lbClassInfoFromTo.setText("From & To Hours (00 - 24) and Minutes (00 or 30)");
////        }
////
////        classCode = tfClassInfoCode.getText();
////
////        if (classCode.length() == 6) {
////            checkClassInfoClassCode = true;
////            lbClassInfoCode.setText("");
////        } else {
////            checkClassInfoClassCode = false;
////            lbClassInfoCode.setText("Class Code must be 6 characters.");
////        }
////
////        int dayIndex = cbClassInfoActualDay.getSelectionModel().getSelectedIndex();
////        int columnIndex = -1;
////
////        if (checkClassInfoFromTo && checkClassInfoClassCode) {
////            lbClassInfoCode.setText("");
////
////            // Assuming a simple scenario where we add the values as Strings
////            String day = cbClassInfoActualDay.getValue();
////            String startTime = classInfoFromHours;
////            String endTime = classInfoToHours;
////
////            // Create a new SchoolDay object
////            SchoolDay newClassInfo = new SchoolDay();
////            newClassInfo.setClassCode(classCode);
////            newClassInfo.setDay(day);
////            newClassInfo.setFromHours(startTime);
////            newClassInfo.setToHours(endTime);
////
////            int rowToUpdate = dayIndex;  // Assuming dayIndex represents the row to update
////
////            // Ensure the row index is within the range
////            if (rowToUpdate >= 0 && rowToUpdate < timetable.getItems().size()) {
////                // Update the SchoolDay object in the TableView
////                timetable.getItems().set(rowToUpdate, newClassInfo);
////
////                // Refresh the table view
////                timetable.refresh();
////            }
////
////            // Clear the text fields and combo box after updating the table
////            tfClassInfoCode.clear();
////            cbClassInfoActualDay.getSelectionModel().clearSelection();
////        }
//    }
//}
