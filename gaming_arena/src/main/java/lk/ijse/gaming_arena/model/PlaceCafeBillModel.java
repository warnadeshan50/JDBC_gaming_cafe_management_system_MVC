package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CafeDTO;
import lk.ijse.gaming_arena.dto.CartDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceCafeBillModel {
    public static boolean placeBill(String bill_id, String cus_id, CafeDTO dto) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = CafeBillModel.save(bill_id, cus_id, LocalDate.now());
                if (isSaved) {
                    boolean isbillDetailSaved = CafeDetailModel.save(bill_id,dto);
                    if (isbillDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }
}
