package View_Controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Address;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.*;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomersController implements Initializable{
    private Stage stage;
    private Parent scene;
    private Alert alert;

    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> address2Col;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, String> postalCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load Customers
        ObservableList<Customer> customers = CustomerDaoImpl.getAll();

        customerNameCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getCustomerName()));
        addressCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getAddress()));
        address2Col.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getAddress2()));
        phoneCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getPhone()));
        cityCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getCity().getCityName()));
        postalCode.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getPostalCode()));
        customerTableView.setItems(customers);
    }

    public void onActionReturn(ActionEvent event){
        System.out.println("Return to Main Menu");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
