package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.*;

public class CancelOrderController {

    @FXML private TextField tfOrderID;
    @FXML private Button btnCancel;
    @FXML private Button btnRemoveOrder;

    @FXML
    private void handleRemoveOrder() {
        String orderID = tfOrderID.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call cancel_order(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, orderID);
                cstmt.executeUpdate();
                System.out.println("Order cancelled successfully");
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
