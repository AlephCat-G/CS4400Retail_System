package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddOrderLineController {

    @FXML private TextField tfOrderID;
    @FXML private TextField tfProductBarcode;
    @FXML private TextField tfProductPrice;
    @FXML private TextField tfProductQuantity;
    @FXML private Button btnAdd;
    @FXML private Button btnCancel;

    @FXML
    private void handleAddOrderLine() {
        String orderID = tfOrderID.getText().trim();
        String productBarcode = tfProductBarcode.getText().trim();

        if (orderID.isEmpty() || productBarcode.isEmpty()) {
            showAlert("Input Error", "Order ID and Product Barcode cannot be empty.", AlertType.ERROR);
            return;
        }

        try {
            double productPrice = Double.parseDouble(tfProductPrice.getText().trim());
            int productQuantity = Integer.parseInt(tfProductQuantity.getText().trim());

            if (productPrice < 0 || productQuantity <= 0) {
                showAlert("Input Error", "Product Price and Quantity must be positive numbers.", AlertType.ERROR);
                return;
            }

            String validationResult = canAddOrderLine(orderID, productBarcode, productPrice, productQuantity);
            if (!validationResult.equals("OK")) {
                showAlert("Error", validationResult, AlertType.ERROR);
                return;
            }

            try (Connection conn = DatabaseConnector.getConnection()) {
                String call = "{call add_order_line(?, ?, ?, ?)}";
                try (CallableStatement cstmt = conn.prepareCall(call)) {
                    cstmt.setString(1, orderID);
                    cstmt.setString(2, productBarcode);
                    cstmt.setDouble(3, productPrice);
                    cstmt.setInt(4, productQuantity);
                    int affectedRows = cstmt.executeUpdate();
                    if (affectedRows > 0) {
                        showAlert("Success", "Order line added successfully.", AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "No order line was added. Check data integrity constraints.", AlertType.ERROR);
                    }
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Product Price and Quantity must be valid numbers.", AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An SQL error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private String canAddOrderLine(String orderID, String productBarcode, double productPrice, int quantity) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String productCheck = "SELECT weight FROM products WHERE barcode = ?";
            String orderCheck = "SELECT d.capacity, (SELECT IFNULL(SUM(ol.quantity * p.weight), 0) FROM order_lines ol JOIN products p ON ol.barcode = p.barcode WHERE ol.orderID = ?) + (? * ?) as total_weight FROM orders o JOIN drones d ON o.carrier_tag = d.droneTag WHERE o.orderID = ?";
            try (PreparedStatement productStmt = conn.prepareStatement(productCheck);
                 PreparedStatement orderStmt = conn.prepareStatement(orderCheck)) {

                productStmt.setString(1, productBarcode);
                ResultSet productRs = productStmt.executeQuery();
                if (!productRs.next()) {
                    return "Product does not exist.";
                }
                int productWeight = productRs.getInt("weight");

                orderStmt.setString(1, orderID);
                orderStmt.setInt(2, productWeight);
                orderStmt.setInt(3, quantity);
                orderStmt.setString(4, orderID);
                ResultSet orderRs = orderStmt.executeQuery();
                if (orderRs.next()) {
                    int capacity = orderRs.getInt("capacity");
                    int totalWeight = orderRs.getInt("total_weight");
                    if (totalWeight > capacity) {
                        return "Cannot add order line due to exceeding drone capacity.";
                    }
                } else {
                    return "Order ID not found or no drones assigned.";
                }
            }
            return "OK";
        }
    }

    @FXML
    private void handleCancel() {
        switchTo("Orders_Related_Tasks.fxml");
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
