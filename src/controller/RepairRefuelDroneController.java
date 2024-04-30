package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RepairRefuelDroneController {
    @FXML private TextField tfDroneStore;
    @FXML private TextField tfDroneTag;
    @FXML private TextField tfRefueledTrips;
    @FXML private Button btnRefuel;
    @FXML private Button btnCancel;

    @FXML
    private void handleRefuel() {
        String droneStore = tfDroneStore.getText();
        String droneTag = tfDroneTag.getText();
        int refueledTrips;

        try {
            refueledTrips = Integer.parseInt(tfRefueledTrips.getText());
            if (refueledTrips < 0) {
                showAlert("Input Error", "Refueled trips cannot be negative.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid integer for refueled trips.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call repair_refuel_drone(?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, droneStore);
                cstmt.setInt(2, Integer.parseInt(droneTag));
                cstmt.setInt(3, refueledTrips);

                int affectedRows = cstmt.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Success", "Drone refueled successfully at store: " + droneStore, AlertType.INFORMATION);
                } else {
                    showAlert("Error", "No changes were made. Check drone details.", AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An SQL error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel() {
        switchTo("Drone_Related_Tasks.fxml");
    }

    private void switchTo(String fxmlFile) {
        try {
            Main.switchToView("/fxml/" + fxmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
