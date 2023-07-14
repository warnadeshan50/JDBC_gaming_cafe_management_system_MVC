package lk.ijse.gaming_arena.model;

import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Membership;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MembershipModel {
    private final static String URL = "jdbc:mysql://localhost:3306/gaming_arena";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    public static boolean save(String membership_id, String customer_id, String type_of_membership,double monthly_payment ) throws SQLException {
        String sql = "INSERT INTO membership(membership_id, customer_id,type_of_membership, monthly_payment) VALUES(? ,? ,? ,? )";

        return CrudUtil.execute(sql, membership_id, customer_id,type_of_membership, monthly_payment);
    }

    public static boolean update(Membership membership) throws SQLException {
        String sql = "UPDATE membership SET customer_id = ?, type_of_membership = ?,monthly_payment = ? WHERE membership_id = ?";
        return CrudUtil.execute(sql, membership.getCustomer_id(), membership.getType_of_membership(),membership.getMonthly_payment(), membership.getMembership_id());
    }

    public static Membership search(String membership_id) throws SQLException {
        String sql = "SELECT * FROM membership WHERE membership_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, membership_id);

        if(resultSet.next()) {
            String member_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String type = resultSet.getString(3);
            Double monthly_payment = resultSet.getDouble(4);

            return new Membership(member_id,cust_id,type,monthly_payment);
        }
        return null;
    }

    public static boolean delete(String code) throws SQLException {
        try (Connection con = DBConnection.getInstance().getConnection()) {
            String sql = "DELETE FROM membership WHERE membership_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);

            return pstm.executeUpdate() > 0;
        }
    }

    public static Membership searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM membership WHERE membership_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Membership(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }
    public static String generateNextMemberId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT membership_id FROM membership ORDER BY membership_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitMemberId(resultSet.getString(1));
        }
        return splitMemberId(null);
    }
    public static String splitMemberId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("GG0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("GG%04d",+id);
        }
        return "GG0001";
    }
    public static Membership searchBycustomerId(String custId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM membership WHERE customer_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, custId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Membership(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }
}
