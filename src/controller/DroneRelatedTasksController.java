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
        Main.switchToView("/fxml/RepairRefuelDrone.fxml");
    }

    @FXML
    private void handleAddDrone() throws Exception {
        System.out.println("Attempting to add a drone...");
        Main.switchToView("/fxml/AddDrone.fxml");
    }



    @FXML
    private void handleRemoveDrone() throws Exception {
        System.out.println("Attempting to remove a drone...");
        Main.switchToView("/fxml/RemoveDrone.fxml");
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/MainDashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
