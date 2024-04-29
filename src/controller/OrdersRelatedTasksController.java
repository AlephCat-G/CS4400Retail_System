package controller;

import application.Main;
import javafx.fxml.FXML;

public class OrdersRelatedTasksController {
    @FXML
    private void handleBeginOrder() throws Exception {
        System.out.println("Attempting to begin a new order...");
        Main.switchToView("/fxml/BeginOrder.fxml");
    }

    @FXML
    private void handleAddOrderLine() throws Exception {
        System.out.println("Attempting to add an order line...");
        Main.switchToView("/fxml/AddOrderLine.fxml");
    }

    @FXML
    private void handleDeliverOrder() throws Exception {
        System.out.println("Attempting to deliver an order...");
        Main.switchToView("/fxml/DeliverOrder.fxml");
    }

    @FXML
    private void handleCancelOrder() throws Exception {
        System.out.println("Attempting to cancel an order...");
        Main.switchToView("/fxml/CancelOrder.fxml");
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
