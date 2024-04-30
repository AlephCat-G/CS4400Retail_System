package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;

public class BeginOrderController {

    @FXML private TextField tfOrderID;
    @FXML private DatePicker dpSoldOn;
    @FXML private TextField tfPurchasedBy;
    @FXML private TextField tfCarrierStore;
    @FXML private TextField tfCarrierTag;
    @FXML private TextField tfProductBarcode;
    @FXML private TextField tfProductPrice;
    @FXML private TextField tfProductQuantity;
    @FXML private Button btnStartOrder;
    @FXML private Button btnCancel;

    @FXML
    private void handleBeginOrder() {
        String orderID = tfOrderID.getText().trim();
        String soldOn = dpSoldOn.getValue() != null ? dpSoldOn.getValue().toString() : null;
        String purchasedBy = tfPurchasedBy.getText().trim();
        String carrierStore = tfCarrierStore.getText().trim();
        int carrierTag = 0;
        String productBarcode = tfProductBarcode.getText().trim();
        double productPrice = 0.0;
        int productQuantity = 0;

        try {
            carrierTag = Integer.parseInt(tfCarrierTag.getText().trim());
            productPrice = Double.parseDouble(tfProductPrice.getText().trim());
            productQuantity = Integer.parseInt(tfProductQuantity.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Carrier tag, price, or quantity must be valid numbers.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call begin_order(?, ?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, orderID);
                if (soldOn != null) {
                    cstmt.setDate(2, java.sql.Date.valueOf(soldOn));
                } else {
                    cstmt.setNull(2, Types.DATE);
                }
                cstmt.setString(3, purchasedBy);
                cstmt.setString(4, carrierStore);
                cstmt.setInt(5, carrierTag);
                cstmt.setString(6, productBarcode);
                cstmt.setDouble(7, productPrice);
                cstmt.setInt(8, productQuantity);

                cstmt.executeUpdate();
                showAlert("Success", "Order started successfully", AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to start order: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
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
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
