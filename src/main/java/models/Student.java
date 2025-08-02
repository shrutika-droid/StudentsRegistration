package models;


import java.util.ArrayList;
import java.util.List;

public class Student {
    private String email;
    private String password;
    private String name;
    private List<Course> registeredCourses;
    private List<Course> completedCourses;

    public Student(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public List<Course> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(List<Course> completedCourses) {
        this.completedCourses = completedCourses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
        }
    }

    public void unregisterCourse(Course course) {
        registeredCourses.remove(course);
    }

    public void completeCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            if (!completedCourses.contains(course)) {
                completedCourses.add(course);
            }
        }
    }
}
