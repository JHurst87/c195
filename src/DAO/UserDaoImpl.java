package DAO;
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
    static boolean act;
    public static User getUserId(String userName) throws SQLException, Exception{

        //Connect to Database and get this specific user
        return new User();
    }

    public static String getUserNameById(int userId) {
        String selectQuery = "SELECT * FROM user WHERE userId = ?";
        Connection conn = DBConnection.startConnection();
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
}
