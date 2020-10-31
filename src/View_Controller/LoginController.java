package View_Controller;

import DAO.AppointmentDaoImpl;
import DAO.UserDaoImpl;
import Main.AppointmentApp;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import utils.DBConnection;
import utils.DBQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoginController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private ResourceBundle activeLanguage;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private UserDaoImpl user;

    public final static Connection conn = AppointmentApp.conn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeLanguage = ResourceBundle.getBundle("i18n/rb");
        loginLabel.setText(activeLanguage.getString("login"));
        loginButton.setText(activeLanguage.getString("login"));
    }

    public LoginController() {

    }

    @FXML
    void onActionLogin(ActionEvent event){
        System.out.println("Login!");

        String selectQuery = "SELECT * FROM user WHERE userName = ? AND password = ?";
        try {
            DBQuery.setPrepareStatement(conn, selectQuery);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, usernameField.getText());
            ps.setString(2, passwordField.getText());

            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getInt("userID") > 0){
                //Get User

                AppointmentApp.user = new User(1, "test");

                checkAppointments();
                logger(AppointmentApp.user.getName(), LocalDateTime.now());
                // Change stage
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
                loader.setController(controller);
                Parent root = loader.load();
                stage.setTitle("Appointment App");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(activeLanguage.getString("loginErrorLabel"));
                alert.setContentText(activeLanguage.getString("loginError"));
                alert.show();
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            DBConnection.closeConnection();
        }
    }

    private void logger(String name, LocalDateTime time) {
        Logger log = Logger.getLogger("user_sign_ins.txt");

        try{
            FileHandler fh = new FileHandler("user_sign_ins.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);

            log.info("User Login: " + name + " Time: " + time );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAppointments() {
        ObservableList<Appointment> appointments = AppointmentDaoImpl.getAll();

        //Check if any upcoming appointments are within the 15 minute login window using a Lambda function

        LocalDateTime now = LocalDateTime.now();
        appointments.forEach( (appointment -> {
            long timeDifference = ChronoUnit.MINUTES.between(now, appointment.getStart());
            if(timeDifference >= 0 && timeDifference <= 15){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have an appointment within the next 15 minutes!");
                alert.setTitle("Upcoming Appointment!");
                alert.show();
            }
        }));
    }
}
