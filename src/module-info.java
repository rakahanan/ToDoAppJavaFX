module ToDoNoteApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires sqlite.jdbc;

    opens app;
    opens app.controller to javafx.fxml;
    opens app.customview to javafx.fxml;
}