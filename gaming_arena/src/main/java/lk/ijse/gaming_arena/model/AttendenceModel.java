package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Attendence;
import lk.ijse.gaming_arena.dto.Employee;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AttendenceModel {

    public static boolean add(String attendent_id, String attendent_type, int attendent_hours, double attendent_salary) throws SQLException {
        String sql = "INSERT INTO employee(attendent_id,  attendent_type, attendent_hours, attendent_salary) VALUES(?, ?, ?, ?)";

        return CrudUtil.execute(sql, attendent_id,  attendent_type, attendent_hours,attendent_salary);
    }

    public static boolean update(Attendence attendence) throws SQLException {
        String sql = "UPDATE attendence SET  attendent_type = ?, attendent_hours = ?,attendent_salary WHERE attendent_id = ?";
        return CrudUtil.execute(sql,attendence.getAttendent_id(), attendence.getAttendent_type(), attendence.getAttendent_hours(),attendence.getAttendent_salary());
    }

    public static boolean delete(String attendent_id) throws SQLException {
        try (Connection con = DBConnection.getInstance().getConnection()) {
            String sql = "DELETE FROM attendence WHERE attendent_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, attendent_id);

            return pstm.executeUpdate() > 0;
        }
    }

    public static Attendence search(String attendent_id) throws SQLException {
        String sql = "SELECT * FROM attendence WHERE attendent_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, attendent_id);

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            Integer hours = resultSet.getInt(3);
            Double salary = resultSet.getDouble(4);


            return new Attendence(id, type,hours,salary);
        }
        return null;
    }
}
