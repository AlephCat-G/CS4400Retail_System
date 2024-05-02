package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveDroneController {

    @FXML private TextField tfStoreID;
    @FXML private TextField tfDroneTag;
    @FXML private Button btnCancel;
    @FXML private Button btnRemove;

    @FXML
    private void handleRemoveDrone() {
        String storeID = tfStoreID.getText().trim();
        String droneTag = tfDroneTag.getText().trim();

        if (storeID.isEmpty() || droneTag.isEmpty()) {
            showAlert("Input Error", "Store ID and Drone Tag cannot be empty.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            if (isDroneCurrentlyInUse(storeID, Integer.parseInt(droneTag), conn)) {
                showAlert("Removal Error", "Cannot remove drone currently assigned to an order.", AlertType.ERROR);
                return;
            }

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to remove this drone?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait();

            if (confirmAlert.getResult() == ButtonType.YES) {
                String call = "{call remove_drone(?, ?)}";
                try (CallableStatement cstmt = conn.prepareCall(call)) {
                    cstmt.setString(1, storeID);
                    cstmt.setInt(2, Integer.parseInt(droneTag));
                    int affectedRows = cstmt.executeUpdate();
                    if (affectedRows > 0) {
                        showAlert("Success", "Drone removed successfully from store: " + storeID, AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "No drone found or removed.", AlertType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private boolean isDroneCurrentlyInUse(String storeID, int droneTag, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM orders WHERE carrier_tag = ? AND carrier_store = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, droneTag);
            pstmt.setString(2, storeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt("count") > 0;
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

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleSQLException(SQLException e) {
        showAlert("Database Error", "SQL Error: " + e.getSQLState() + " - " + e.getMessage(), AlertType.ERROR);
    }
}
