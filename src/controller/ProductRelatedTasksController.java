package controller;

import application.Main;
import javafx.fxml.FXML;

public class ProductRelatedTasksController {

    @FXML
    private void handleAddProduct() throws Exception {
        System.out.println("Attempting to add a product...");
        Main.switchToView("/fxml/AddProduct.fxml");
    }

    @FXML
    private void handleRemoveProduct() throws Exception {
        System.out.println("Attempting to remove a product...");
        Main.switchToView("/fxml/RemoveProduct.fxml");
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
