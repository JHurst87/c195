package DAO;

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

    public final static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM country";
        try {
            Connection conn = DBConnection.startConnection();
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
}
