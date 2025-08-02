package controllers;

import com.example.studentregistration.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    protected void onGetStartedClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 500, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());

            // Correct way to get the stage from the ActionEvent
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Student Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

