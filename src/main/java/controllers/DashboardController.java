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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label welcomeLabel;

    @FXML
    private VBox registeredCoursesBox;

    @FXML
    private Label totalCoursesLabel;

    @FXML
    private Label registeredCoursesLabel;

    @FXML
    private Label availableCoursesLabel;

    @FXML
    private Label coursesCompletedLabel;

    @FXML
    private Label coursesInProgressLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent != null) {
            welcomeLabel.setText("Welcome back, " + currentStudent.getName() + "!");
            loadDashboardData(currentStudent);
        }
    }

    private void loadDashboardData(Student student) {
        // Clear existing course boxes
        registeredCoursesBox.getChildren().clear();

        // Add registered courses
        for (Course course : student.getRegisteredCourses()) {
            VBox courseBox = createCourseBox(course);
            registeredCoursesBox.getChildren().add(courseBox);
        }

        // Update statistics
        int totalCourses = DataManager.getInstance().getAvailableCourses().size() + student.getRegisteredCourses().size();
        int registeredCourses = student.getRegisteredCourses().size();
        int availableCourses = DataManager.getInstance().getAvailableCourses().size();
        int completedCourses = student.getCompletedCourses().size();
        int inProgressCourses = registeredCourses;

        totalCoursesLabel.setText(String.valueOf(totalCourses));
        registeredCoursesLabel.setText(String.valueOf(registeredCourses));
        availableCoursesLabel.setText(String.valueOf(availableCourses));
        coursesCompletedLabel.setText(String.valueOf(completedCourses));
        coursesInProgressLabel.setText(String.valueOf(inProgressCourses));
    }

    private VBox createCourseBox(Course course) {
        VBox courseBox = new VBox();
        courseBox.getStyleClass().add("course-card");

        Label courseNameLabel = new Label(course.getCourseName());
        courseNameLabel.getStyleClass().add("course-name");

        Label courseCodeLabel = new Label(course.getCourseCode());
        courseCodeLabel.getStyleClass().add("course-code");

        Label professorLabel = new Label("Professor: " + course.getProfessor());
        professorLabel.getStyleClass().add("professor-name");

        courseBox.getChildren().addAll(courseNameLabel, courseCodeLabel, professorLabel);

        return courseBox;
    }

    @FXML
    protected void onProfileClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/profile.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRegisterCourseClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/courseRegistration.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onMyCoursesClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/viewCourses.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogoutClick() {
        DataManager.getInstance().setCurrentStudent(null);
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/studentregistration/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 500, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/studentregistration/css/style.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Student Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}