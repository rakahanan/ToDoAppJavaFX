package app;

import app.data.SQLiteHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SQLiteHelper.initialize();

        Parent root = FXMLLoader.load(getClass().getResource("/app/view/dashboard.fxml"));
        primaryStage.setTitle("To Do Note");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnHiding(windowEvent -> SQLiteHelper.closeConnection());

    }
}
