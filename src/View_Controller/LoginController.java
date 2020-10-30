package View_Controller;

import DAO.UserDaoImpl;
import Main.AppointmentApp;
import Model.User;
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
import java.util.ResourceBundle;

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
        usernameField.setText(activeLanguage.getString("username"));
        passwordField.setText(activeLanguage.getString("password"));
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
}
