package controllers;

import com.example.studentregistration.HelloApplication;
import models.Student;
import utils.DataManager;
//import com.example.studentregistration.models.Student;
//import com.example.studentregistration.utils.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label registeredCoursesCountLabel;

    @FXML
    private Label completedCoursesCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProfileData();
    }

    private void loadProfileData() {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent != null) {
            nameLabel.setText(currentStudent.getName());
            nameField.setText(currentStudent.getName());
            emailField.setText(currentStudent.getEmail());
            registeredCoursesCountLabel.setText(String.valueOf(currentStudent.getRegisteredCourses().size()));
            completedCoursesCountLabel.setText(String.valueOf(currentStudent.getCompletedCourses().size()));
        }
    }

    @FXML
    protected void onUpdateProfileClick() {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent != null) {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                showAlert("Error", "Name and email cannot be empty.");
                return;
            }

            currentStudent.setName(newName);
            currentStudent.setEmail(newEmail);

            nameLabel.setText(newName);
            showAlert("Success", "Profile updated successfully!");
        }
    }

    @FXML
    protected void onBackToDashboardClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




