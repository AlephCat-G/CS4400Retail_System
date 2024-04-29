package controller;
import application.Main;
import javafx.fxml.FXML;

public class ViewsRelatedTasksController {
    @FXML
    private void handleRoleDistribution() throws Exception {
        System.out.println("Attempting to view role distribution...");
        Main.switchToView("/fxml/ViewRoleDistribution.fxml");
    }

    @FXML
    private void handleCustomerCreditCheck() throws Exception {
        System.out.println("Attempting to view customer credit check...");
        Main.switchToView("/fxml/ViewCustomerCreditCheck.fxml");
    }

    @FXML
    private void handleDroneTrafficControl() throws Exception {
        System.out.println("Attempting to view drone traffic control...");
        Main.switchToView("/fxml/ViewDroneTrafficControl.fxml");
    }

    @FXML
    private void handleMostPopularProducts() throws Exception {
        System.out.println("Attempting to view most popular products...");
        Main.switchToView("/fxml/ViewMostPopularProducts.fxml");
    }

    @FXML
    private void handleDronePilotRoster() throws Exception {
        System.out.println("Attempting to view drone pilot roster...");
        Main.switchToView("/fxml/ViewDronePilotRoster.fxml");
    }

    @FXML
    private void handleStoreSalesOverview() throws Exception {
        System.out.println("Attempting to view store sales overview...");
        Main.switchToView("/fxml/ViewStoreSalesOverview.fxml");
    }

    @FXML
    private void handleOrdersInProgress() throws Exception {
        System.out.println("Attempting to view orders in progress...");
        Main.switchToView("/fxml/ViewOrdersInProgress.fxml");
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
