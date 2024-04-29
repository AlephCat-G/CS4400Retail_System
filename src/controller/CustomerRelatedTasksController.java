package controller;

import application.Main;
import javafx.fxml.FXML;

public class CustomerRelatedTasksController {

    @FXML
    public void handleAddCustomer() throws Exception {
        System.out.println("Attempting to add customer...");
        Main.switchToView("/fxml/AddCustomerView.fxml");
    }

    @FXML
    private void handleIncreaseCredits() throws Exception {
        // Logic to switch to the Increase Customer Credits View
        Main.switchToView("/fxml/IncreaseCredit.fxml");
    }


    @FXML
    private void handleRemoveCustomer() throws Exception {
        System.out.println("Attempting to remove a customer...");
        Main.switchToView("/fxml/RemoveCustomer.fxml");
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
