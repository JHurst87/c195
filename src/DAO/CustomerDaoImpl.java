package DAO;

import DAO.AddressDaoImpl;
import Main.AppointmentApp;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;


public class CustomerDaoImpl {
    public final static Connection conn = AppointmentApp.conn;
    public static ObservableList<Customer> getAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String selectQuery = "SELECT * FROM customer WHERE active = 1";
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                //Create Customer
                System.out.println("Getting customer with id:" + rs.getInt("customerId"));
                Customer customer = new Customer(
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getInt("addressId"),
                    rs.getBoolean("active"),
                    AddressDaoImpl.getById(rs.getInt("addressId"))
                );

                System.out.println("Getting address with id:" + rs.getInt("addressId"));

                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public static String getCustomerName(int customerId){
        try{
            String selectQuery = "SELECT customerName FROM customer WHERE customerId = ?";
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

    public static int create(Customer customer){
        String query = String.join(" ",
                "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES",
                "(?, ?, 1, NOW(), 'test', NOW(), 'test')"
        );

        try{
            String generatedColumns[] = {"customerId"};
            PreparedStatement ps = conn.prepareStatement(query, generatedColumns);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, customer.getAddress().getAddressId());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static Customer getById(int customerId){

        Customer customer = new Customer();
        try{
            String selectQuery = "SELECT customerName FROM customer WHERE customerId = ?";
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setActive(rs.getBoolean("active"));
                return customer;
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return customer;

    }

    public static void delete(Customer customer){
        //Soft Delete a customer from the database
        String query = "UPDATE customer SET active=0 WHERE customerId = ?";

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void update(Customer customer){
        String query = String.join(" ",
                "UPDATE customer",
                "SET customerName=?,",
                "addressId=?",
                "lastUpdate=NOW(),",
                "lastUpdateBy='test'",
                "WHERE customerId=?"
                );

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, customer.getAddress().getAddressId());
            ps.setInt(3, customer.getCustomerId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
