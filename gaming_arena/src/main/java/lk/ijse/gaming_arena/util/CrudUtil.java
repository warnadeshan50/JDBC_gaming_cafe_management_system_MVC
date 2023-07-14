package lk.ijse.gaming_arena.util;

import lk.ijse.gaming_arena.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CrudUtil {
    public static <T>T execute(String sql, Object... args) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1), args[i]);
        }

        if(sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) pstm.executeQuery(); // ResultSet
        }
        return (T) (Boolean)(pstm.executeUpdate() > 0); //Boolean
    }
}
