package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;
import utils.DBConnection;
import DAO.CustomerDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDaoImpl {
    public static Appointment getAppointmentById(int id) throws SQLException {
        String selectQuery = "SELECT * FROM appointment WHERE appointmentId = ?";
        Connection conn = DBConnection.startConnection();
        DBQuery.setPrepareStatement(conn, selectQuery);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ResultSet rs = ps.executeQuery();
        return new Appointment(
                rs.getInt("appointmentId"),
                rs.getInt("customerId"),
                rs.getInt("userId"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("contact"),
                rs.getString("type"),
                rs.getString("url"),
                rs.getString("start"),
                rs.getString("end"),
                rs.getDate("createDate"),
                rs.getString("createdBy"),
                rs.getTimestamp("lastUpdate"),
                rs.getString("lastUpdateBy"),
                CustomerDaoImpl.getCustomerName(rs.getInt("customerId")),
                UserDaoImpl.getUserNameById(rs.getInt("userId"))
        );
    }

    public static ObservableList<Appointment> getAppointmentsThisMonth() throws SQLException {
        //String selectQuery = "SELECT * FROM appointment WHERE start BETWEEN ? AND  ?";
        ObservableList<Appointment> apptsThisMonth = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM appointment";
        Connection conn = DBConnection.startConnection();
        DBQuery.setPrepareStatement(conn, selectQuery);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Appointment appointment = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    rs.getString("start"),
                    rs.getString("end"),
                    rs.getDate("createDate"),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate"),
                    rs.getString("lastUpdateBy"),
                    CustomerDaoImpl.getCustomerName(rs.getInt("customerId")),
                    UserDaoImpl.getUserNameById(rs.getInt("userId"))
                    );
            apptsThisMonth.add(appointment);
        }

        return apptsThisMonth;
    }

    public static ObservableList<Appointment> getAppointmentsThisWeek() {
        ObservableList<Appointment> apptsThisWeek = FXCollections.observableArrayList();
        return apptsThisWeek;
    }

    public String getCustomerName(int userId){
        return CustomerDaoImpl.getCustomerName(userId);
    }
}
