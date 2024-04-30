package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class IncreaseCreditController {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfAmount;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnIncrease;

    @FXML
    private void handleIncreaseCredit() {
        String username = tfUsername.getText();
        int amount;

        try {
            amount = Integer.parseInt(tfAmount.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid integer amount.", Alert.AlertType.ERROR);
            return;
        }

        if (amount < 0) {
            showAlert("Input Error", "Credit increase cannot be negative.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call increase_customer_credits(?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.setInt(2, amount);
                int rowsAffected = cstmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Credit increased successfully for user: " + username, Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Notice", "No credits were added. User not found or zero amount specified.", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        tfUsername.clear();
        tfAmount.clear();
        try {
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
