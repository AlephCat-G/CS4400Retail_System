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

public class RemoveCustomerController {

    @FXML private TextField tfUsername;
    @FXML private Button btnRemove;
    @FXML private Button btnCancel;

    @FXML
    private void handleRemoveCustomer() {
        String username = tfUsername.getText().trim();

        if (username.isEmpty()) {
            showAlert("Input Error", "Username cannot be empty.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            if (hasActiveOrders(username, conn)) {
                showAlert("Removal Error", "Cannot remove customer with active orders.", AlertType.ERROR);
                return;
            }

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to remove this customer?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait();

            if (confirmAlert.getResult() == ButtonType.YES) {
                String call = "{call remove_customer(?)}";
                try (CallableStatement cstmt = conn.prepareCall(call)) {
                    cstmt.setString(1, username);
                    int affectedRows = cstmt.executeUpdate();
                    if (affectedRows > 0) {
                        showAlert("Success", "Customer removed successfully.", AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "No customer found or removed.", AlertType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private boolean hasActiveOrders(String username, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS order_count FROM orders WHERE purchased_by = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt("order_count") > 0;
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
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
