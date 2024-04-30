package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDronePilotController {
    @FXML private TextField tfUsername, tfFirstName, tfLastName, tfAddress, tfBirthdate, tfTaxID, tfService, tfSalary, tfLicenseID, tfExperience;
    @FXML private Button btnCancel, btnAdd;

    @FXML
    private void handleAddDronePilot() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        Date birthdate;
        try {
            birthdate = Date.valueOf(tfBirthdate.getText());
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", "Birthdate is invalid. Please enter a valid date in the format YYYY-MM-DD.", Alert.AlertType.ERROR);
            return;  // Stop execution if birthdate is invalid
        }
        String taxID = tfTaxID.getText();
        int service, salary;
        try {
            service = Integer.parseInt(tfService.getText());
            salary = Integer.parseInt(tfSalary.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Service or salary input is invalid. Please enter valid integers.", Alert.AlertType.ERROR);
            return;  // Stop execution if service or salary are not valid integers
        }
        String licenseID = tfLicenseID.getText();
        int experience;
        try {
            experience = Integer.parseInt(tfExperience.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Experience input is invalid. Please enter a valid integer.", Alert.AlertType.ERROR);
            return;  // Stop execution if experience is not a valid integer
        }

        // Perform database operations
        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "CALL add_drone_pilot(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(call)) {
                stmt.setString(1, username);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, address);
                stmt.setDate(5, birthdate);
                stmt.setString(6, taxID);
                stmt.setInt(7, service);
                stmt.setInt(8, salary);
                stmt.setString(9, licenseID);
                stmt.setInt(10, experience);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    showAlert("Error", "No drone pilot was added. Possible duplicate entry.", Alert.AlertType.ERROR);
                } else {
                    showAlert("Success", "Drone Pilot added successfully.", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An unexpected SQL error has occurred.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Pilots_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
