package controller;
import application.Main;
import javafx.fxml.FXML;

public class PilotsRelatedTasksController {
    @FXML
    private void handleAddDronePilot() throws Exception {
        System.out.println("Attempting to add a drone pilot...");
        Main.switchToView("/fxml/AddDronePilot.fxml");
    }

    @FXML
    private void handleViewDronePilotRoster() throws Exception {
        System.out.println("Attempting to view drone pilot roster...");
        Main.switchToView("/fxml/ViewDronePilotRoster.fxml");
    }

    @FXML
    private void handleRemoveDronePilot() throws Exception {
        System.out.println("Attempting to remove a drone pilot...");
        Main.switchToView("/fxml/RemoveDronePilot.fxml");
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/MainDashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to return to the main dashboard.");
        }
    }
}
