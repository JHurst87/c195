package View_Controller;

import DAO.AppointmentDaoImpl;
import Model.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsWeeklyController implements Initializable {

    private Stage stage;
    private Parent scene;
    private Alert alert;
    private ObservableList<Appointment> appointments;
    private Appointment selectedApppointment;
    private DateTimeFormatter appointmentTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    public LocalDate theWeek;
    public static final int DAYS_IN_WEEK = 7;

    //Week Label
    @FXML
    private Label weekLabel;

    //Appointments

    @FXML private TableView<Appointment> appointmentsTableView = new TableView<Appointment>();
    @FXML private TableColumn<Appointment, String> customerNameCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> titleCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> descriptionCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> typeCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> startCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> endCol = new TableColumn<Appointment, String>();

    public AppointmentsWeeklyController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate now = LocalDate.now();
        int weekOffset = now.getDayOfWeek().getValue() % DAYS_IN_WEEK;

        theWeek = now.minusDays(weekOffset);

        setValues();
        weeklyView(); //Load Appointments by Month View (default)
    }

    private void weeklyView(){
        weekLabel.setText("Week " + theWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        LocalDate lastDay = theWeek.plusWeeks(1);
        LocalDateTime start = LocalDateTime.of(theWeek, LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(lastDay, LocalTime.MAX);
        appointments = AppointmentDaoImpl.getByDateRange(start, end);
        setAppointmentsTableView(appointments);
    }

    private void setAppointmentsTableView(ObservableList<Appointment> appointments){
        appointmentsTableView.setItems(appointments);
    }

    public void onActionPrevWeek(ActionEvent event){
        theWeek = theWeek.minusWeeks(1);
        weeklyView();
    }

    public void onActionNextWeek(ActionEvent event){
        theWeek = theWeek.plusWeeks(1);
        weeklyView();
    }

    public void onActionAdd(ActionEvent event){
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyAppointment.fxml"));
        View_Controller.ModifyAppointmentController controller = new ModifyAppointmentController(new Appointment(), true);
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle("Add Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionEdit(ActionEvent event){
        selectedApppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if(selectedApppointment == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Selection Error");
            alert.setContentText("Please select a valid Appointment");
            alert.show();
        } else {
            ModifyAppointmentController controller = new ModifyAppointmentController(this.selectedApppointment, false);
            displayStage("/View_Controller/ModifyAppointment.fxml", "Modify Appointment", event, controller);
        }
    }

    public void onActionDelete(ActionEvent event){
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
                    weeklyView();
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

    public void onActionReturn(ActionEvent event){
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

    public void onActionMonthly(ActionEvent event){
        AppointmentsController controller = new AppointmentsController();
        displayStage("/View_Controller/Appointments.fxml", "Monthly Appointments", event, controller);
    }

    private void setValues(){
        // Set values using a Lambda function to help display strings from the embedded classes/properties
        customerNameCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getCustomer().getCustomerName()));
        titleCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getTitle()));
        descriptionCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getDescription()));
        typeCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getType()));
        startCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getStart().format(appointmentTimeFormat)));
        endCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getEnd().format(appointmentTimeFormat)));
    }

    public void displayStage(String resource, String title, ActionEvent event, Object controller){
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
