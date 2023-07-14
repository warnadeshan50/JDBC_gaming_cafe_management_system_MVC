package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderModel {
    public static boolean save(String oId, String cusId, LocalDate date) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Orders(order_id, order_date, customer_id) VALUES (?, ?, ?)";

        LocalTime time= LocalTime.now();
        String.valueOf(time);
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, (String.valueOf(date)+" Time "+time));
        pstm.setString(3, cusId);

        return pstm.executeUpdate() > 0;
    }
    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("OR0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("OR0%03d",+id);
        }
        return "OR0001";
    }
    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

}
