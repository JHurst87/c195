package DAO;

import Model.Address;
import Model.City;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDaoImpl implements AddressDaoImplInterface{
    private static Address address;
    public static Address getById(int addressId){

        address = new Address();

        Connection conn = DBConnection.startConnection();
        String query = "SELECT * FROM address WHERE addressId = ?";
        try {
            DBQuery.setPrepareStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();

            CityDaoImpl city = new CityDaoImpl();
            address = new Address(
                    rs.getInt("addressId"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    city.getCity(rs.getInt("cityId")),
                    rs.getString("postalCode"),
                    rs.getString("phone")
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }
}
