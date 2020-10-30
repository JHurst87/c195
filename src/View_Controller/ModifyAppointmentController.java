package View_Controller;

import Exception.AppointmentTimeException;
import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import Main.AppointmentApp;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
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
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private Appointment appointment;
    private Boolean isNewAppointment = false;
    private ObservableList<Customer> customers;
    private ObservableList<User> users;
    private DateTimeFormatter appointmentTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private DateTimeFormatter appointmentTimeFullFormat = DateTimeFormatter.ofPattern("hh:mm a");
    private DateTimeFormatter appointmentHourFormatter = DateTimeFormatter.ofPattern("hh");
    private DateTimeFormatter appointmentMinuteFormatter = DateTimeFormatter.ofPattern("mm");
    private ObservableList<String> hours;
    private ObservableList<String> minutes;
    private ObservableList<String> amPm = FXCollections.observableArrayList("AM", "PM");
    private ObservableList<String> types = FXCollections.observableArrayList("Initial", "Follow-Up", "Final");

    @FXML
    private ComboBox<Customer> apptCustomerComboBox;
    @FXML
    private TextField apptDescription;
    @FXML
    private TextField apptTitle;
    @FXML
    private TextField apptContact;
    @FXML
    private ComboBox<String> apptTypeComboBox;
    @FXML
    private TextField apptURL;
    @FXML
    private TextField apptLocation;
    @FXML
    private DatePicker apptDate;
    @FXML
    private ComboBox<String> apptStartHours;
    @FXML
    private ComboBox<String> apptStartMinutes;
    @FXML
    private ComboBox<String> apptStartAMPM;
    @FXML
    private ComboBox<String> apptEndHours;
    @FXML
    private ComboBox<String> apptEndMinutes;
    @FXML
    private ComboBox<String> apptEndAMPM;

    public ModifyAppointmentController(Appointment appointment, Boolean isNewAppointment) {
        this.appointment = appointment;
        this.isNewAppointment = isNewAppointment;
        System.out.println("Ran modified Appointment constructor!");
        if (isNewAppointment) {
            this.appointment = new Appointment();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.customers = CustomerDaoImpl.getAll();
        this.users = UserDaoImpl.getAll();

        this.hours = FXCollections.observableArrayList(
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );

        this.minutes = FXCollections.observableArrayList(
                "00", "15", "30", "45"
        );

        apptCustomerComboBox.setItems(this.customers);
        apptDescription.setText(this.appointment.getDescription());
        apptTitle.setText(this.appointment.getTitle());
        apptContact.setText(this.appointment.getContact());
        apptURL.setText(this.appointment.getUrl());
        apptLocation.setText(this.appointment.getLocation());
        apptDate.setValue(LocalDate.now());
        apptTypeComboBox.setItems(types);

        //Time

        apptStartHours.setItems(hours);
        apptStartMinutes.setItems(minutes);
        apptStartAMPM.setItems(amPm);

        apptEndHours.setItems(hours);
        apptEndMinutes.setItems(minutes);
        apptEndAMPM.setItems(amPm);

        if(!isNewAppointment){
            apptCustomerComboBox.getSelectionModel().select(this.appointment.getCustomer());
            apptDate.setValue(this.appointment.getStart().toLocalDate());
            apptContact.setText(this.appointment.getContact());
            apptTypeComboBox.getSelectionModel().select(this.appointment.getType());

            System.out.println("Start: " + this.appointment.getStart().format(appointmentTimeFormat));
            apptStartHours.getSelectionModel().select((this.appointment.getStart().format(appointmentHourFormatter)));
            apptStartMinutes.getSelectionModel().select("" + this.appointment.getStart().format(appointmentMinuteFormatter));
            apptStartAMPM.getSelectionModel().select(this.appointment.getStart().getHour() < 12 ? "AM":"PM");

            apptEndHours.getSelectionModel().select((this.appointment.getEnd().format(appointmentHourFormatter)));
            apptEndMinutes.getSelectionModel().select("" + this.appointment.getEnd().format(appointmentMinuteFormatter));
            apptEndAMPM.getSelectionModel().select(this.appointment.getEnd().getHour() < 12 ? "AM":"PM");

            System.out.println("End: " + this.appointment.getEnd().format(appointmentTimeFormat));
        }
    }

    public void onActionCancel(ActionEvent event) throws IOException {
        System.out.println("Go Back to Appointments");
        View_Controller.AppointmentsController controller = new AppointmentsController();
        showScreen("/View_Controller/Appointments.fxml", "Appointments", event, controller);
    }

    public void onActionSave(ActionEvent event) throws IOException {
        System.out.println("Save/Edit Appointment");
        try{
            Customer newCustomer = apptCustomerComboBox.getValue();
            String newLocation = apptLocation.getText();
            String newTitle = apptTitle.getText();
            String newURL = apptURL.getText();
            String newContact = apptContact.getText();
            String newDesc = apptDescription.getText();

            //Create DateTime Objects

            LocalDate newDate = apptDate.getValue();

            String newStartHours = apptStartHours.getValue();
            String newStartMinutes = apptStartMinutes.getValue();
            String newStartAMPM = apptStartAMPM.getValue();

            String newStartTime = newStartHours + ":" + newStartMinutes + " " + newStartAMPM;

            String newEndHours = apptEndHours.getValue();
            String newEndMinutes = apptEndMinutes.getValue();
            String newEndAMPM = apptEndAMPM.getValue();

            String newEndTime = newEndHours + ":" + newEndMinutes + " " + newEndAMPM;

            if(newDate == null || newStartTime.isEmpty() || newEndTime.isEmpty() ){
                throw new AppointmentTimeException("Please select a valid date and time");
            }

            LocalDateTime newStartDateTime = LocalDateTime.of(newDate, LocalTime.parse(newStartTime, appointmentTimeFullFormat));
            LocalDateTime newEndDateTime = LocalDateTime.of(newDate, LocalTime.parse(newEndTime, appointmentTimeFullFormat));

            Appointment appointment = new Appointment();
            appointment.setUserId(AppointmentApp.user.getUserId());
            appointment.setCustomer(apptCustomerComboBox.getValue());
            appointment.setContact(apptContact.getText());
            appointment.setTitle(apptTitle.getText());
            appointment.setDescription(apptDescription.getText());
            appointment.setLocation(apptLocation.getText());
            appointment.setType(apptTypeComboBox.getValue());
            appointment.setUrl(apptURL.getText());
            appointment.setStart(newStartDateTime);
            appointment.setEnd(newEndDateTime);

            if(!isNewAppointment){
                //Update Existing appointment
                if(appointment.isValid()){
                    appointment.setAppointmentId(this.appointment.getAppointmentId());
                    AppointmentDaoImpl.update(appointment);
                    View_Controller.AppointmentsController controller = new AppointmentsController();
                    showScreen("/View_Controller/Appointments.fxml", "Appointments", event, controller);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid Appointment");
                    alert.show();
                }
            } else {

                if(appointment.isValid()){
                    appointment.setAppointmentId(AppointmentDaoImpl.create(appointment));
                    View_Controller.AppointmentsController controller = new AppointmentsController();
                    showScreen("/View_Controller/Appointments.fxml", "Appointments", event, controller);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid Appointment");
                    alert.show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void showScreen(String resource, String title, ActionEvent event, Object controller) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private boolean isValid(){

        return true;
    }

    private void setUserInput(){

    }


}
