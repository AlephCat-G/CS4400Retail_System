package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.RoleDistribution;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewRoleDistributionController {

    @FXML
    private TableView<RoleDistribution> tableRoleDistribution;
    @FXML
    private TableColumn<RoleDistribution, String> columnCategory;
    @FXML
    private TableColumn<RoleDistribution, Integer> columnTotal;

    @FXML
    public void initialize() {
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            loadRoleDistributionData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading role distribution data: " + e.getMessage());
        }
    }

    private void loadRoleDistributionData() throws SQLException {
        ObservableList<RoleDistribution> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM role_distribution";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String category = rs.getString("category");
            int total = rs.getInt("total");
            data.add(new RoleDistribution(category, total));
        }

        tableRoleDistribution.setItems(data);
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
