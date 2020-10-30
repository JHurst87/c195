package View_Controller;

import DAO.CustomerDaoImpl;
import Model.*;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable{
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private ObservableList<Appointment> appointments;
    private Appointment selectedApppointment;
    private DateTimeFormatter appointmentTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    public String currentView = "monthly";

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
        appointments = AppointmentDaoImpl.getAll();
        // Set values using a Lambda function to help display strings from the embedded classes/properties
        customerNameCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getCustomer().getCustomerName()));
        titleCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getTitle()));
        descriptionCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getDescription()));
        typeCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getType()));
        startCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getStart().format(appointmentTimeFormat)));
        endCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getEnd().format(appointmentTimeFormat)));
        appointmentsTableView.setItems(appointments);
    }

    public void onActionAdd(ActionEvent event){
        System.out.println("Add Appointment");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddAppointment.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle("Add Appointment(s)");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionEdit(ActionEvent event){
        System.out.println("Modify Appointment");
        selectedApppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if(selectedApppointment == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Selection Error");
            alert.setContentText("Please select a valid Appointment");
            alert.show();
        } else {
            ModifyAppointmentController controller = new ModifyAppointmentController(this.selectedApppointment, false);
            displayModifyAppointment("/View_Controller/ModifyAppointment.fxml", "Modify Appointment", event, controller);
        }
    }

    public void onActionDelete(ActionEvent event){
        System.out.println("Delete Appointment");

        //Soft Delete

        try{
            selectedApppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
            if( selectedApppointment == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a valid Appointment");
                alert.setTitle("Selection Error");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Part?");
                alert.setContentText("Delete appointment with " + selectedApppointment.getCustomer() +
                        " starting at "+ selectedApppointment.getStart().format(appointmentTimeFormat) + " ?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.isPresent() && option.get() == ButtonType.OK) {
                    AppointmentDaoImpl.delete(selectedApppointment);
                    System.out.println("Delete and update view!");
                    setAppointmentTableView();
                }
            }
        }
        catch(Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("No Customer Selected Error");
            alert.show();
        }

    }

    private void setAppointmentTableView() {
        ObservableList<Appointment> appointments = AppointmentDaoImpl.getAll();
        appointmentsTableView.setItems(appointments);
    }

    public void onActionReturn(ActionEvent event){
        System.out.println("Return to Main Menu");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
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

    public void displayModifyAppointment(String resource, String title, ActionEvent event, Object controller){
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
