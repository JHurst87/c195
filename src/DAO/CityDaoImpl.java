package DAO;

import Model.City;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDaoImpl {
    public CityDaoImpl(){
    }

    public int getNextId(){
        String nextIdQuery = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'U06exx' AND TABLE_NAME = 'city';";

        try {
            Connection conn = DBConnection.startConnection();
            DBQuery.setPrepareStatement(conn, nextIdQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;//Not found
    }

    public static City getCity(int cityId){
        City city = new City();
        String query = "SELECT * FROM city WHERE cityId = ?";
        try {
            Connection conn = DBConnection.startConnection();
            DBQuery.setPrepareStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //System.out.println("Got the city of: " + rs.getString("city"));
                System.out.println(rs.getInt("cityId"));
                System.out.println(rs.getString("city"));
                System.out.println(rs.getInt("countryId"));
                city.setCityId(rs.getInt("cityId"));
                city.setCityName(rs.getString("city"));
                city.setCountry(CountryDaoImpl.getById(rs.getInt("countryId")));
                return city;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return city;
    }
}
