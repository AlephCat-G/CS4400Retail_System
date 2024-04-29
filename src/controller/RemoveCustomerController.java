package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RemoveCustomerController {

    @FXML private TextField tfUsername;
    @FXML private Button btnRemove;
    @FXML private Button btnCancel;

    @FXML
    private void handleRemoveCustomer() {
        String username = tfUsername.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call remove_customer(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.executeUpdate();
                System.out.println("Customer removed successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
