package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Product;
import lk.ijse.gaming_arena.dto.Supplier;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SupplierModel {


    public static boolean add(String supplier_id, String supplier_name, String supplier_company, String supplier_contact,String supplier_email) throws SQLException {
        String sql = "INSERT INTO supplier(supplier_id, supplier_name, supplier_company, supplier_contact,supplier_email) VALUES(?, ?, ?, ?, ?)";

        return CrudUtil.execute(sql, supplier_id, supplier_name,  supplier_company,  supplier_contact,supplier_email);
    }
    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supplier_name = ?, supplier_company = ?, supplier_contact = ?,supplier_email  WHERE supplier_id = ?";
        return CrudUtil.execute(sql, supplier.getSupplier_id(), supplier.getSupplier_name(), supplier.getSupplier_company(),supplier.getSupplier_contact(),supplier.getSupplier_email());
    }
    public static boolean delete(String supplier_id) throws SQLException {
        try (Connection con = DBConnection.getInstance().getConnection()) {
            String sql = "DELETE FROM supplier WHERE supplier_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, supplier_id);

            return pstm.executeUpdate() > 0;
        }
    }
    public static Supplier search(String supplier_id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, supplier_id);

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String company = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);

            return new Supplier(id, name, company, contact, email);
        }
        return null;
    }
    public static List<String> getIds() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT supplier_id FROM supplier";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static List<Supplier> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return data;
    }
    public static String generateNextSupplierId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitSupplierId(resultSet.getString(1));
        }
        return splitSupplierId(null);
    }

    public static String splitSupplierId(String currentsupplierId) {
        if(currentsupplierId != null) {
            String[] strings = currentsupplierId.split("SP0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("SP%04d",+id);
        }
        return "SP0001";
    }
}
