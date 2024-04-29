package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RemoveDronePilotController {

    @FXML private TextField tfUsername;
    @FXML private Button btnCancel;
    @FXML private Button btnRemove;

    @FXML
    private void handleRemoveDronePilot() {
        String username = tfUsername.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call remove_drone_pilot(?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.executeUpdate();
                System.out.println("Drone pilot removed successfully");
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
            Main.switchToView("/fxml/Pilot_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, possibly show an error message to the user
        }
    }
}
