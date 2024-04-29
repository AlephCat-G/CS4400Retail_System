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
        double productPrice = Double.parseDouble(tfProductPrice.getText());  // Assumes valid numeric input
        int productQuantity = Integer.parseInt(tfProductQuantity.getText()); // Assumes valid numeric input

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
            // Handle exception, potentially update GUI with error message
        }
    }

    @FXML
    private void handleCancel() {
        try {
            // Optionally switch the view back to another screen or clear the fields
            Main.switchToView("/fxml/Product_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, possibly show an error message to the user
        }
    }
}

