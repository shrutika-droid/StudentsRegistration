module com.example.studentregistration {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.studentregistration to javafx.fxml;
    opens controllers to javafx.fxml; // This is the new, crucial line

    exports com.example.studentregistration;
    exports controllers;
    exports models;
    exports utils;
}
