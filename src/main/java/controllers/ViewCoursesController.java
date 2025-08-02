package controllers;

import com.example.studentregistration.HelloApplication;
import models.Course;
import models.Student;
import utils.DataManager;
//import com.example.studentregistration.models.Course;
//import com.example.studentregistration.models.Student;
//import com.example.studentregistration.utils.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCoursesController implements Initializable {
    @FXML
    private TableView<Course> registeredCoursesTable;

    @FXML
    private TableColumn<Course, String> courseCodeCol;

    @FXML
    private TableColumn<Course, String> courseNameCol;

    @FXML
    private TableColumn<Course, Integer> creditsCol;

    @FXML
    private TableColumn<Course, String> professorCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadRegisteredCourses();
    }

    private void setupTableColumns() {
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));
        professorCol.setCellValueFactory(new PropertyValueFactory<>("professor"));
    }

    private void loadRegisteredCourses() {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent != null) {
            registeredCoursesTable.getItems().clear();
            registeredCoursesTable.getItems().addAll(currentStudent.getRegisteredCourses());
        }
    }

    @FXML
    protected void onBackToDashboardClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) registeredCoursesTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}