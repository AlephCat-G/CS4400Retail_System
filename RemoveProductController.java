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

public class RemoveProductController {

    @FXML private TextField tfBarcode;
    @FXML private Button btnRemove;
    @FXML private Button btnCancel;

    @FXML
    private void handleRemoveProduct() {
        String barcode = tfBarcode.getText().trim();

        if (barcode.isEmpty()) {
            showAlert("Input Error", "Barcode cannot be empty.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call remove_product(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, barcode);
                int affectedRows = cstmt.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Success", "Product removed successfully.", AlertType.INFORMATION);
                } else {
                    showAlert("Error", "No product found or removed.", AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
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
