package controller;

import application.Main;
import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeliverOrderController {

    @FXML private TextField tfOrderID;
    @FXML private Button btnDeliver;
    @FXML private Button btnCancel;

    @FXML
    private void handleDeliverOrder() {
        String orderID = tfOrderID.getText().trim();

        if (orderID.isEmpty()) {
            showAlert("Input Error", "Order ID cannot be empty.", AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            if (!orderExists(conn, orderID)) {
                showAlert("Error", "No order found with the provided ID.", AlertType.ERROR);
                return;
            }

            if (!canDeliverOrder(conn, orderID)) {
                showAlert("Error", "No remaining drone trips available for this order.", AlertType.ERROR);
                callProcedure(conn, "cancel_order", orderID);
                return;
            }

            deliverOrder(conn, orderID);
            showAlert("Success", "Order delivered successfully.", AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An SQL error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private boolean orderExists(Connection conn, String orderID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM orders WHERE orderID = ?")) {
            pstmt.setString(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private boolean canDeliverOrder(Connection conn, String orderID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT remaining_trips FROM drones WHERE storeID = (SELECT carrier_store FROM orders WHERE orderID = ?) AND droneTag = (SELECT carrier_tag FROM orders WHERE orderID = ?)")) {
            pstmt.setString(1, orderID);
            pstmt.setString(2, orderID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("remaining_trips") > 0;
            }
        }
        return false;
    }

    private void deliverOrder(Connection conn, String orderID) throws SQLException {
        try (CallableStatement cstmt = conn.prepareCall("{call deliver_order(?)}")) {
            cstmt.setString(1, orderID);
            cstmt.executeUpdate();
        }
    }

    private void callProcedure(Connection conn, String procedureName, String param) throws SQLException {
        try (CallableStatement cstmt = conn.prepareCall("{call " + procedureName + "(?)}")) {
            cstmt.setString(1, param);
            cstmt.executeUpdate();
        }
    }

    @FXML
    private void handleCancel() {
        switchTo("Orders_Related_Tasks.fxml");
    }

    private void switchTo(String fxmlFile) {
        try {
            Main.switchToView("/fxml/" + fxmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
