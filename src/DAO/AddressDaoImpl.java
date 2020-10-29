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
}
