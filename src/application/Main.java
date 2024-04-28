package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage; // Keep a reference to the primary stage

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage; // Initialize the stage reference
        switchToView("/fxml/AddCustomerView.fxml");
        primaryStage.setTitle("Retail System");
        primaryStage.show();
    }

    // Utility method to switch views
    public static void switchToView(String fxmlFile) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
        primaryStage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Now you can call Main.switchToView("/fxml/DroneDispatchView.fxml"); from anywhere in your application to switch views.
