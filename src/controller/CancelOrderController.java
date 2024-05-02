package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.sql.*;

public class CancelOrderController {

    @FXML private TextField tfOrderID;
    @FXML private Button btnCancel;
    @FXML private Button btnRemoveOrder;

    @FXML
    private void handleRemoveOrder() {
        String orderID = tfOrderID.getText().trim();

        if (orderID.isEmpty()) {
            showAlert("Input Error", "Order ID cannot be empty.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            if (!orderExists(orderID, conn)) {
                showAlert("Error", "Order ID does not exist.", AlertType.ERROR);
                return;
            }

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel this order?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait();

            if (confirmAlert.getResult() == ButtonType.YES) {
                String call = "{call cancel_order(?)}";
                try (CallableStatement cstmt = conn.prepareCall(call)) {
                    cstmt.setString(1, orderID);
                    int affectedRows = cstmt.executeUpdate();
                    if (affectedRows > 0) {
                        showAlert("Success", "Order cancelled successfully.", AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "Failed to cancel the order. No rows were affected.", AlertType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private boolean orderExists(String orderID, Connection conn) throws SQLException {
        String query = "SELECT count(*) FROM orders WHERE orderID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Orders_Related_Tasks.fxml");
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
        showAlert("Database Error", "An SQL error occurred: " + e.getSQLState() + " - " + e.getMessage(), AlertType.ERROR);
    }
}
