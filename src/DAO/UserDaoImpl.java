package DAO;
import Main.AppointmentApp;
import Model.Appointment;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.*;

public class UserDaoImpl {
    public final static Connection conn = AppointmentApp.conn;
    static boolean act;
    public static User getUserId(String userName) throws SQLException, Exception{

        //Connect to Database and get this specific user
        return new User();
    }

    public static String getUserNameById(int userId) {
        String selectQuery = "SELECT * FROM user WHERE userId = ?";
        try {
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("userName");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    public static ObservableList<User> getAll(){
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE active=1";

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("userName"));
                users.add(user);
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
}
