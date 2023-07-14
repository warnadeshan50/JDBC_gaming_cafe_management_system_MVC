package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CafeDTO;
import lk.ijse.gaming_arena.dto.CartDTO;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CafeBillModel {
    public static String generateNextBillId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT bill_id FROM cafe_bill ORDER BY bill_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitBillId(resultSet.getString(1));
        }
        return splitBillId(null);
    }
    public static boolean save(String bill_id, String cus_id, LocalDate date) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO cafe_bill(bill_id, customer_id,date) VALUES (?, ?, ?)";

        LocalTime t=LocalTime.now();
        String time=String.valueOf(t);

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, bill_id);
        pstm.setString(2, cus_id);
        pstm.setString(3, (String.valueOf(date)+" Time "+time));


        return pstm.executeUpdate() >= 0;
    }
    public static String splitBillId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("CB0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return  String.format("CB%04d",+id);
        }
        return "CB0001";
    }

}

