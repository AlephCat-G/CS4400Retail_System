package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
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
        double weight = Double.parseDouble(tfWeight.getText());

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call add_product(?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, barcode);
                cstmt.setString(2, name);
                cstmt.setDouble(3, weight);
                cstmt.executeUpdate();
                System.out.println("Product added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}
