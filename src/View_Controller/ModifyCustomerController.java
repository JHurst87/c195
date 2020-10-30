package View_Controller;

import DAO.AddressDaoImpl;
import DAO.CityDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Country;
import Model.Customer;
import Model.Address;
import Model.City;
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
        displayCustomerScreen(event);
    }

    @FXML
    public void onActionSave(ActionEvent event){
        System.out.println("Save!");

        // GUI values
        String customerNameText = nameField.getText();
        String addressText = addressField.getText();
        String address2Text = address2Field.getText();
        String cityText = cityField.getText();
        String postalCodeText = postalCodeField.getText();
        Country modifiedCountry = countryField.getSelectionModel().getSelectedItem();
        String phoneText = phoneField.getText();
        // Detect if new customer or Existing customer
        if(!isNewCustomer){
            Address modifiedAddress = this.customer.getAddress();
            City modifiedCity = modifiedAddress.getCity();

            City city = modifiedCityHandler(modifiedCity.getCityId(), cityText, modifiedCountry);
            Address address = modifyAddressHandler(modifiedAddress.getAddressId(), addressText, address2Text, city, postalCodeText, phoneText);


            try{
                if(address.isValid()){
                    //Display
                    Customer customer = modifyCustomerHandler(this.customer.getCustomerId(), customerNameText, address);
                    System.out.println("Successfully Updated Customer!");
                }else{
                    //Create Error messages
                    System.out.println("There were errors!");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } else {
            //New Customer and NO ID exists
            City city = addCityHandler(cityText, modifiedCountry);
            Address address = addAddressHandler(addressText, address2Text, postalCodeText, city ,phoneText );
            Customer customer = addCustomerHandler(customerNameText, address);

            if(customer.getCustomerId() != 0){
                System.out.println("Add New Customer!");
            }

        }

    }

    private Customer addCustomerHandler(String customerName, Address address) {
        Customer customer = new Customer();

        customer.setCustomerName(customerName);
        customer.setAddress(address);

        customer.setCustomerId(CustomerDaoImpl.create(customer));

        return customer;
    }

    private Address addAddressHandler(String address1, String address2, String postalCode, City city, String phone) {
        Address address = new Address();

        address.setAddress(address1);
        address.setAddress2(address2);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setPhone(phone);

        address.setAddressId(AddressDaoImpl.create(address));

        return address;
    }

    private City addCityHandler(String cityText, Country country) {
        City city = new City();
        city.setCityName(cityText);
        city.setCountry(country);

        city.setCityId(CityDaoImpl.create(city));

        return city;
    }

    private Customer modifyCustomerHandler(int customerId, String customerNameText, Address address) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerNameText);
        customer.setAddress(address);

        CustomerDaoImpl.update(customer);
        return customer;
    }

    private City modifiedCityHandler(int cityId, String cityText, Country country) {
        City city = new City();
        city.setCityId(cityId);
        city.setCityName(cityText);
        city.setCountry(country);

        CityDaoImpl.update(city, country);

        return city;
    }

    private Address modifyAddressHandler(int addressId, String address1, String address2, City city, String postalCode, String phone) {
        Address address = new Address();

        address.setAddressId(addressId);
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setPhone(phone);

        AddressDaoImpl.updateAddress(address);

        return address;
    }

    private Country modifyCountryHandler(int countryId, Address address) {
        Country country = new Country();
        //CountryDaoImpl.update(countryId, );
        return country;
    }

    private void displayCustomerScreen(ActionEvent event){
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
