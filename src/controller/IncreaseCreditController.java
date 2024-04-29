package controller;

import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class IncreaseCreditController {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfAmount;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnIncrease;

    @FXML
    private void handleIncreaseCredit() {
        String username = tfUsername.getText();
        int amount = Integer.parseInt(tfAmount.getText());

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call increase_customer_credit(?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.setInt(2, amount);
                cstmt.executeUpdate();
                System.out.println("Credit increased successfully for user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        tfUsername.clear();
        tfAmount.clear();
        System.out.println("Operation cancelled.");
    }
}

