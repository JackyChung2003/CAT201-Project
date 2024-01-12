package com.group_22.timetablemanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Random;

public class HomeController {

    @FXML
    private Button DashboardBtn;

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
    private Stage loginStage;
    private Scene loginScene;

    private Stage stage;

    private Scene scene;

    private Parent root;
    // Store the active button
    private Button activeButton;

    // Variable to hold user's full name
    private String fullName;
    FXMLLoader loader = null;

    @FXML
    void DashboardBtnOnClicked(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Pass any necessary data to the HomeController
        HomeController homeController = loader.getController();
        homeController.initData(fullName);
    }

    @FXML
    void CustomizeTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("CustomizePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void EditTimeTableBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("EditTimeTable.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ModifyDueDatesBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("ModifyDueDates.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void EditPersonalBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("EditPersonalInfo.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    void SignOutBtnOnClicked(ActionEvent event) {
//        FXMLLoader loader = null;
//        try {
//            if (loginStage == null) {
//                // If home stage is not created yet, create it
//                loader = new FXMLLoader(getClass().getResource("Sign-In.fxml"));
//                root = loader.load();
//                loginScene = new Scene(root);
//                loginStage = new Stage();
//                loginStage.setScene(loginScene);
//            }
//            // Show the home stage
//            loginStage.show();
//
//            // Pass any necessary data to the HomeController
////        HomeController homeController = loader.getController();
////        homeController.initData(fullName);  // You can create a method like initData to pass data
//        } catch (Exception  e){
//            e.printStackTrace();
//        }
//
//    }

    @FXML
    void SignOutBtnOnClicked(ActionEvent event) throws IOException {
        // Load the new FXML file
        root = FXMLLoader.load(getClass().getResource("Sign-In.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void initData(String fullName) {
//        this.fullName = fullName;
//        WelcomeUserLabel.setText("Welcome back, " + fullName + "!");
        if (fullName != null) {
            WelcomeUserLabel.setText("Welcome back, " + fullName + "!");
        } else {
            // Display something else on subsequent visits
            String[] motivationalQuotes = {
                    "Make today amazing!",
                    "Embrace the challenge, conquer the day.",
                    "Small steps lead to big victories.",
                    "Every moment is a fresh beginning.",
                    "Your dedication today defines your success tomorrow.",
                    "Dream big, work hard, stay focused.",
                    "Seeds of discipline blossom into fields of achievement.",
                    "Believe in your hustle; miracles happen when you do.",
                    "Effort today, triumph tomorrow.",
                    "Success is a journey not a destination.",
                    "It always seems impossible until it's done.",
                    "Success is liking yourself, liking what you do, and liking how you do it.",
                    "Aim for the moon. If you miss, you may hit a star.",
                    "If you cannot do great things, do small things in a great way.",
                    "Success only comes to those who dare to attempt.",
                    "I never dreamed about success. I worked for it.",
                    "If opportunity doesn't knock, build a door.",
                    "You’ve got to learn to leave the table when love’s no longer being served.",
                    "Talk to yourself like someone you love.",
                    "Love yourself first and everything else falls into place.",
                    "If you prioritize yourself, you are going to save yourself.",
                    "It takes courage to grow up and become who you really are.",
                    "You are your best thing.",
                    "You are enough just as you are.",
                    "The most powerful relationship you will ever have is the relationship with yourself.",
                    "The only person you should try to be better than is the person you were yesterday.",
                    "You yourself, as much as anybody in the entire universe, deserve your love and affection.",
                    "To improve is to change; to be perfect is to change often.",
                    "Everyone thinks of changing the world, but no one thinks of changing himself.",
                    "Life isn't about finding yourself. Life is about creating yourself.",
                    "You are never too old to set another goal or to dream a new dream.",
                    "Rise above the storm and you will find the sunshine.",
                    "Vitality shows not only in the ability to persist but in the ability to start over.",
                    "It is never too late to be what you might have been.",
                    "You must be the change you wish to see in the world.",
                    "Mistakes are a fact of life. It is the response to the errors that counts.",
                    "The only person you are destined to become is the person you decide to be.",
                    "The best way out is always through.",
                    "You can't cross the sea merely by standing and staring at the water.",
                    "With the new day comes new strength and new thoughts.",
                    "What we fear of doing most is usually what we most need to do.",
                    "Success is not final, failure is not fatal: it is the courage to continue that counts."
            };




            // Display a random motivational quote
            Random random = new Random();
            String randomQuote = motivationalQuotes[random.nextInt(motivationalQuotes.length)];
            WelcomeUserLabel.setText(randomQuote);
        }
    }
}
