package com.module4;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainController {
    @FXML
    private Button btSmart;

    @FXML
    private Button btManual;

    @FXML
    public void switchToManualPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manualPage.fxml"));
        Parent newView = loader.load();
        Scene scene = new Scene(newView);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}