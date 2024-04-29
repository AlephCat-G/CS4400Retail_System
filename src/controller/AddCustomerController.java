package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;


public class AddCustomerController {
    public Button customersButton;
    public Button productsButton;
    @FXML
    private Button backButton;
    @FXML
    private void handleBackAction() {
        try {
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField tfUsername;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAddress;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField tfRating;
    @FXML private TextField tfCredit;

    @FXML
    private void handleAddCustomer() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        String birthdate = dpBirthday.getValue() != null ? dpBirthday.getValue().toString() : null;
        int rating = Integer.parseInt(tfRating.getText());
        int credit = Integer.parseInt(tfCredit.getText());

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
                cstmt.executeUpdate();
                System.out.println("Customer added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        try {
            // This will switch the view back to the MainDashboard.fxml view
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Here you can log the error or show an error message to the user
        }
    }
}
