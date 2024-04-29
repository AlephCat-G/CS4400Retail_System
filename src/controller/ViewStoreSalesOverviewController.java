package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.StoreSalesOverview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewStoreSalesOverviewController {
    @FXML
    private TableView<StoreSalesOverview> tableStoreSalesOverview;
    @FXML
    private TableColumn<StoreSalesOverview, String> columnStoreId;
    @FXML
    private TableColumn<StoreSalesOverview, String> columnSname;
    @FXML
    private TableColumn<StoreSalesOverview, String> columnManager;
    @FXML
    private TableColumn<StoreSalesOverview, Integer> columnRevenue;
    @FXML
    private TableColumn<StoreSalesOverview, Integer> columnIncomingRevenue;
    @FXML
    private TableColumn<StoreSalesOverview, Integer> columnIncomingOrders;

    @FXML
    public void initialize() {
        columnStoreId.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        columnSname.setCellValueFactory(new PropertyValueFactory<>("sname"));
        columnManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        columnRevenue.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        columnIncomingRevenue.setCellValueFactory(new PropertyValueFactory<>("incomingRevenue"));
        columnIncomingOrders.setCellValueFactory(new PropertyValueFactory<>("incomingOrders"));

        try {
            loadStoreSalesOverviewData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading store sales overview data: " + e.getMessage());
        }
    }

    private void loadStoreSalesOverviewData() throws SQLException {
        ObservableList<StoreSalesOverview> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM store_sales_overview";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            StoreSalesOverview storeSalesOverview = new StoreSalesOverview(
                    rs.getString("store_id"),
                    rs.getString("sname"),
                    rs.getString("manager"),
                    rs.getInt("revenue"),
                    rs.getInt("incoming_revenue"),
                    rs.getInt("incoming_orders")
            );
            data.add(storeSalesOverview);
        }

        tableStoreSalesOverview.setItems(data);
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
