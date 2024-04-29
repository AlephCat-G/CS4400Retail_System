package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class BeginOrderController {

    @FXML private TextField tfOrderID;
    @FXML private DatePicker dpSoldOn;
    @FXML private TextField tfPurchasedBy;
    @FXML private TextField tfCarrierStore;
    @FXML private TextField tfCarrierTag;
    @FXML private TextField tfProductBarcode;
    @FXML private TextField tfProductPrice;
    @FXML private TextField tfProductQuantity;

    @FXML
    private void handleBeginOrder() {
        String orderID = tfOrderID.getText();
        String soldOn = dpSoldOn.getValue() != null ? dpSoldOn.getValue().toString() : null;
        String purchasedBy = tfPurchasedBy.getText();
        String carrierStore = tfCarrierStore.getText();
        String carrierTag = tfCarrierTag.getText();
        String productBarcode = tfProductBarcode.getText();
        double productPrice = Double.parseDouble(tfProductPrice.getText());
        int productQuantity = Integer.parseInt(tfProductQuantity.getText());

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call begin_order(?, ?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, orderID);
                if (soldOn != null) {
                    cstmt.setDate(2, java.sql.Date.valueOf(soldOn));
                } else {
                    cstmt.setNull(2, java.sql.Types.DATE);
                }
                cstmt.setString(3, purchasedBy);
                cstmt.setString(4, carrierStore);
                cstmt.setString(5, carrierTag);
                cstmt.setString(6, productBarcode);
                cstmt.setDouble(7, productPrice);
                cstmt.setInt(8, productQuantity);
                cstmt.executeUpdate();
                System.out.println("Order started successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        try {
            // This will switch the view back to the previous dashboard or view
            Main.switchToView("/fxml/Orders_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Here you can log the error or show an error message to the user
        }
    }
}

