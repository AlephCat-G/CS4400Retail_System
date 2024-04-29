package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class SwapDroneControlController {

    @FXML private TextField tfIncomingPilot;
    @FXML private TextField tfOutgoingPilot;
    @FXML private Button btnCancel;
    @FXML private Button btnConfirm;

    @FXML
    private void handleConfirm() {
        String incomingPilot = tfIncomingPilot.getText();
        String outgoingPilot = tfOutgoingPilot.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {
            String call = "{call swap_drone_control(?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, incomingPilot);
                cstmt.setString(2, outgoingPilot);
                cstmt.executeUpdate();
                System.out.println("Drone control swapped successfully from " + outgoingPilot + " to " + incomingPilot);
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
            // Here you can log the error or show an error message to the user
        }
    }
}

