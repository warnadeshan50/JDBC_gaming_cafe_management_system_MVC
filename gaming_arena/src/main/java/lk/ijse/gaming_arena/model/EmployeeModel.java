package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Employee;
import lk.ijse.gaming_arena.dto.Supplier;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeModel {

    public static boolean add(String employee_id, String employee_name, String attendent_id, String employee_address,String employee_contact,String employee_role) throws SQLException {
        String sql = "INSERT INTO employee(employee_id,  employee_name, attendent_id, employee_address,employee_contact, employee_role) VALUES(?, ?, ?, ?, ?, ?)";

        return CrudUtil.execute(sql,employee_id, employee_name,  attendent_id,  employee_address, employee_contact,employee_role);
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET employee_name = ?, attendent_id = ?, employee_address = ?, employee_contact=?, employee_role = ? WHERE supplier_id = ?";
        return CrudUtil.execute(sql,employee.getEmployee_id(), employee.getEmployee_name(), employee.getAttendent_id(),employee.getEmployee_address(),employee.getEmployee_contact(),employee.getEmployee_role());
    }

    public static boolean delete(String employee_id) throws SQLException {
        try (Connection con = DBConnection.getInstance().getConnection()) {
            String sql = "DELETE FROM employee WHERE employee_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, employee_id);

            return pstm.executeUpdate() > 0;
        }
    }

    public static Employee search(String supplier_id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, supplier_id);

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String at_id = resultSet.getString(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);
            String role = resultSet.getString(6);

            return new Employee(id, name, at_id,address, contact, role);
        }
        return null;
    }
    public static List<String> getEmployeeIds() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT employee_id FROM employee";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }
}
