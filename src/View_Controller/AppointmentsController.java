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
import javafx.stage.*;
import DAO.AppointmentDaoImpl;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
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
    public YearMonth theMonth;
    public LocalDate theWeek;
    public static final int DAYS_IN_WEEK = 7;


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


        theMonth = YearMonth.now();

        LocalDate now = LocalDate.now();
        int weekOffset = now.getDayOfWeek().getValue() % DAYS_IN_WEEK;

        theWeek = now.minusDays(weekOffset);

        setValues();
        thisMonth(); //Load Appointments by Month View (default)
    }

    public void onActionThisMonth(ActionEvent event){
        // Show this month's appointments from day 1 to day 30/31/28 depending on the month
        thisMonth();
    }

    private void thisWeek(){
        LocalDate lastDayOfNextWeek = theWeek.plusWeeks(1);
        LocalDateTime startWKDateTime = LocalDateTime.of(this.theWeek, LocalTime.MIDNIGHT);//Start of this week
        LocalDateTime endWKDateTime = LocalDateTime.of(lastDayOfNextWeek, LocalTime.MAX); //End of next week

        appointments = AppointmentDaoImpl.getByDateRange(startWKDateTime, endWKDateTime);
        setAppointmentsTableView(appointments);
    }

    private void thisMonth(){
        LocalDate firstDay = theMonth.atDay(1);
        LocalDate lastDay  = theMonth.atEndOfMonth();
        LocalDateTime startDT = LocalDateTime.of(firstDay, LocalTime.MIDNIGHT); // First day of the target month
        LocalDateTime endDT = LocalDateTime.of(lastDay,LocalTime.MAX);//End of the Day for that month

        appointments = AppointmentDaoImpl.getByDateRange(startDT, endDT);
        setAppointmentsTableView(appointments);
    }

    private void showAll() {
        appointments = AppointmentDaoImpl.getAll();
        setAppointmentsTableView(appointments);
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

    private void setAppointmentsTableView(ObservableList<Appointment> appointments){
        appointmentsTableView.setItems(appointments);
        //appointmentsTableView.refresh();
    }

    public void onActionThisWeek(ActionEvent event){
        System.out.println("Show Only This week's appointments!");
        thisWeek();
    }

    public void onActionShowAll(ActionEvent event){
        System.out.println("Show All appointments!");
        showAll();
    }

    public void onActionAdd(ActionEvent event){
        System.out.println("Add Appointment");
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
