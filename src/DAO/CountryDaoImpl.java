package DAO;

import Main.AppointmentApp;
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

public class CountryDaoImpl {
    public final static Connection conn = AppointmentApp.conn;
    public final static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM country";
        try {
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                //Create Country
                Country country = new Country(
                        rs.getInt("countryId"),
                        rs.getString("country")
                );

                countries.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

    public static Country getById(int countryId){

        Country country = null;
        String query = "SELECT * FROM country WHERE countryId = ?";
        try {
            DBQuery.setPrepareStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println(rs.getInt("countryId"));
                System.out.println(rs.getString("country"));
                country =  new Country(
                        rs.getInt("countryId"),
                        rs.getString("country")
                );
                return country;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;

    }
}
