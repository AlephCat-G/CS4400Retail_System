package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RemoveProductController {

    @FXML private TextField tfBarcode;
    @FXML private Button btnCancel;
    @FXML private Button btnRemove;

    @FXML
    private void handleRemoveProduct() {
        String barcode = tfBarcode.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call remove_product(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, barcode);
                cstmt.executeUpdate();
                System.out.println("Product removed successfully");
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
