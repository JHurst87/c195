package View_Controller;

import DAO.CustomerDaoImpl;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.*;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable{
    private Stage stage;
    private Parent scene;
    private Alert alert;
    public static Customer selectedCustomer;

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
        customerNameCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getCustomerName()));
        addressCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getAddress()));
        address2Col.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getAddress2()));
        phoneCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getPhone()));
        cityCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getCity().getCityName()));
        postalCode.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getAddress().getPostalCode()));
        setCustomerTableView();

    }

    public void onActionReturn(ActionEvent event){
        System.out.println("Return to Main Menu");
        try {
            showScreen("/View_Controller/MainScreen.fxml", "Customers", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionAdd(ActionEvent event) throws IOException {
        System.out.println("Add Customer!");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyCustomer.fxml"));
        View_Controller.ModifyCustomerController controller = new View_Controller.ModifyCustomerController(new Customer(),true);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }



    public void onActionEdit(ActionEvent event) throws IOException{
        System.out.println("Edit Customer!");
        try{
            selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            if(selectedCustomer == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer Selection Error");
                alert.setContentText("Please select a valid Customer");
                alert.show();
            } else {
                showModifyScreen(event, false);
            }
        }
        catch(IOException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Selection Error");
            alert.show();
        }
    }

    public void onActionDelete(ActionEvent event) throws IOException{
        System.out.println("Delete Customer");
        try{
            selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            if( selectedCustomer == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a valid Customer");
                alert.setTitle("Selection Error");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Part?");
                alert.setContentText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.isPresent() && option.get() == ButtonType.OK) {
                    CustomerDaoImpl.delete(selectedCustomer);
                    System.out.println("Delete and update view!");
                    setCustomerTableView();
                }
            }
        }
        catch(Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("No Customer Selected Error");
            alert.show();
        }

    }

    private void setCustomerTableView() {
        ObservableList<Customer> customers = CustomerDaoImpl.getAll();
        customerTableView.setItems(customers);
    }

    private void showModifyScreen(ActionEvent event, Boolean isNewCustomer) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyCustomer.fxml"));
        View_Controller.ModifyCustomerController controller = new View_Controller.ModifyCustomerController(selectedCustomer, isNewCustomer);
        loader.setController(controller);
        Parent root = loader.load();
        if(isNewCustomer) {
            stage.setTitle("Add Customer");
        }
        else {
            stage.setTitle("Update Customer");
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showScreen(String resource, String title, ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
