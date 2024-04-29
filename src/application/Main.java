package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage; // Keep a reference to the primary stage

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        switchToView("/fxml/MainDashboard.fxml");
        primaryStage.setTitle("Retail System");
        primaryStage.show();
    }

    public static void switchToView(String fxmlFile) throws Exception {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the FXML file: " + fxmlFile);
            // Add additional error handling as needed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
