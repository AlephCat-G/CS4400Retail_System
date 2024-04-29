package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class AddDroneController {

    @FXML private TextField tfStoreID;
    @FXML private TextField tfDroneTag;
    @FXML private TextField tfCapacity;
    @FXML private TextField tfRemainingTrips;
    @FXML private TextField tfPilot;

    @FXML
    private void handleAddDrone() {
        String storeID = tfStoreID.getText();
        String droneTag = tfDroneTag.getText();
        int capacity = Integer.parseInt(tfCapacity.getText());
        int remainingTrips = Integer.parseInt(tfRemainingTrips.getText());
        String pilot = tfPilot.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call add_drone(?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, storeID);
                cstmt.setString(2, droneTag);
                cstmt.setInt(3, capacity);
                cstmt.setInt(4, remainingTrips);
                cstmt.setString(5, pilot);
                cstmt.executeUpdate();
                System.out.println("Drone added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCancel() {
        try {
            // This will switch the view back to the MainDashboard.fxml view
            Main.switchToView("/fxml/Drone_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
