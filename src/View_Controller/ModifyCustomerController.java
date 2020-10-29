package View_Controller;

import DAO.CountryDaoImpl;
import Model.Country;
import Model.Customer;
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
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private Customer customer;
    private Boolean isNewCustomer = false;

    // GUI Dropdown
    private ObservableList<Country> countries;

    @FXML
    private TextField customerId;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField address2Field;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private ComboBox<Country> countryField;

    @FXML
    private TextField phoneField;

    public ModifyCustomerController(Customer selectedCustomer, boolean isNewCustomer) {
        this.customer = selectedCustomer;
        this.isNewCustomer = isNewCustomer;
        System.out.println("Ran modified constructor!");
        if(isNewCustomer){
            this.customer = new Customer();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.countries = CountryDaoImpl.getAllCountries();
        countryField.setItems(this.countries);
        if(!isNewCustomer){
            System.out.println("Loaded Modify Customer!");
            nameField.setText(this.customer.getCustomerName());
            addressField.setText(this.customer.getAddress().getAddress());
            address2Field.setText(this.customer.getAddress().getAddress2());
            cityField.setText(this.customer.getAddress().getCity().getCityName());
            postalCodeField.setText(this.customer.getAddress().getPostalCode());
            countryField.getSelectionModel().select(this.customer.getAddress().getCity().getCountry());
            System.out.println("Selected CountryId: " + this.customer.getAddress().getCity().getCountry().getCountryId());
            phoneField.setText(this.customer.getAddress().getPhone());
        }
    }

    @FXML
    public void onActionCancel(ActionEvent event){
        System.out.println("Go back to main Customer screen");
        displayMain(event);
    }

    @FXML
    public void onActionSave(ActionEvent event){
        System.out.println("Save!");
        String customerName = nameField.getText();
        String address = addressField.getText();
        String address2 = address2Field.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();
        int country = countryField.getSelectionModel().getSelectedIndex();
        String phone = phoneField.getText();

        if(!isNewCustomer){

        }

    }

    private void displayMain(ActionEvent event){
        try {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Customers.fxml"));
            View_Controller.CustomersController controller = new View_Controller.CustomersController();
            loader.setController(controller);
            Parent root = loader.load();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
