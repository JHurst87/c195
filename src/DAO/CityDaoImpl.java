package DAO;

import Model.City;
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

    public City getCity(int cityId){
        return new City();
    }
}
