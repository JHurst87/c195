package DAO;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;


public class CustomerDaoImpl {

    public static ObservableList<Customer> getAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String selectQuery = "SELECT * FROM customer";
            Connection conn = DBConnection.startConnection();
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                //Create Customer
                Customer customer = new Customer(
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getInt("addressId"),
                    rs.getBoolean("active"),
                    rs.getTimestamp("createDate"),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate"),
                    rs.getString("lastUpdateBy")
                );

                customers.add(customer);
            }
            return customers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;



    }

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
