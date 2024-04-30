package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AddCustomerController {
    @FXML
    private Button customersButton, productsButton, backButton;
    @FXML
    private TextField tfUsername, tfFirstName, tfLastName, tfAddress, tfRating, tfCredit;
    @FXML
    private DatePicker dpBirthday;

    @FXML
    private void handleBackAction() {
        switchTo("Customer_Related_Tasks.fxml");
    }

    @FXML
    private void handleAddCustomer() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        String birthdate = (dpBirthday.getValue() != null) ? dpBirthday.getValue().toString() : null;
        int rating = 0;
        int credit = 0;

        try {
            rating = Integer.parseInt(tfRating.getText());
            credit = Integer.parseInt(tfCredit.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Rating or credit input is invalid. Please enter valid integers.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call add_customer(?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.setString(2, firstName);
                cstmt.setString(3, lastName);
                cstmt.setString(4, address);
                if (birthdate != null) {
                    cstmt.setDate(5, java.sql.Date.valueOf(birthdate));
                } else {
                    cstmt.setNull(5, java.sql.Types.DATE);
                }
                cstmt.setInt(6, rating);
                cstmt.setInt(7, credit);

                int affectedRows = cstmt.executeUpdate();
                if (affectedRows == 0) {
                    showAlert("Error", "No customer was added. Possible duplicate entry or other constraints violation.", AlertType.ERROR);
                } else {
                    showAlert("Success", "Customer added successfully.", AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            showAlert("Database Error", "An unexpected SQL error has occurred.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel() {
        switchTo("Customer_Related_Tasks.fxml");
    }

    private void switchTo(String fxmlFile) {
        try {
            Main.switchToView("/fxml/" + fxmlFile);
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
}
