package controllers;

import com.example.studentregistration.HelloApplication;
import models.Student;
import utils.DataManager;
//import com.example.studentregistration.models.Student;
//import com.example.studentregistration.utils.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        Student student = DataManager.getInstance().authenticateStudent(email, password);
        if (student != null) {
            try {
                // Load the dashboard FXML using an absolute path
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/dashboard.fxml"));
                Scene scene = new Scene(loader.load(), 1000, 700);

                // Set the stylesheet for the new scene
                scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Student Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load dashboard.");
            }
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    @FXML
    protected void onForgotPasswordClick() {
        showAlert("Forgot Password", "Please contact administrator to reset your password.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

//test