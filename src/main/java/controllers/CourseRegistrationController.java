// src/main/java/controllers/CourseRegistrationController.java

package controllers;

import models.Course;
import models.Student;
import utils.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CourseRegistrationController extends ViewController {

    @FXML
    private TableView<Course> availableCoursesTable;

    @FXML
    private TableView<Course> registeredCoursesTable;

    @FXML
    private TableColumn<Course, String> availableCourseCodeCol;

    @FXML
    private TableColumn<Course, String> availableCourseNameCol;

    @FXML
    private TableColumn<Course, Integer> availableCreditsCol;

    @FXML
    private TableColumn<Course, String> availableProfessorCol;

    @FXML
    private TableColumn<Course, String> registeredCourseCodeCol;

    @FXML
    private TableColumn<Course, String> registeredCourseNameCol;

    @FXML
    private TableColumn<Course, Integer> registeredCreditsCol;

    @FXML
    private TableColumn<Course, String> registeredProfessorCol;

    private ObservableList<Course> availableCourses;
    private ObservableList<Course> registeredCourses;

    @FXML
    public void initialize() {
        // Setup cell value factories for available courses table
        availableCourseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        availableCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        availableCreditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));
        availableProfessorCol.setCellValueFactory(new PropertyValueFactory<>("professor"));

        // Setup cell value factories for registered courses table
        registeredCourseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        registeredCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        registeredCreditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));
        registeredProfessorCol.setCellValueFactory(new PropertyValueFactory<>("professor"));
    }

    @Override
    public void initData(Student student) {
        super.initData(student);
        loadCourseData();
    }

    private void loadCourseData() {
        // Use the singleton instance of DataManager
        List<Course> allAvailable = DataManager.getInstance().getAvailableCourses();
        List<Course> alreadyRegistered = currentStudent.getRegisteredCourses();

        List<Course> notRegistered = allAvailable.stream()
                .filter(course -> !alreadyRegistered.contains(course))
                .collect(Collectors.toList());

        availableCourses = FXCollections.observableArrayList(notRegistered);
        registeredCourses = FXCollections.observableArrayList(alreadyRegistered);

        availableCoursesTable.setItems(availableCourses);
        registeredCoursesTable.setItems(registeredCourses);
    }

    @FXML
    protected void onRegisterClick() {
        Course selectedCourse = availableCoursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to register.");
            return;
        }

        // Directly call the method on the Student object
        currentStudent.registerCourse(selectedCourse);

        availableCourses.remove(selectedCourse);
        registeredCourses.add(selectedCourse);

        showAlert("Success", "Successfully registered for " + selectedCourse.getCourseName());
    }

    @FXML
    protected void onRemoveClick() {
        Course selectedCourse = registeredCoursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to remove.");
            return;
        }

        // Directly call the method on the Student object
        currentStudent.unregisterCourse(selectedCourse);

        registeredCourses.remove(selectedCourse);
        availableCourses.add(selectedCourse);

        showAlert("Success", "Successfully removed " + selectedCourse.getCourseName());
    }

    @FXML
    protected void onBackToDashboardClick(ActionEvent event) {
        navigateTo(event, "fxml/dashboard.fxml", "Student Dashboard");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
