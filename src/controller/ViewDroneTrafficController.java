package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.DroneTraffic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewDroneTrafficController {

    @FXML
    private TableView<DroneTraffic> tableDroneTraffic;
    @FXML
    private TableColumn<DroneTraffic, String> columnServesStore;
    @FXML
    private TableColumn<DroneTraffic, String> columnDroneTag;
    @FXML
    private TableColumn<DroneTraffic, String> columnPilot;
    @FXML
    private TableColumn<DroneTraffic, Integer> columnTotalWeightAllowed;
    @FXML
    private TableColumn<DroneTraffic, Integer> columnCurrentWeight;
    @FXML
    private TableColumn<DroneTraffic, Integer> columnDeliveriesAllowed;
    @FXML
    private TableColumn<DroneTraffic, Integer> columnDeliveriesInProgress;

    @FXML
    public void initialize() {
        columnServesStore.setCellValueFactory(new PropertyValueFactory<>("droneServesStore"));
        columnDroneTag.setCellValueFactory(new PropertyValueFactory<>("droneTag"));
        columnPilot.setCellValueFactory(new PropertyValueFactory<>("pilot"));
        columnTotalWeightAllowed.setCellValueFactory(new PropertyValueFactory<>("totalWeightAllowed"));
        columnCurrentWeight.setCellValueFactory(new PropertyValueFactory<>("currentWeight"));
        columnDeliveriesAllowed.setCellValueFactory(new PropertyValueFactory<>("deliveriesAllowed"));
        columnDeliveriesInProgress.setCellValueFactory(new PropertyValueFactory<>("deliveriesInProgress"));

        try {
            loadDroneTrafficData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading drone traffic data: " + e.getMessage());
        }
    }

    private void loadDroneTrafficData() throws SQLException {
        ObservableList<DroneTraffic> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM drone_traffic_control";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            // Assume DroneTrafficControl has a constructor that matches the table columns
            DroneTraffic entry = new DroneTraffic(
                    rs.getString("drone_serves_store"),
                    rs.getString("drone_tag"),
                    rs.getString("pilot"),
                    rs.getInt("total_weight_allowed"),
                    rs.getInt("current_weight"),
                    rs.getInt("deliveries_allowed"),
                    rs.getInt("deliveries_in_progress")
            );
            data.add(entry);
        }

        tableDroneTraffic.setItems(data);
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
            System.out.println("Failed to return to the views DroneTraffic.");
        }
    }
}
