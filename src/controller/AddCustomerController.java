package controller;

import database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;


public class AddCustomerController {

    @FXML private TextField tfUsername;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAddress;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField tfRating;
    @FXML private TextField tfCredit;

    @FXML
    private void handleAddCustomer() {
        String username = tfUsername.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String address = tfAddress.getText();
        // 需要处理 dpBirthday.getValue() 可能为空的情况
        String birthdate = dpBirthday.getValue() != null ? dpBirthday.getValue().toString() : null;
        int rating = Integer.parseInt(tfRating.getText());
        int credit = Integer.parseInt(tfCredit.getText());

        // 使用 try-with-resources 语句来自动关闭连接
        try (Connection conn = DatabaseConnector.getConnection()) {
            // 调用存储过程 add_customer
            String call = "{call add_customer(?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(call)) {
                cstmt.setString(1, username);
                cstmt.setString(2, firstName);
                cstmt.setString(3, lastName);
                cstmt.setString(4, address);
                if (birthdate != null) {
                    cstmt.setDate(5, java.sql.Date.valueOf(birthdate));
                } else {
                    cstmt.setNull(5, java.sql.Types.DATE);
                }
                cstmt.setInt(6, rating);
                cstmt.setInt(7, credit);
                cstmt.executeUpdate();
                System.out.println("Customer added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理异常，更新 GUI
        }
    }

    @FXML
    private void handleCancel() {
        // 清空所有输入框，或关闭窗口等
    }
}
