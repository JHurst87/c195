package View_Controller;

import DAO.AddressDaoImpl;
import DAO.CityDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Country;
import Model.Customer;
import Model.Address;
import Model.City;
import Exception.CityException;
import Exception.CustomerException;
import Exception.AddressException;

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
        if (isNewCustomer) {
            this.customer = new Customer();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.countries = CountryDaoImpl.getAllCountries();
        countryField.setItems(this.countries);
        if (!isNewCustomer) {
            System.out.println("Loaded Modify Customer!");
            nameField.setText(this.customer.getCustomerName());
            addressField.setText(this.customer.getAddress().getAddress());
            address2Field.setText(this.customer.getAddress().getAddress2());
            cityField.setText(this.customer.getAddress().getCity().getCityName());
            postalCodeField.setText(this.customer.getAddress().getPostalCode());
            countryField.getSelectionModel().select(this.customer.getAddress().getCity().getCountry());
            phoneField.setText(this.customer.getAddress().getPhone());
        }
    }

    @FXML
    public void onActionCancel(ActionEvent event) {
        System.out.println("Go back to main Customer screen");
        displayCustomerScreen(event);
    }

    @FXML
    public void onActionSave(ActionEvent event) throws CityException, CustomerException, AddressException {

        // GUI values
        String customerNameText = nameField.getText();
        String addressText = addressField.getText();
        String address2Text = address2Field.getText();
        String cityText = cityField.getText();
        String postalCodeText = postalCodeField.getText();
        Country modifiedCountry = countryField.getSelectionModel().getSelectedItem();
        String phoneText = phoneField.getText();

        // Detect if new customer or Existing customer
        if (!isNewCustomer) {
            try {

                Address oldAddress = this.customer.getAddress();
                City oldCity = oldAddress.getCity();
                City updatedCity = new City();
                updatedCity.setCityId(oldCity.getCityId());
                updatedCity.setCityName(oldCity.getCityName());
                updatedCity.setCountry(modifiedCountry);
                Address updatedAddress = new Address();
                if (updatedCity.isValid()) {
                    updatedAddress.setAddressId(oldAddress.getAddressId());
                    updatedAddress.setAddress(addressText);
                    updatedAddress.setAddress2(address2Text);
                    updatedAddress.setCity(updatedCity);
                    updatedAddress.setPostalCode(postalCodeText);
                    updatedAddress.setPhone(phoneText);

                    AddressDaoImpl.update(updatedAddress); //Update the validated address information
                }
                if (updatedAddress.isValid()) {
                    //Display
                    //Customer customer = modifyCustomerHandler(this.customer.getCustomerId(), customerNameText, updatedAddress);
                    Customer customer = new Customer();
                    customer.setCustomerId(this.customer.getCustomerId());
                    customer.setCustomerName(customerNameText);
                    customer.setAddress(updatedAddress);
                    if (customer.isValid()) {
                        CustomerDaoImpl.update(customer); //Update the customer
                        displayCustomerScreen(event);
                    }
                }
            }
            catch(Exception e){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setTitle("Customer Update Error");
                alert.show();
            }
        } else {
            try {
                //New Customer and NO ID exists
                City city = new City();
                city.setCityName(cityText);
                city.setCountry(modifiedCountry);
                if (city.isValid()) {
                    city.setCityId(CityDaoImpl.create(city));
                }
                Address address = new Address();
                address.setAddress(addressText);
                address.setAddress2(address2Text);
                address.setPostalCode(postalCodeText);
                address.setCity(city);
                address.setPhone(phoneText);
                if (address.isValid()) {
                    address.setAddressId(AddressDaoImpl.create(address));
                }
                Customer customer = new Customer();
                customer.setCustomerName(customerNameText);
                customer.setAddress(address);
                if (customer.isValid()) {
                    customer.setCustomerId(CustomerDaoImpl.create(customer));
                    System.out.println("Add New Customer!");
                    displayCustomerScreen(event);
                }
            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setTitle("Customer Data Validation Error");
                alert.show();
            }
        }
    }

    private void displayCustomerScreen(ActionEvent event) {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
