package utils;

import models.Course;
import models.Student;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Optional;

public class DataManager {
    // Singleton instance
    private static DataManager instance;

    private static final String STUDENTS_FILE_PATH = "C:\\Users\\Sushreet\\Documents\\AP\\Ap code\\StudentRegistration\\data\\student.txt";


    // Data lists
    private List<Student> students;
    private List<Course> availableCourses;
    private Student currentStudent;

    // Private constructor to enforce singleton pattern
    private DataManager() {
        students = new ArrayList<>();
        availableCourses = new ArrayList<>();
        // Initialize data from file on startup
        loadDataFromFiles();
    }

    // Public method to get the singleton instance
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    // Main method to orchestrate data loading
    private void loadDataFromFiles() {
        // Load courses first, as they are needed to register students
        loadAvailableCourses();
        loadStudentData();
    }

    // Method to load course data from the student.txt file
    private void loadAvailableCourses() {
        availableCourses.clear();
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE_PATH))) {
            // Find the "Available Courses" section
            boolean foundSection = false;
            while (scanner.hasNextLine() && !foundSection) {
                if (scanner.nextLine().trim().equals("Available Courses:")) {
                    foundSection = true;
                }
            }

            // Parse courses from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().startsWith("-")) {
                    try {
                        String[] parts = line.trim().replaceFirst("-", "").trim().split(":");
                        String courseCode = parts[0].trim();
                        String rest = parts[1].trim();

                        String courseName = rest.substring(0, rest.indexOf("(")).trim();
                        String creditsAndProf = rest.substring(rest.indexOf("(") + 1, rest.length() - 1);

                        String[] creditsAndProfParts = creditsAndProf.split("-");
                        int credits = Integer.parseInt(creditsAndProfParts[0].trim().replace("credits", "").trim());
                        String professor = creditsAndProfParts[1].trim();

                        availableCourses.add(new Course(courseCode, courseName, credits, professor));
                    } catch (Exception e) {
                        System.err.println("Error parsing course line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading student.txt from file system: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Method to load student data from the student.txt file
    private void loadStudentData() {
        students.clear();
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE_PATH))) {
            // Find the "Sample Student Data" section
            boolean foundSection = false;
            while (scanner.hasNextLine() && !foundSection) {
                if (scanner.nextLine().trim().equals("Sample Student Data:")) {
                    foundSection = true;
                }
            }

            // Parse student data from the file
            if (foundSection) {
                String email = null;
                String password = null;
                String name = null;
                List<String> registeredCourseNames = new ArrayList<>();

                String line;

                // Read student data line by line
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.trim().startsWith("- Email:")) {
                        email = line.split(":")[1].trim();
                    } else if (line.trim().startsWith("- Password:")) {
                        password = line.split(":")[1].trim();
                    } else if (line.trim().startsWith("- Name:")) {
                        name = line.split(":")[1].trim();
                    } else if (line.trim().startsWith("- Registered Courses:")) {
                        String registeredCoursesStr = line.split(":")[1].trim();
                        registeredCourseNames = Arrays.asList(registeredCoursesStr.split(","));
                    } else if (line.trim().isEmpty() || line.trim().equals("Available Courses:")) {
                        // Stop reading student data when a blank line or the next section is found
                        break;
                    }
                }

                // Create the student object only if all data is found
                if (email != null && password != null && name != null) {
                    Student newStudent = new Student(email, password, name);

                    // Register the courses
                    for (String courseName : registeredCourseNames) {
                        Optional<Course> courseToAdd = availableCourses.stream()
                                .filter(c -> c.getCourseName().equalsIgnoreCase(courseName.trim()))
                                .findFirst();
                        courseToAdd.ifPresent(newStudent::registerCourse);
                    }
                    students.add(newStudent);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading student.txt from file system: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to authenticate a student
    public Student authenticateStudent(String email, String password) {
        for (Student student : students) {
            // Use trim() to remove whitespace and equalsIgnoreCase() for robust comparison
            if (student.getEmail().trim().equalsIgnoreCase(email.trim()) && student.getPassword().trim().equals(password.trim())) {
                currentStudent = student;
                return student;
            }
        }
        return null;
    }

    // Getter for the current student
    public Student getCurrentStudent() {
        return currentStudent;
    }

    // Setter for the current student
    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

    // Getter for all available courses
    public List<Course> getAvailableCourses() {
        return availableCourses;
    }

    // Getter for all students
    public List<Student> getStudents() {
        return students;
    }

    // Method to add a new student
    public void addStudent(Student student) {
        students.add(student);
    }
}
