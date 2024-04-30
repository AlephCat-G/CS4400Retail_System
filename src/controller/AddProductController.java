package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class AddProductController {

    @FXML private TextField tfBarcode;
    @FXML private TextField tfName;
    @FXML private TextField tfWeight;

    @FXML
    private void handleAddProduct() {
        String barcode = tfBarcode.getText();
        String name = tfName.getText();
        double weight = 0;

        try {
            weight = Double.parseDouble(tfWeight.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Weight input is invalid. Please enter a valid number.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            // First check if the barcode already exists
            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT count(*) FROM products WHERE barcode = ?")) {
                checkStmt.setString(1, barcode);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert("Error", "Operation not completed: Barcode already exists.", Alert.AlertType.ERROR);
                    return; // Stop execution if barcode already exists
                }
            }

            // If barcode does not exist, proceed to add the product
            String call = "{call add_product(?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, barcode);
                cstmt.setString(2, name);
                cstmt.setDouble(3, weight);
                cstmt.executeUpdate();
                showAlert("Success", "Product added successfully.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An unexpected SQL error has occurred.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Product_Related_Tasks.fxml");
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
