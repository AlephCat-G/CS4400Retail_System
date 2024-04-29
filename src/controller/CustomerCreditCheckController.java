package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.CustomerCredit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerCreditCheckController {

    @FXML
    private TableView<CustomerCredit> tableCustomerCredit;
    @FXML
    private TableColumn<CustomerCredit, String> columnCustomerName;
    @FXML
    private TableColumn<CustomerCredit, Integer> columnRating;
    @FXML
    private TableColumn<CustomerCredit, Integer> columnCurrentCredit;
    @FXML
    private TableColumn<CustomerCredit, Integer> columnCreditAllocated;

    @FXML
    public void initialize() {
        columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        columnCurrentCredit.setCellValueFactory(new PropertyValueFactory<>("currentCredit"));
        columnCreditAllocated.setCellValueFactory(new PropertyValueFactory<>("creditAllocated"));

        try {
            loadCustomerCreditsData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading customer credits data: " + e.getMessage());
        }
    }

    private void loadCustomerCreditsData() throws SQLException {
        ObservableList<CustomerCredit> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM customer_credit_check";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String customerName = rs.getString("customer_name");
            int rating = rs.getInt("rating");
            int currentCredit = rs.getInt("current_credit");
            int creditAllocated = rs.getInt("credit_already_allocated");
            data.add(new CustomerCredit(customerName, rating, currentCredit, creditAllocated));
        }

        tableCustomerCredit.setItems(data);
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
