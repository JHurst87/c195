package View_Controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableColumn<Customer, String> address1Col;
    @FXML private TableColumn<Customer, String> address2Col;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, String> postalCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load Customers
        ObservableList<Customer> customers = CustomerDaoImpl.getAll();
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address1Col.setCellValueFactory(new PropertyValueFactory<>("address1"));
        address2Col.setCellValueFactory(new PropertyValueFactory<>("address2"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
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
