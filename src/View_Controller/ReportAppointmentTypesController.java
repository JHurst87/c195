package View_Controller;

import DAO.AppointmentDaoImpl;
import Model.Appointment;
import Model.AppointmentReport;
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
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.*;

public class ReportAppointmentTypesController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;

    public YearMonth theMonth;

    private ObservableList<Appointment> appointments;
    private ObservableList<AppointmentReport> appointmentReports;


    @FXML TableView<AppointmentReport> reportTableView = new TableView<AppointmentReport>();
    @FXML TableColumn<AppointmentReport, String> monthCol = new TableColumn<AppointmentReport, String>();
    @FXML TableColumn<AppointmentReport, String> typeCol = new TableColumn<AppointmentReport, String>();
    @FXML TableColumn<AppointmentReport, String> countCol = new TableColumn<AppointmentReport, String>();

    //private
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        theMonth = YearMonth.now();

        try {
            reportView();
            setValues();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ReportAppointmentTypesController() {

    }

    private void setValues(){
        // Set values using a Lambda function to help display strings from the embedded classes/properties
        monthCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getMonth()));
        typeCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getType()));
        countCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getCount()));
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

    private void reportView() throws SQLException {
        try{
            appointmentReports = AppointmentDaoImpl.getAppointmentTypeReport();
            setReportsTableView(appointmentReports);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setReportsTableView(ObservableList<AppointmentReport> appointmentReports) {
        reportTableView.setItems(appointmentReports);
    }
}
