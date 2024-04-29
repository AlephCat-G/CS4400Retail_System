package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainDashboardController {
    @FXML
    private Button customersButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button dronesButton;
    @FXML
    private Button pilotsButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button viewsButton;

    @FXML
    private void handleCustomersAction() {
        try {
            Main.switchToView("/fxml/Customer_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductsAction() {
        // TODO: Implement view switching logic
        switchView("fxml/ProductsView.fxml");
    }

    @FXML
    private void handleDronesAction() {
        try {
            Main.switchToView("/fxml/Drone_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePilotsAction() {
        try {
            Main.switchToView("/fxml/Pilots_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOrdersAction() {
        // TODO: Implement view switching logic
        switchView("fxml/OrdersView.fxml");
    }

    @FXML
    private void handleViewsAction() {
        // TODO: Implement view switching logic
        switchView("fxml/ViewsView.fxml");
    }

    private void switchView(String fxmlFile) {
        try {
            Main.switchToView(fxmlFile);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Handle exception (e.g. show error message)
        }
    }


}
