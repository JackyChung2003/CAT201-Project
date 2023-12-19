package com.group_22.timetablemanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class HomeController {
    @FXML
    private Button CustomizeTimeTableBtn;

    @FXML
    private ListView<?> DueDatesListView;

    @FXML
    private Button EditPersonalBtn;

    @FXML
    private Button EditTimeTableBtn;

    @FXML
    private Button ModifyDueDatesBtn;

    @FXML
    private Button SignOutBtn;

    @FXML
    private ListView<?> UpdateListView;

    @FXML
    private Label WelcomeUserLabel;

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) {

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

    public void initData(String fullName) {
        WelcomeUserLabel.setText("Welcome back, " + fullName + "!");
    }
}
