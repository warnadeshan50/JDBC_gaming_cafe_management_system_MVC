package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CartDTO;
import lk.ijse.gaming_arena.dto.Product;

import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductModel {

    public static boolean add(String item_id, String item_description, int item_on_hand_qty, double item_one_qty_price,String type) throws SQLException {
        String sql = "INSERT INTO Items(item_id, item_description, item_on_hand_qty, item_one_qty_price, type) VALUES(?, ?, ?, ?, ?)";

        return CrudUtil.execute(sql, item_id, item_description, item_on_hand_qty, item_one_qty_price,type);
    }

    public static boolean update(Product product) throws SQLException {
        String sql = "UPDATE Items SET item_description = ?, item_on_hand_qty = ?, item_one_qty_price = ?, type = ? WHERE item_id = ?";
        return CrudUtil.execute(sql,product.getItem_description(), product.getItem_on_hand_qty(), product.getItem_one_qty_price(), product.getType(),product.getItem_id());
    }

    public static boolean delete(String item_id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        try {
            String sql = "DELETE FROM items WHERE item_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, item_id);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            System.out.println(e);
        }
        return false;
    }

    public static Product search(String item_id) throws SQLException {

        String sql = "SELECT * FROM Items WHERE item_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, item_id);

        if(resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            Integer on_hand_qty = resultSet.getInt(3);
            Double one_qty_price = resultSet.getDouble(4);
            String type=resultSet.getString(5);


            return new Product(id, description,on_hand_qty,one_qty_price,type);
        }
        return null;
    }
    public static List<Product> searchAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Items");
        List<Product> dataList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            Double one_qty_price = resultSet.getDouble(3);
            Integer on_hand_qty = resultSet.getInt(4);
            String type=resultSet.getString(5);


            var item = new Product(id, description,on_hand_qty, one_qty_price,type);
            dataList.add(item);
        }
        return dataList;
    }
    public static List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT item_id FROM Items";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }
    public static Product searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Items WHERE item_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }
    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
       for (CartDTO dto : cartDTOList) {
           if(!updateQty(dto)) {
               return false;
            }
        }
        return true;
    }
    private static boolean updateQty(CartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Items SET item_on_hand_qty = (item_on_hand_qty - ?) WHERE item_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, dto.getQty());
        pstm.setString(2, dto.getItem_id());

        return pstm.executeUpdate() > 0;
    }
    public static String generateNextProductId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT item_id FROM items ORDER BY item_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitProductId(resultSet.getString(1));
        }
        return splitProductId(null);
    }
    public static String splitProductId(String currentProductId) {
        if(currentProductId != null) {
            String[] strings = currentProductId.split("IT0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("IT%04d",+id);
        }
        return "IT0001";
    }

    public static List<Product> getByType(String type) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Items WHERE type = ?";

        List<Product>data=new ArrayList<>();

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, type);

        ResultSet resultSet = pstm.executeQuery();
        while(resultSet.next()) {
            data.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    /*private static boolean updateFromSupplyQty(StockDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Items SET item_on_hand_qty = (item_on_hand_qty + ?) WHERE item_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, dto.getNumber_of_unit());
        pstm.setString(2, dto.getItem_id());

        return pstm.executeUpdate() > 0;
    }*/
}
