package controller;

import application.Main;
import javafx.fxml.FXML;

public class DroneRelatedTasksController {


    @FXML
    private void handleSwapDroneControl() throws Exception {
        System.out.println("Attempting to swap drone control...");
        Main.switchToView("/fxml/SwapDrone.fxml");
    }

    @FXML
    private void handleRepairAndRefuelDrone() throws Exception {
        System.out.println("Attempting to repair and refuel drone...");
        Main.switchToView("/fxml/RepairAndRefuelDroneView.fxml");
    }

    @FXML
    private void handleAddDrone() throws Exception {
        System.out.println("Attempting to add a drone...");
        Main.switchToView("/fxml/AddDrone.fxml");
    }

    @FXML
    private void handleViewDroneTrafficControl() throws Exception {
        System.out.println("Attempting to view drone traffic control...");
        Main.switchToView("/fxml/ViewDroneTrafficControlView.fxml");
    }


    @FXML
    private void handleRemoveDrone() throws Exception {
        System.out.println("Attempting to remove a drone...");
        Main.switchToView("/fxml/RemoveDroneView.fxml");
    }

    @FXML
    private void handleCancel() {
        try {
            // This will switch the view back to the MainDashboard.fxml view
            Main.switchToView("/fxml/MainDashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
