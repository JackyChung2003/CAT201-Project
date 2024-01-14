package com.group_22.timetablemanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomListViewBackground extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a sample ListView
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Item 1", "Item 2", "Item 3");

        // Set a cell factory to customize the appearance of the list cells
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        // Set a custom background color for the list cells
                        if (item == null || empty) {
                            setBackground(null);
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                        }

                        // Set the text of the list cell
                        setText(item);
                    }
                };
            }
        });

        // Create a Scene and set it to the stage
        Scene scene = new Scene(listView, 300, 200);
        primaryStage.setScene(scene);

        // Set the stage title and show it
        primaryStage.setTitle("Custom ListView Background");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
