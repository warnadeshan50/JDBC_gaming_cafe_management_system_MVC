package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Computer;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerModel {
    public static List<Computer> searchAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        List<Computer> dataList = new ArrayList<>();

        while (resultSet.next()) {
            String compId = resultSet.getString(1);
            String description = resultSet.getString(2);
            Double per_hour = resultSet.getDouble(3);


            var computer = new Computer(compId, description, per_hour);
            dataList.add(computer);
        }
        return dataList;
    }
    public static List<Computer> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM cafe_computers";

        List<Computer> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Computer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)

            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT computer_id FROM cafe_computers";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Computer searchById(String compId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Cafe_computers WHERE computer_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, compId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Computer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;
    }
}
