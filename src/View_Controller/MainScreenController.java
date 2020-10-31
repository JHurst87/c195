package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;

    @FXML
    private Button appointmentsBtn;
    @FXML
    private Button customersBtn;
    @FXML
    private Button reportsBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    public void onActionAppointments(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Appointments.fxml"));
        View_Controller.AppointmentsController controller = new AppointmentsController();
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle("Appointments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onActionCustomers(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Customers.fxml"));
        try {
            View_Controller.CustomersController controller = new View_Controller.CustomersController();
            loader.setController(controller);
            Parent root = loader.load();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onActionReports(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Reports.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle("Reports");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onActionLogout(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public MainScreenController() {

    }


}
