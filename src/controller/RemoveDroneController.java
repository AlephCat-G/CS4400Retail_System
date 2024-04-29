package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class RemoveDroneController {

    @FXML private TextField tfStoreID;
    @FXML private TextField tfDroneTag;
    @FXML private Button btnCancel;
    @FXML private Button btnRemove;

    @FXML
    private void handleRemoveDrone() {
        String storeID = tfStoreID.getText();
        String droneTag = tfDroneTag.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call remove_drone(?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, storeID);
                cstmt.setString(2, droneTag);
                cstmt.executeUpdate();
                System.out.println("Drone removed successfully from store: " + storeID);
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
