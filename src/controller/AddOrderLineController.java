package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class AddOrderLineController {

    @FXML private TextField tfOrderID;
    @FXML private TextField tfProductBarcode;
    @FXML private TextField tfProductPrice;
    @FXML private TextField tfProductQuantity;
    @FXML private Button btnAdd;
    @FXML private Button btnCancel;

    @FXML
    private void handleAddOrderLine() {
        String orderID = tfOrderID.getText();
        String productBarcode = tfProductBarcode.getText();
        double productPrice = Double.parseDouble(tfProductPrice.getText());
        int productQuantity = Integer.parseInt(tfProductQuantity.getText());

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call add_order_line(?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, orderID);
                cstmt.setString(2, productBarcode);
                cstmt.setDouble(3, productPrice);
                cstmt.setInt(4, productQuantity);
                cstmt.executeUpdate();
                System.out.println("Order line added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}

