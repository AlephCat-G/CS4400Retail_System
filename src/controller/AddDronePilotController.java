package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

public class AddDronePilotController {
    @FXML private TextField tfUsername;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAddress;
    @FXML private TextField tfBirthdate;
    @FXML private TextField tfTaxID;
    @FXML private TextField tfService;
    @FXML private TextField tfSalary;
    @FXML private TextField tfLicenseID;
    @FXML private TextField tfExperience;
    @FXML private Button btnCancel;
    @FXML private Button btnAdd;


    @FXML
    private void handleAddDronePilot() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        Date birthdate = Date.valueOf(tfBirthdate.getText());
        String taxID = tfTaxID.getText();
        int service = Integer.parseInt(tfService.getText());
        int salary = Integer.parseInt(tfSalary.getText());
        String licenseID = tfLicenseID.getText();
        int experience = Integer.parseInt(tfExperience.getText());

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
                stmt.execute();
                System.out.println("Drone Pilot added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}
