package Main;

import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;

public class AppointmentApp extends Application {

    public static User user;
    public static Connection conn = DBConnection.startConnection();
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Started!");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Login.fxml"));
        View_Controller.LoginController controller = new View_Controller.LoginController();
//        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("Appointment Management App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String args[]) throws SQLException {
//        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES (?, ?, ?, ?)";

//        DBQuery.setPrepareStatement(conn, insertStatement); //Create prepared statement
//
//        PreparedStatement ps = DBQuery.getPreparedStatement();
//
//        String countryName;
//        String createDate = "2020-10-13 00:00:00";
//        String createdBy = "admin";
//        String lastUpdateBy = "admin";
//        //Get keyboard input
//        Scanner keyboard = new Scanner(System.in);
//        System.out.print("Enter a country: ");
//        countryName = keyboard.nextLine();
//
//        // Key value mapping
//        ps.setString(1, countryName);
//        ps.setString(2, createDate);
//        ps.setString(3, createdBy);
//        ps.setString(4, lastUpdateBy);
//
//        ps.execute();
//
//        // Check rows affected
//
//        if(ps.getUpdateCount() > 0){
//            System.out.println(ps.getUpdateCount() + " row(s) affected.");
//        } else {
//            System.out.println("No Change!");
//        }

        launch(args);

        DBConnection.closeConnection();
    }
}
