module com.example.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.taskmanagement.controller to javafx.fxml;

    exports com.example.taskmanagement;
}