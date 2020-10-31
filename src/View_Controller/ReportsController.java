package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;

    public ReportsController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onActionAppointmentsType(ActionEvent event){
        View_Controller.ReportAppointmentTypesController controller = new ReportAppointmentTypesController();
        displayStage("/View_Controller/ReportAppointmentTypes.fxml", "Appointments By Type", event, controller);
    }

    public void onActionSchedule(ActionEvent event){
        View_Controller.ReportScheduleByConsultantController controller = new ReportScheduleByConsultantController();
        displayStage("/View_Controller/ReportScheduleByConsultant.fxml", "Consultant Schedule", event, controller);
    }

    public void onActionAppointmentsByCustomer(ActionEvent event){
        View_Controller.ReportAppointmentsByCustomer controller = new ReportAppointmentsByCustomer();
        displayStage("/View_Controller/ReportAppointmentsByCustomer.fxml", "Appointments By Customer", event, controller);
    }

    public void onActionReturn(ActionEvent event){
        View_Controller.MainScreenController controller = new MainScreenController();
        displayStage("/View_Controller/MainScreen.fxml", "Appointments", event, controller);
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
