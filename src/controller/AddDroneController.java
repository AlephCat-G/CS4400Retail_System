package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class AddDroneController {

    @FXML private TextField tfStoreID;
    @FXML private TextField tfDroneTag;
    @FXML private TextField tfCapacity;
    @FXML private TextField tfRemainingTrips;
    @FXML private TextField tfPilot;

    @FXML
    private void handleAddDrone() {
        String storeID = tfStoreID.getText();
        String droneTag = tfDroneTag.getText();
        int capacity = Integer.parseInt(tfCapacity.getText());
        int remainingTrips = Integer.parseInt(tfRemainingTrips.getText());
        String pilot = tfPilot.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call add_drone(?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, storeID);
                cstmt.setString(2, droneTag);
                cstmt.setInt(3, capacity);
                cstmt.setInt(4, remainingTrips);
                cstmt.setString(5, pilot);

                int affectedRows = cstmt.executeUpdate();
                if (affectedRows == 0) {
                    showAlert("Error", "No drone was added. Possible duplicate entry or other constraints violation.", AlertType.ERROR);
                } else {
                    showAlert("Success", "Drone added successfully.", AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An unexpected SQL error has occurred.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Drone_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
