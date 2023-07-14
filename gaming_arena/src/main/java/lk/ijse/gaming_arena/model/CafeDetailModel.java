package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CafeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CafeDetailModel {
    public static boolean save(String bill_id,CafeDTO dto) throws SQLException {
            if(!save1(bill_id,dto)) {
                return false;
            }
        return true;
    }

    private static boolean save1(String bill_id, CafeDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO cafe_details(bill_id, computer_id,end_time, start_time,payment) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, bill_id);
        pstm.setString(2, dto.getComputer_id());
        pstm.setDouble(3, dto.getStart_time());
        pstm.setDouble(4, dto.getEnd_time());
        pstm.setDouble(5, dto.getPayment());

        return pstm.executeUpdate() > 0;
    }
}
