package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import database.DatabaseConnector;
import model.MostPopularProduct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewMostPopularProductsController {

    @FXML
    private TableView<MostPopularProduct> tableMostPopularProducts;
    @FXML
    private TableColumn<MostPopularProduct, String> columnBarcode;
    @FXML
    private TableColumn<MostPopularProduct, String> columnProductName;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnWeight;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnLowestPrice;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnHighestPrice;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnLowestQuantity;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnHighestQuantity;
    @FXML
    private TableColumn<MostPopularProduct, Integer> columnTotalQuantity;

    @FXML
    public void initialize() {
        columnBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        columnLowestPrice.setCellValueFactory(new PropertyValueFactory<>("lowestPrice"));
        columnHighestPrice.setCellValueFactory(new PropertyValueFactory<>("highestPrice"));
        columnLowestQuantity.setCellValueFactory(new PropertyValueFactory<>("lowestQuantity"));
        columnHighestQuantity.setCellValueFactory(new PropertyValueFactory<>("highestQuantity"));
        columnTotalQuantity.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));

        try {
            loadMostPopularProductsData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading most popular products data: " + e.getMessage());
        }
    }

    private void loadMostPopularProductsData() throws SQLException {
        ObservableList<MostPopularProduct> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM most_popular_products";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String barcode = rs.getString("barcode");
            String productName = rs.getString("product_name");
            int weight = rs.getInt("weight");
            int lowestPrice = rs.getInt("lowest_price");
            int highestPrice = rs.getInt("highest_price");
            int lowestQuantity = rs.getInt("lowest_quantity");
            int highestQuantity = rs.getInt("highest_quantity");
            int totalQuantity = rs.getInt("total_quantity");

            data.add(new MostPopularProduct(barcode, productName, weight,
                    lowestPrice, highestPrice, lowestQuantity, highestQuantity, totalQuantity));
        }

        tableMostPopularProducts.setItems(data);
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
