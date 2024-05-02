package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SwapDroneControlController {

    @FXML private TextField tfIncomingPilot;
    @FXML private TextField tfOutgoingPilot;
    @FXML private Button btnCancel;
    @FXML private Button btnConfirm;

    @FXML
    private void handleConfirm() {
        String incomingPilot = tfIncomingPilot.getText();
        String outgoingPilot = tfOutgoingPilot.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            // Pre-checks before attempting the swap
            if (!pilotExists(conn, incomingPilot)) {
                showAlert("Error", "Invalid incoming pilot username.", Alert.AlertType.ERROR);
                return;
            }
            if (!pilotExists(conn, outgoingPilot)) {
                showAlert("Error", "Invalid outgoing pilot username.", Alert.AlertType.ERROR);
                return;
            }
            if (isPilotAssignedToDrone(conn, incomingPilot)) {
                showAlert("Error", "Incoming pilot is already assigned to a drone.", Alert.AlertType.ERROR);
                return;
            }
            if (!isPilotAssignedToDrone(conn, outgoingPilot)) {
                showAlert("Error", "Outgoing pilot is not assigned to any drone.", Alert.AlertType.ERROR);
                return;
            }

            // If all checks pass, attempt the swap
            String call = "{call swap_drone_control(?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, incomingPilot);
                cstmt.setString(2, outgoingPilot);
                int rowsAffected = cstmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Drone control swapped successfully from " + outgoingPilot + " to " + incomingPilot, Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "No action taken. Please try again.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An unexpected SQL error has occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean pilotExists(Connection conn, String pilot) throws SQLException {
        String query = "SELECT 1 FROM drone_pilots WHERE uname = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pilot);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private boolean isPilotAssignedToDrone(Connection conn, String pilot) throws SQLException {
        String query = "SELECT 1 FROM drones WHERE pilot = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pilot);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
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
