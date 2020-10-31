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
        launch(args);

        DBConnection.closeConnection();
    }
}
