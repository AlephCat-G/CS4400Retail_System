package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class DeliverOrderController {

    @FXML private TextField tfOrderID;
    @FXML private Button btnDeliver;
    @FXML private Button btnCancel;

    @FXML
    private void handleDeliverOrder() {
        String orderID = tfOrderID.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call deliver_order(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, orderID);
                cstmt.executeUpdate();
                System.out.println("Order delivered successfully");
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
            Main.switchToView("/fxml/Orders_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, possibly show an error message to the user
        }
    }
}
