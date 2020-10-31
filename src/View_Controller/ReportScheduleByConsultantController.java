package View_Controller;

import DAO.AppointmentDaoImpl;
import DAO.UserDaoImpl;
import Model.Appointment;
import Model.Customer;
import Model.User;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ReportScheduleByConsultantController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;

    private ObservableList<Appointment> appointments;
    private DateTimeFormatter appointmentTimeFormat = java.time.format.DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private ObservableList<User> users;

    @FXML private TableView<Appointment> appointmentsTableView = new TableView<Appointment>();
    @FXML private TableColumn<Appointment, String> customerNameCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> titleCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> descriptionCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> typeCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> startCol = new TableColumn<Appointment, String>();
    @FXML private TableColumn<Appointment, String> endCol = new TableColumn<Appointment, String>();
    @FXML private ComboBox<User> userComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.users = UserDaoImpl.getAll();
        setValues();
        userComboBox.setItems(this.users);
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

    public void onActionUpdate(ActionEvent event){
        User user = userComboBox.getSelectionModel().getSelectedItem();
        System.out.println("userId: " + user.getUserId());
        appointments = AppointmentDaoImpl.getByUserId(user.getUserId());
        setReportsTableView(appointments);
    }

    private void setReportsTableView(ObservableList<Appointment> appointments) {
        appointmentsTableView.setItems(appointments);
    }

    public void onActionReturn(ActionEvent event){
        System.out.println("Return to Main Menu");
        View_Controller.MainScreenController controller = new MainScreenController();
        displayStage("/View_Controller/MainScreen.fxml", "Appointment App", event, controller);
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
