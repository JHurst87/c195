package DAO;

import Main.AppointmentApp;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;
import utils.DBConnection;
import DAO.CustomerDaoImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppointmentDaoImpl {
    public final static Connection conn = AppointmentApp.conn;
    public static Appointment getById(int id) throws SQLException {
        String selectQuery = "SELECT * FROM appointment WHERE appointmentId = ?";

        DBQuery.setPrepareStatement(conn, selectQuery);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ResultSet rs = ps.executeQuery();
        Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setCustomerId(rs.getInt("customerId"));
                appointment.setUserId(rs.getInt("userId"));
                appointment.setTitle(rs.getString("title"));
                appointment.setDescription(rs.getString("description"));
                appointment.setLocation(rs.getString("location"));
                appointment.setContact(rs.getString("contact"));
                appointment.setType(rs.getString("type"));
                appointment.setUrl(rs.getString("url"));
                appointment.setStart(rs.getTimestamp("start").toLocalDateTime());
                appointment.setEnd(rs.getTimestamp("end").toLocalDateTime());
                appointment.setCustomer(CustomerDaoImpl.getById(rs.getInt("customerId")));
        return appointment;
    }

    public static ObservableList<Appointment> getAppointmentsThisMonth() throws SQLException {
        //String selectQuery = "SELECT * FROM appointment WHERE start BETWEEN ? AND  ?";
        ObservableList<Appointment> apptsThisMonth = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM appointment";
        DBQuery.setPrepareStatement(conn, selectQuery);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ResultSet rs = ps.executeQuery();

//        while(rs.next()){
////            Appointment appointment = new Appointment(
////                    rs.getInt("appointmentId"),
////                    rs.getInt("customerId"),
////                    rs.getInt("userId"),
////                    rs.getString("title"),
////                    rs.getString("description"),
////                    rs.getString("location"),
////                    rs.getString("contact"),
////                    rs.getString("type"),
////                    rs.getString("url"),
////                    rs.getString("start"),
////                    rs.getString("end"),
////                    );
//            apptsThisMonth.add(appointment);
//        }

        return apptsThisMonth;
    }

    public static ObservableList<Appointment> getAppointmentsThisWeek() {
        ObservableList<Appointment> apptsThisWeek = FXCollections.observableArrayList();
        return apptsThisWeek;
    }
    
    public static ObservableList<Appointment> getAll(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String query = String.join(" ",
                "SELECT * FROM appointment"
        );

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Appointment rsAppointment = new Appointment();

                rsAppointment.setAppointmentId(rs.getInt("appointmentId"));
                rsAppointment.setTitle(rs.getString("title"));
                rsAppointment.setDescription(rs.getString("description"));
                rsAppointment.setLocation(rs.getString("location"));
                rsAppointment.setContact(rs.getString("contact"));
                rsAppointment.setType(rs.getString("type"));
                rsAppointment.setUrl(rs.getString("url"));

                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime startTimeUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endTimeUTC = rs.getTimestamp("end").toLocalDateTime();
                LocalDateTime startTimeLocal = startTimeUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();
                LocalDateTime endTimeLocal = endTimeUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();

                rsAppointment.setStart(startTimeLocal);
                rsAppointment.setEnd(endTimeLocal);

                rsAppointment.setCustomer(CustomerDaoImpl.getById(rs.getInt("customerId")));

                appointments.add(rsAppointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }

    public static int create(Appointment appointment){
        String[] generatedColumns = {"appointmentId"};
        String query = String.join(" ", "INSERT INTO appointment",
                "(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES",
                "(?, ?, ?, ?, ?, ?, ?, ?, NOW(), 'test', NOW(), 'test')"
        );

        try{
            PreparedStatement ps = conn.prepareStatement(query, generatedColumns);
            ps.setInt(1, appointment.getCustomer().getCustomerId());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getContact());
            ps.setString(6, appointment.getUrl());

            //Set time to UTC/ZULU time
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startTimeUTC = appointment.getStart().atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endTimeUTC = appointment.getEnd().atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

            ps.setTimestamp(7, Timestamp.valueOf(startTimeUTC));
            ps.setTimestamp(8, Timestamp.valueOf(endTimeUTC));

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static int update(Appointment appointment){
        String query = String.join(" ",
                "UPDATE appointment",
                "SET customerId=?,",
                "userId=?,",
                "title=?,",
                "description=?,",
                "location=?,",
                "contact=?,",
                "type=?,",
                "url=?,",
                "start=?,",
                "end=?,",
                "lastUpdate=NOW(),",
                "lastUpdateBy='test'"
        );

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, appointment.getCustomer().getCustomerId());
            ps.setInt(2, appointment.getUserId());
            ps.setString(3, appointment.getTitle());
            ps.setString(4, appointment.getDescription());
            ps.setString(5, appointment.getLocation());
            ps.setString(6, appointment.getContact());
            ps.setString(7, appointment.getType());
            ps.setString(8, appointment.getUrl());

            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startTimeUTC = appointment.getStart().atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endTimeUTC = appointment.getEnd().atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

            ps.setTimestamp(9, Timestamp.valueOf(startTimeUTC));
            ps.setTimestamp(10, Timestamp.valueOf(endTimeUTC));

            int affectedRows = ps.executeUpdate();

            System.out.println("Affected Rows: " + affectedRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public static int delete(Appointment appointment){
        String query = "DELETE FROM appointment WHERE appointmentId = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, appointment.getAppointmentId());

            int deletedRows = ps.executeUpdate();
            System.out.println("Deleted Rows: " + deletedRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
