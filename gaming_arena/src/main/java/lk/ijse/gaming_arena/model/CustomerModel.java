package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Employee;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean add(String customer_id,String employee_id, String customer_name, String customer_address, String customer_contact,String customer_email ) throws SQLException {
        String sql = "INSERT INTO customers(customer_id,employee_id, customer_name, customer_address, customer_contact,customer_email) VALUES(?, ?, ?, ?, ?, ?)";

        return CrudUtil.execute(sql, customer_id, employee_id, customer_name,  customer_address,customer_contact,customer_email);
    }
    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET employee_id = ?, customer_name = ?, customer_address = ?, customer_contact=?, customer_email = ? WHERE customer_id = ?";
        return CrudUtil.execute(sql,customer.getEmployee_id(),customer.getCustomer_name(),customer.getCustomer_address(),customer.getCustomer_contact(),customer.getCustomer_email(),customer.getCustomer_id());
    }

    public static boolean delete(String customer_id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        try  {
            String sql = "DELETE FROM customers WHERE customer_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, customer_id);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            System.out.println(e);
        }
        return false;
    }

    public static List<Customer> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customers";

        List<Customer> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public static Customer search(String id)throws SQLException{
        String sql="SELECT * FROM Customers WHERE customer_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);
        if (resultSet.next()){
            String customer_id=resultSet.getString(1);
            String employee_id=resultSet.getString(2);
            String customer_name=resultSet.getString(3);
            String customer_address=resultSet.getString(4);
            String custoemr_contact=resultSet.getString(5);
            String custoemr_email=resultSet.getString(6);
            return new Customer(customer_id,employee_id,customer_name,customer_address,custoemr_contact,custoemr_email);
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT customer_id FROM Customers";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }
    public static Customer searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }
    public static String generateNextCustomerId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT customer_id FROM customers ORDER BY customer_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }

    public static String splitCustomerId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] strings = currentCustomerId.split("CU0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("CU%04d",+id);
        }
        return "CU0001";
    }
}
