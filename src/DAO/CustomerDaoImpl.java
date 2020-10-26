package DAO;

import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDaoImpl {

    public static String getCustomerName(int customerId){
        try{
            DBConnection.startConnection();
            String selectQuery = "SELECT customerName FROM customer WHERE customerId = ?";
            Connection conn = DBConnection.startConnection();
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("customerName");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
