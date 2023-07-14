package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class StockDetailsModel {
    public static boolean save(String item_id, String supplier_id,int number_of_qty,double one_qty_price, LocalDate date) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier_items(item_id, supplier_id,number_of_unit,one_qty_price,date) VALUES (?, ?, ?, ?, ?)";

        LocalTime t=LocalTime.now();
        String time=String.valueOf(t);

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, item_id);
        pstm.setString(2, supplier_id);
        pstm.setInt(3,number_of_qty);
        pstm.setDouble(4,one_qty_price);
        pstm.setString(5, (String.valueOf(date)+" Time "+time));


        return pstm.executeUpdate() >= 0;
    }
}
