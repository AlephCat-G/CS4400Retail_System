package controller;

import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class AddDronePilotController {

    @FXML private TextField tfUsername;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAddress;
    @FXML private DatePicker dpBirthdate;
    @FXML private TextField tfTaxID;
    @FXML private TextField tfLicenseID;
    @FXML private TextField tfService;
    @FXML private TextField tfExperience;
    @FXML private TextField tfSalary;

    @FXML
    private void handleAddDronePilot() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        // Handle possible null value for dpBirthdate.getValue()
        String birthdate = dpBirthdate.getValue() != null ? dpBirthdate.getValue().toString() : null;
        String taxID = tfTaxID.getText();
        String licenseID = tfLicenseID.getText();
        int service = Integer.parseInt(tfService.getText());
        int experience = Integer.parseInt(tfExperience.getText());
        int salary = Integer.parseInt(tfSalary.getText());

        // Use try-with-resources to auto-close the connection
        try (Connection conn = DatabaseConnector.getConnection()) {
            // Call the stored procedure add_drone_pilot
            String call = "{call add_drone_pilot(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
                cstmt.setString(6, taxID);
                cstmt.setString(7, licenseID);
                cstmt.setInt(8, service);
                cstmt.setInt(9, experience);
                cstmt.setInt(10, salary);
                cstmt.executeUpdate();
                System.out.println("Drone Pilot added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, update GUI accordingly
        }
    }

    @FXML
    private void handleCancel() {
        // Clear all input fields, or close window, etc.
    }
}

