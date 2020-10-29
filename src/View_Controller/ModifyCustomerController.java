package View_Controller;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Alert alert;
    private Customer customer;
    @FXML
    private TextField customerId;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField address2;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField city;
    @FXML
    private TextField country;

    @FXML
    private TextField phone;

    public ModifyCustomerController(Customer selectedCustomer) {
        this.customer = selectedCustomer;
        System.out.println("RAn modified constructor!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(this.customer);
        System.out.println("Loaded Modify Customer!");
        name.setText(this.customer.getCustomerName());
        address.setText(this.customer.getAddress().getAddress());
        address2.setText(this.customer.getAddress().getAddress2());
        city.setText(this.customer.getAddress().getCity().getCityName());
        postalCode.setText(this.customer.getAddress().getPostalCode());
        country.setText(this.customer.getAddress().getCity().getCountry().getCountryName());
        phone.setText(this.customer.getAddress().getPhone());

    }

    @FXML
    public void onActionCancel(ActionEvent event){
        System.out.println("Go back to main Customer screen");
        displayMain(event);
    }

    @FXML
    public void onActionSave(ActionEvent event){
        System.out.println("Save!");
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
