package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.OrderInProgress;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewOrderInProgressController {

    @FXML
    private TableView<OrderInProgress> tableOrdersInProgress;
    @FXML
    private TableColumn<OrderInProgress, String> columnOrderID;
    @FXML
    private TableColumn<OrderInProgress, Integer> columnCost;
    @FXML
    private TableColumn<OrderInProgress, Integer> columnNumProducts;
    @FXML
    private TableColumn<OrderInProgress, Integer> columnPayload;
    @FXML
    private TableColumn<OrderInProgress, String> columnContents;

    @FXML
    public void initialize() {
        columnOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnNumProducts.setCellValueFactory(new PropertyValueFactory<>("numProducts"));
        columnPayload.setCellValueFactory(new PropertyValueFactory<>("payload"));
        columnContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

        try {
            loadOrdersInProgressData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading orders in progress data: " + e.getMessage());
        }
    }

    private void loadOrdersInProgressData() throws SQLException {
        ObservableList<OrderInProgress> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM orders_in_progress";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            OrderInProgress order = new OrderInProgress(
                    rs.getString("orderID"),
                    rs.getInt("cost"),
                    rs.getInt("num_products"),
                    rs.getInt("payload"),
                    rs.getString("contents")
            );
            data.add(order);
        }

        tableOrdersInProgress.setItems(data);
        rs.close();
        stmt.close();
        conn.close();
    }

    @FXML
    private void handleCancel() {
        try {
            Main.switchToView("/fxml/Views_Related_Tasks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to return to the views dashboard.");
        }
    }
}

