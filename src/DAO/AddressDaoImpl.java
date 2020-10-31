package DAO;

import Main.AppointmentApp;
import Model.Address;
import Model.City;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.AppointmentApp.*;

public class AddressDaoImpl{
    public final static Connection conn = AppointmentApp.conn;
    public static Address getById(int addressId){

        Address address = new Address();

        String query = "SELECT * FROM address WHERE addressId = ?";
        try {
            DBQuery.setPrepareStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                address.setAddressId(rs.getInt("addressId"));
                address.setAddress(rs.getString("address"));
                address.setAddress2(rs.getString("address2"));
                address.setCity(CityDaoImpl.getCity(rs.getInt("cityId")));
                address.setPostalCode(rs.getString("postalCode"));
                address.setPhone(rs.getString("phone"));

                return address;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }

    public static int create(Address address){
        String query = String.join(" ", "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, ?, ?, NOW(), 'test', NOW(), 'test')"
        );

        try{
            String generatedColumns[] = {"addressId"};
            PreparedStatement ps = conn.prepareStatement(query, generatedColumns);
            ps.setString(1, address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3, address.getCity().getCityId());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public static void update(Address address){
        String query = String.join(" ",
                "UPDATE address",
                "SET address=?,",
                "address2=?,",
                "cityId=?,",
                "postalCode=?,",
                "phone=?,",
                "lastUpdate=NOW(),",
                "lastUpdateBy='test'",
                "WHERE addressId=?"
                );

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3, address.getCity().getCityId());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.setInt(6, address.getAddressId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
