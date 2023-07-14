package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CartDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModel {
    private static boolean save(String order_id, CartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO order_details(order_id, item_id, qty, total_price) VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, order_id);
        pstm.setString(2, dto.getItem_id());
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getTotal_price());

        return pstm.executeUpdate() > 0;

    }
    public static boolean save(String order_id, List<CartDTO> cartDTOList) throws SQLException {
        for(CartDTO dto :  cartDTOList) {
            if(!save(order_id, dto)) {
                return false;
            }
        }
        return true;
    }
}
