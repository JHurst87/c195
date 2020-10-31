package DAO;

import Main.AppointmentApp;
import Model.City;
import Model.Country;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDaoImpl {
    public final static Connection conn = AppointmentApp.conn;
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

    public static int create(City city){
        String query = String.join(" ","INSERT INTO city",
                "(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, NOW(), 'test', NOW(), 'test')"
        );

        try{
            String generatedColumns[] = {"cityId"};
            PreparedStatement ps = conn.prepareStatement(query, generatedColumns);
            ps.setString(1, city.getCityName());
            ps.setInt(2, city.getCountry().getCountryId());
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

    public static void update(City city, Country country){
        String query = String.join(" ",
                "UPDATE city",
                "SET city=?,",
                "countryId=?,",
                "lastUpdate=NOW(),",
                "lastUpdateBy='test'",
                "WHERE cityId=?"
        );
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, city.getCityName());
            ps.setInt(2, city.getCountry().getCountryId());
            ps.setInt(3, city.getCityId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
