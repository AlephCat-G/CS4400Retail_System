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
            Main.switchToView("/fxml/MainDashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
