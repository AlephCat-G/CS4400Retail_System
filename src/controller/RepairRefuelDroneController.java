package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RepairRefuelDroneController {
    @FXML private TextField tfDroneStore;
    @FXML private TextField tfDroneTag;
    @FXML private TextField tfRefueledTrips;
    @FXML private Button btnRefuel;
    @FXML private Button btnCancel;

    @FXML
    private void handleRefuel() {
        String droneStore = tfDroneStore.getText();
        String droneTag = tfDroneTag.getText();
        int refueledTrips = Integer.parseInt(tfRefueledTrips.getText());

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call repair_refuel_drone(?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, droneStore);
                cstmt.setString(2, droneTag);
                cstmt.setInt(3, refueledTrips);
                cstmt.executeUpdate();
                System.out.println("Drone refueled successfully at store: " + droneStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Here you can log the error or show an error message to the user
        }
    }

    @FXML
    private void handleCancel() {
        try {
            // This will switch the view back to the previous view
            Main.switchToView("/fxml/MainDashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            // Here you can log the error or show an error message to the user
        }
    }
}
