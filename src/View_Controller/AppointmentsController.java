package View_Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import utils.DBConnection;
import DAO.AppointmentDaoImpl;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable{
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private ObservableList<Appointment> appointments;

    //Appointments

    @FXML private TableView<Appointment> appointmentsTableView = new TableView<Appointment>();
    @FXML private TableColumn<Appointment, String> customerNameCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> titleCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> descriptionCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> typeCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> startCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> endCol = new TableColumn<Appointment, String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load Appointments by Month View (default)
        try {
            appointments = AppointmentDaoImpl.getAppointmentsThisMonth();
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            appointmentsTableView.setItems(appointments);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onActionReturn(ActionEvent event){
        System.out.println("Return to Main Menu");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
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
}
