package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.DronePilotRoster;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewDronePilotRosterController {

    @FXML
    private TableView<DronePilotRoster> tableDronePilotRoster;
    @FXML
    private TableColumn<DronePilotRoster, String> columnPilot;
    @FXML
    private TableColumn<DronePilotRoster, String> columnLicenseID;
    @FXML
    private TableColumn<DronePilotRoster, String> columnDroneServesStore;
    @FXML
    private TableColumn<DronePilotRoster, Integer> columnDroneTag;
    @FXML
    private TableColumn<DronePilotRoster, Integer> columnSuccessfulDeliveries;
    @FXML
    private TableColumn<DronePilotRoster, Integer> columnPendingDeliveries;

    @FXML
    public void initialize() {
        columnPilot.setCellValueFactory(new PropertyValueFactory<>("pilot"));
        columnLicenseID.setCellValueFactory(new PropertyValueFactory<>("licenseID"));
        columnDroneServesStore.setCellValueFactory(new PropertyValueFactory<>("droneServesStore"));
        columnDroneTag.setCellValueFactory(new PropertyValueFactory<>("droneTag"));
        columnSuccessfulDeliveries.setCellValueFactory(new PropertyValueFactory<>("successfulDeliveries"));
        columnPendingDeliveries.setCellValueFactory(new PropertyValueFactory<>("pendingDeliveries"));

        try {
            loadDronePilotRosterData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading drone pilot roster data: " + e.getMessage());
        }
    }

    private void loadDronePilotRosterData() throws SQLException {
        ObservableList<DronePilotRoster> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM drone_pilot_roster";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            data.add(new DronePilotRoster(
                    rs.getString("pilot"),
                    rs.getString("licenseID"),
                    rs.getString("drone_serves_store"),
                    rs.getInt("drone_tag"),
                    rs.getInt("successful_deliveries"),
                    rs.getInt("pending_deliveries")
            ));
        }

        tableDronePilotRoster.setItems(data);
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
