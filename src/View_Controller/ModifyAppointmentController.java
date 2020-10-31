package View_Controller;

import Exception.AppointmentOverlappingException;
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
import java.sql.SQLException;
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
    public Appointment appointment;
    public Appointment oldAppointment;
    public boolean isNewAppointment = false;
    public boolean isModified = false;
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

    public ModifyAppointmentController(Appointment oldAppointment, Boolean isNewAppointment) {
        this.oldAppointment = oldAppointment;
        this.isNewAppointment = isNewAppointment;
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
        apptDescription.setText(this.oldAppointment.getDescription());
        apptTitle.setText(this.oldAppointment.getTitle());
        apptContact.setText(this.oldAppointment.getContact());
        apptURL.setText(this.oldAppointment.getUrl());
        apptLocation.setText(this.oldAppointment.getLocation());
        apptDate.setValue(LocalDate.now());
        apptTypeComboBox.setItems(types);

        //Time

        apptStartHours.setItems(hours);
        apptStartHours.getSelectionModel().selectLast();

        apptStartMinutes.setItems(minutes);
        apptStartMinutes.getSelectionModel().selectFirst();

        apptStartAMPM.setItems(amPm);
        apptStartAMPM.getSelectionModel().selectFirst();

        apptEndHours.setItems(hours);
        apptEndHours.getSelectionModel().selectLast();

        apptEndMinutes.setItems(minutes);
        apptEndMinutes.getSelectionModel().selectFirst();

        apptEndAMPM.setItems(amPm);
        apptEndAMPM.getSelectionModel().selectFirst();

        if(!isNewAppointment){
            apptCustomerComboBox.getSelectionModel().select(this.oldAppointment.getCustomer());
            apptDate.setValue(this.oldAppointment.getStart().toLocalDate());
            apptContact.setText(this.oldAppointment.getContact());
            apptTypeComboBox.getSelectionModel().select(this.oldAppointment.getType());

            System.out.println("Start: " + this.oldAppointment.getStart().format(appointmentTimeFormat));
            apptStartHours.getSelectionModel().select((this.oldAppointment.getStart().format(appointmentHourFormatter)));
            apptStartMinutes.getSelectionModel().select("" + this.oldAppointment.getStart().format(appointmentMinuteFormatter));
            apptStartAMPM.getSelectionModel().select(this.oldAppointment.getStart().getHour() < 12 ? "AM":"PM");

            apptEndHours.getSelectionModel().select((this.oldAppointment.getEnd().format(appointmentHourFormatter)));
            apptEndMinutes.getSelectionModel().select("" + this.oldAppointment.getEnd().format(appointmentMinuteFormatter));
            apptEndAMPM.getSelectionModel().select(this.oldAppointment.getEnd().getHour() < 12 ? "AM":"PM");

            System.out.println("End: " + this.oldAppointment.getEnd().format(appointmentTimeFormat));
        }
    }

    public void onActionCancel(ActionEvent event) throws IOException {
        View_Controller.AppointmentsController controller = new AppointmentsController();
        showScreen("/View_Controller/Appointments.fxml", "Appointments", event, controller);
    }

    public void onActionSave(ActionEvent event) throws IOException {
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

            Appointment modifiedAppointment = new Appointment();
            modifiedAppointment.setUserId(AppointmentApp.user.getUserId());
            modifiedAppointment.setCustomer(apptCustomerComboBox.getValue());
            modifiedAppointment.setContact(apptContact.getText());
            modifiedAppointment.setTitle(apptTitle.getText());
            modifiedAppointment.setDescription(apptDescription.getText());
            modifiedAppointment.setLocation(apptLocation.getText());
            modifiedAppointment.setType(apptTypeComboBox.getValue());
            modifiedAppointment.setUrl(apptURL.getText());
            modifiedAppointment.setStart(newStartDateTime);
            modifiedAppointment.setEnd(newEndDateTime);

            if(!isNewAppointment){
                //Update Existing appointment
                if(modifiedAppointment.isValid() && isValidOverlappingAppointments(this.oldAppointment)){
                    modifiedAppointment.setAppointmentId(this.oldAppointment.getAppointmentId());
                    AppointmentDaoImpl.update(modifiedAppointment);
                    View_Controller.AppointmentsController controller = new AppointmentsController();
                    showScreen("/View_Controller/Appointments.fxml", "Appointments", event, controller);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid Appointment");
                    alert.show();
                }
            } else {
                if(modifiedAppointment.isValid() && isValidOverlappingAppointments(modifiedAppointment)){
                    modifiedAppointment.setAppointmentId(AppointmentDaoImpl.create(modifiedAppointment));
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
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
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

    private boolean isValidOverlappingAppointments(Appointment oldAppointment) throws SQLException, AppointmentOverlappingException {
        //Get overlapping appointments
        ObservableList<Appointment> overLappingAppointments = oldAppointment.getOverlappingAppointments();
        //Process them 1 at a time
        for(Appointment overLapAppt : overLappingAppointments){
            //Proceed if the IDs match
            if(overLapAppt.getAppointmentId() != oldAppointment.getAppointmentId()){
                //Throw exception only iff the IDs don't match
                throw new AppointmentOverlappingException("The requested time has a conflicting appointment.");
            }
        }

        //Return true if valid
        return true;
    }
}
