package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Customer {
    private int customerId;
    private String customerName;
    private Boolean active;
    private Address address;

    public Customer(){
        this.customerId = 0;
        this.customerName = "";
        this.active = false;
        this.address = new Address();
    }

    public Customer(int customerId, String customerName, int addressId, Boolean active, Address address){
        this.customerId = customerId;
        this.customerName = customerName;
        this.active = active;
        this.address = address;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public String getCustomerName(){
        return this.customerName;
    }

    public Boolean getActive(){
        return this.active;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public void setAddress(Address address){
        this.address = address;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Address getAddress(){
        return this.address;
    }
}
